package com.github.morningwn.tools.utils.bean;

import com.github.morningwn.tools.component.FileListComponent;
import com.github.morningwn.tools.component.WallpaperComponent;
import com.github.morningwn.tools.mapper.ConfigMapper;
import com.github.morningwn.tools.mapper.NoteMapper;
import com.github.morningwn.tools.mapper.impl.ConfigMapperImpl;
import com.github.morningwn.tools.mapper.impl.NoteMapperImpl;
import com.github.morningwn.tools.view.stage.AbstractStageHolder;
import com.github.morningwn.tools.view.stage.BaseStageHolder;
import com.github.morningwn.tools.view.stage.ViewEnum;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author morningwn
 * @create in 2022/10/13 17:46
 */
public final class BeanUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtils.class);
    private static final Map<Class<?>, Object> BEANMAP = new HashMap<>();
    private static final Map<ViewEnum, AbstractStageHolder> STAGE_HOLDER = new ConcurrentHashMap<>();
    private static Stage mainStage;

    private BeanUtils() {
    }

    /**
     * 注册并初始化bean
     */
    public static void createBean() {
        BEANMAP.put(ConfigMapper.class, new ConfigMapperImpl());
        LOGGER.info("BeanUtils#create ConfigMapperImpl");
        BEANMAP.put(NoteMapper.class, new NoteMapperImpl());
        LOGGER.info("BeanUtils#create NoteMapperImpl");
        BEANMAP.put(FileListComponent.class, new FileListComponent());
        LOGGER.info("BeanUtils#create FileListComponent");
        BEANMAP.put(WallpaperComponent.class, new WallpaperComponent());
        LOGGER.info("BeanUtils#create WallpaperComponent");
        initBean();
    }

    /**
     * 初始化
     */
    private static void initBean() {
        LOGGER.info("BeanUtils#init");
        for (Map.Entry<Class<?>, Object> entry : BEANMAP.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof LifeBean) {
                ((LifeBean) value).init();
            }
        }
    }

    /**
     * 全局销毁
     */
    public static void destroy() {
        LOGGER.info("BeanUtils#destroy stage start");
        for (Map.Entry<ViewEnum, AbstractStageHolder> entry : STAGE_HOLDER.entrySet()) {
            AbstractStageHolder value = entry.getValue();
            if (value != null) {
                value.hidden();
            }
        }
        LOGGER.info("BeanUtils#destroy stage end");

        LOGGER.info("BeanUtils#destroy bean");
        for (Map.Entry<Class<?>, Object> entry : BEANMAP.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof LifeBean) {
                ((LifeBean) value).destroy();
            }
        }
    }

    /**
     * 获取bean
     *
     * @param cTClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(final Class<T> cTClass) {
        Object o = BEANMAP.get(cTClass);
        return cTClass.cast(o);
    }

    public static void setMainStage(Stage stage) {
        //stage采用UTILITY风格
        stage.initStyle(StageStyle.UTILITY);
        //将stage的透明度设置为0
        stage.setOpacity(0);
        //stage展示出来，此步骤不可少，缺少此步骤stage还是会存在任务栏
        stage.show();
        mainStage = stage;
    }

    /**
     * 使用默认的方式创建stage
     *
     * @param viewEnum
     * @return
     */
    public static AbstractStageHolder getOrCreateStage(ViewEnum viewEnum) {
        return getOrCreateStage(viewEnum, (main) -> new BaseStageHolder(viewEnum, main));
    }

    public static AbstractStageHolder getOrCreateStage(ViewEnum viewEnum, Function<Stage, AbstractStageHolder> stageHolderSupplier) {
        AbstractStageHolder abstractStageHolder = STAGE_HOLDER.get(viewEnum);
        if (abstractStageHolder != null) {
            return abstractStageHolder;
        }
        abstractStageHolder = stageHolderSupplier.apply(mainStage);
        putStage(viewEnum, abstractStageHolder);
        return abstractStageHolder;
    }

    public static AbstractStageHolder getStage(ViewEnum viewEnum) {
        return STAGE_HOLDER.get(viewEnum);
    }

    public static void putStage(ViewEnum viewEnum, AbstractStageHolder stage) {
        STAGE_HOLDER.put(viewEnum, stage);
    }
}
