//
// Created by morni on 2022/10/19.
//

#include <Windows.h>
#include "com_github_morningwn_tools_utils_WinUtils.h"
#include <stdio.h>

//int main() {
//    char *path = "C:\\Users\\morni\\Pictures\\IMG_20170812_075042.jpg";
//    //SystemParametersInfoA(SPI_SETDESKWALLPAPER, 0, path, SPIF_SENDCHANGE);
//    printf(path);
//
//    return 0;
//}
static const char *ConvertUtf8ToAnsi(const char *_szUtf8) {
    if (NULL == _szUtf8) {
        return "";
    }
    int dwAnsiLen = MultiByteToWideChar(CP_UTF8, 0, _szUtf8, -1, NULL, 0);
    wchar_t *szTmpUnicode = malloc((size_t) dwAnsiLen + 1);
    memset(szTmpUnicode, 0, sizeof(wchar_t) * ((size_t) dwAnsiLen + 1));
    MultiByteToWideChar(CP_UTF8, 0, _szUtf8, -1, szTmpUnicode, dwAnsiLen);

    int dwUtf8Len = WideCharToMultiByte(CP_ACP, 0, szTmpUnicode, -1, NULL, 0, NULL, NULL);
    char *szAnsi = malloc((size_t) dwUtf8Len + 1);
    memset(szAnsi, 0, sizeof(char) * ((size_t) dwUtf8Len + 1));
    WideCharToMultiByte(CP_ACP, 0, szTmpUnicode, -1, szAnsi, dwUtf8Len, NULL, NULL);
    free(szTmpUnicode);

    return szAnsi;
}

char *jstringToWindows(JNIEnv *env, jstring jstr) { //UTF8/16转换成gb2312
    int length = (*env)->GetStringLength(env, jstr);
    const jchar *jcstr = (*env)->GetStringChars(env, jstr, 0);

    int clen = WideCharToMultiByte(CP_ACP, 0, (LPCWSTR) jcstr, length, NULL, 0, NULL, NULL);

    //更正。作者原来用的是(char*)malloc( length*2+1 )，当java字符串中同时包含汉字和英文字母时，所需缓冲区大小并不是2倍关系。
    char *rtn = (char *) malloc(clen);
    int size = 0;
    size = WideCharToMultiByte(CP_ACP, 0, (LPCWSTR) jcstr, length, rtn, clen, NULL, NULL);
    if (size <= 0)
        return NULL;
    (*env)->ReleaseStringChars(env, jstr, jcstr);
    rtn[size] = 0;
    return rtn;
}

JNIEXPORT void JNICALL
Java_com_github_morningwn_tools_utils_WinUtils_setWallpaper(JNIEnv *env, jclass jClass, jstring jPath) {
//    const char *path = (*env)->GetStringUTFChars(env, jPath, NULL);
//    const char *szAnsi = ConvertUtf8ToAnsi(path);
    const char *path = jstringToWindows(env, jPath);
    SystemParametersInfoA(SPI_SETDESKWALLPAPER, 0, (PVOID) path, SPIF_SENDCHANGE);
//    (*env)->ReleaseStringUTFChars(env, jPath, path);  // release resources
}


