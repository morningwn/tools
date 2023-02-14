#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <dlfcn.h>
#include <pthread.h>

#include <jni.h>

#define  BUFFER_SIZE         256
#define  JAVA_MAIN_CLASS     "com/github/morningwn/tools/Main"
#define  JNI_CREATE_JNI      "JNI_CreateJavaVM"

/**
 从libjvm中找到的函数，产生虚拟机。
 */
typedef int (*CreateJavaVM_t)(JavaVM **ppJvm, void **ppEnv, void *pArgs);

static char *curPath = NULL;

static void *g_pLibHandler = NULL;
static JavaVM *g_pJvm = NULL;
static JNIEnv *g_pJniEnv = NULL;
static jclass g_jMainClass = NULL;
static jmethodID g_jMainMethod = NULL;

char *join(char *s1, char *s2) {
    char *result = malloc(strlen(s1) + strlen(s2) + 1);//+1 for the zero-terminator
    //in real code you would check for errors in malloc here
    if (result == NULL) exit(1);

    strcpy(result, s1);
    strcat(result, s2);

    return result;
}

/**
 从后向前
 */
static void release_for_exit() {
    g_jMainMethod = NULL;
    g_jMainClass = NULL;

    if (g_pJniEnv != NULL) {
        (*g_pJniEnv)->ExceptionDescribe(g_pJniEnv);
        (*g_pJniEnv)->ExceptionClear(g_pJniEnv);
        (*g_pJniEnv) = NULL;
    }

    if (g_pJvm != NULL) {
        (*g_pJvm)->DestroyJavaVM(g_pJvm);
        g_pJvm = NULL;
    }

    if (g_pLibHandler != NULL) {
        dlclose(g_pLibHandler);
        g_pLibHandler = NULL;
    }
}


static void load_jvm(char *pHomePath, JNIEnv **ppEnv, JavaVM **ppJvm) {
    char pJvmPath[BUFFER_SIZE] = {0};

//    strcpy(pJvmPath, "C:/Users/morni/IdeaProjects/Tools/target/app/bin/server/jvm.dll");
//    strcpy(pJvmPath, join(curPath, "/../target/app/bin/server/jvm.dll"));

    strcpy(pJvmPath, "C:/Users/morni/IdeaProjects/Tools/jre/bin/server/jvm.dll");

//    strcpy(pJvmPath, join(curPath, "/bin/server/jvm.dll"));

    g_pLibHandler = dlopen(pJvmPath, RTLD_NOW | RTLD_GLOBAL);

    if (g_pLibHandler == NULL) {
        printf("Error: %s\n", dlerror());
        return;
    }

    JavaVMOption options[10];
    int counter = 0;
    options[counter++].optionString = (char *) "-XX:+UseG1GC";
    options[counter++].optionString = (char *) "-XX:+UseG1GC";
    options[counter++].optionString = (char *) "-XX:-UseAdaptiveSizePolicy";
    options[counter++].optionString = (char *) "-XX:-OmitStackTraceInFastThrow";
    options[counter++].optionString = (char *) "-Xmn512m";
    options[counter++].optionString = (char *) "-Xmx2048m";
    options[counter++].optionString = (char *) "-Djava.library.path=natives";
    options[counter++].optionString = (char *) "-Djava.class.path=C:/Users/morni/IdeaProjects/Tools/target/Tools-1.0-SNAPSHOT-jar-with-dependencies.jar";

    JavaVMInitArgs vm_args;
    memset(&vm_args, 0, sizeof(vm_args));
    vm_args.version = JNI_VERSION_1_8;
    vm_args.nOptions = counter++;
    vm_args.options = options;
    vm_args.ignoreUnrecognized = JNI_TRUE;
    CreateJavaVM_t pCreateJvmFunction = (CreateJavaVM_t) dlsym(g_pLibHandler, JNI_CREATE_JNI);

    if (pCreateJvmFunction == NULL) {
        return;
    }
    int retCode = pCreateJvmFunction(ppJvm, (void **) ppEnv, &vm_args);
    if (retCode != 0 || *ppJvm == NULL || *ppEnv == NULL) {
        *ppJvm = NULL;
        *ppEnv = NULL;
    }
}

static void run_java_class() {
    if (g_pJniEnv == NULL) {
        return;
    }

    g_jMainClass = (*g_pJniEnv)->FindClass(g_pJniEnv, JAVA_MAIN_CLASS);
    if ((*g_pJniEnv)->ExceptionCheck(g_pJniEnv) == JNI_TRUE || g_jMainClass == NULL) {
        return;
    }

    g_jMainMethod = (*g_pJniEnv)->GetStaticMethodID(g_pJniEnv, g_jMainClass, "main", "([Ljava/lang/String;)V");
    if ((*g_pJniEnv)->ExceptionCheck(g_pJniEnv) == JNI_TRUE || g_jMainMethod == NULL) {
        return;
    }

    (*g_pJniEnv)->CallStaticVoidMethod(g_pJniEnv, g_jMainClass, g_jMainMethod, NULL);
}

void *thread_function(void *pData) {
    char pHomePath[512] = {0};

    load_jvm(pHomePath, &g_pJniEnv, &g_pJvm);

    run_java_class();

    return NULL;
}


int main(const int argc, const char **argv) {
    //也可以将buffer作为输出参数
    char *tmpPath;
    if ((tmpPath = getcwd(NULL, 0)) == NULL) {
        perror("getcwd error");
        return -1;
    }
    curPath = malloc(strlen(tmpPath) + 1);
    int index = 0;
    while (index < strlen(tmpPath)) {
        if (tmpPath[index] != '\\') {
            curPath[index] = tmpPath[index];
        } else {
            curPath[index] = '/';
        }
        index++;
    }
    curPath[strlen(tmpPath)] = '\0';
    free(tmpPath);

    thread_function(NULL);
    release_for_exit();
    return 0;
}