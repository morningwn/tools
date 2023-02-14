package com.github.morningwn.tools.controller;

import com.github.morningwn.tools.entity.ConfigEntity;
import com.github.morningwn.tools.entity.ConfigTypeEnum;
import com.github.morningwn.tools.entity.WallpaperSetting;
import com.github.morningwn.tools.mapper.ConfigMapper;
import com.github.morningwn.tools.utils.DateUtils;
import com.github.morningwn.tools.utils.JSONUtils;
import com.github.morningwn.tools.utils.bean.BeanUtils;
import com.github.morningwn.tools.view.stage.ViewEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.controlsfx.control.ToggleSwitch;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author morningwn
 * @create in 2022/11/4 10:52
 */
public class SettingController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingController.class);
    @FXML
    private TextField fileListDirTxf;
    @FXML
    private TextField timeStyleTxf;
    @FXML
    private Button wallpaperDirBtn;
    @FXML
    private TextField wallpaperDirTxf;
    @FXML
    private Spinner<Integer> wallpaperUpdateSpr;
    @FXML
    private ToggleSwitch showTimeTs;
    private WallpaperSetting wallpaperSetting;
    private ConfigMapper configMapper;
    private ValidationSupport validationSupport;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configMapper = BeanUtils.getBean(ConfigMapper.class);
        ConfigEntity configEntity = configMapper.getByType(ConfigTypeEnum.WALLPAPER.getCode());
        if (Objects.nonNull(configEntity)) {
            wallpaperSetting = (WallpaperSetting) configEntity.getConfig();
        } else {
            wallpaperSetting = new WallpaperSetting();
        }

        validationSupport = new ValidationSupport();
        validationSupport.registerValidator(timeStyleTxf, Validator.createPredicateValidator(s -> DateUtils.pattern((String) s), "日期格式错误"));
        timeStyleTxf.setText(wallpaperSetting.getDatePattern());
        wallpaperDirTxf.setText(wallpaperSetting.getWallpaperDir());
        wallpaperUpdateSpr.getValueFactory().setValue(wallpaperSetting.getWallpaperUpdateFrequency());
        showTimeTs.setSelected(wallpaperSetting.getShowTime());
    }

    public void wallpaperDirAction(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择文件夹");
        File file = chooser.showDialog(BeanUtils.getStage(ViewEnum.SETTING).getStage());
        if (Objects.nonNull(file) && file.exists()) {
            wallpaperDirTxf.setText(file.getAbsolutePath());
            wallpaperSetting.setWallpaperDir(file.getAbsolutePath());
        }
    }

    public void saveExit(ActionEvent actionEvent) {
        LOGGER.debug("e :{}", validationSupport.isInvalid());
        if (validationSupport.isInvalid()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(BeanUtils.getStage(ViewEnum.SETTING).getStage());
            alert.setTitle("验证错误");
            alert.setHeaderText("日期格式错误");
            alert.showAndWait();
            return;
        }
        wallpaperSetting.setWallpaperDir(wallpaperDirTxf.getText());
        wallpaperSetting.setDatePattern(timeStyleTxf.getText());
        wallpaperSetting.setWallpaperUpdateFrequency(wallpaperUpdateSpr.getValue());
        wallpaperSetting.setShowTime(showTimeTs.isSelected());
        ConfigEntity wallpaperConfig = new ConfigEntity(JSONUtils.toJson(wallpaperSetting), ConfigTypeEnum.WALLPAPER.getCode());
        configMapper.insertOrUpdate(wallpaperConfig);
        BeanUtils.getStage(ViewEnum.SETTING).hidden();
    }

    public void exit(ActionEvent actionEvent) {
        BeanUtils.getStage(ViewEnum.SETTING).hidden();
    }

    public void restoreDefault(ActionEvent actionEvent) {
        wallpaperSetting.setShowTime(WallpaperSetting.SHOWTIME_DEFAULT);
        wallpaperSetting.setWallpaperDir("");
        wallpaperSetting.setWallpaperUpdateFrequency(WallpaperSetting.WALLPAPER_UPDATE_FREQUENCY_DEFAULT);
        wallpaperSetting.setDatePattern(WallpaperSetting.DATE_PATTERN_DEFAULT);
        timeStyleTxf.setText(wallpaperSetting.getDatePattern());
        wallpaperDirTxf.setText(wallpaperSetting.getWallpaperDir());
        wallpaperUpdateSpr.getValueFactory().setValue(wallpaperSetting.getWallpaperUpdateFrequency());
        showTimeTs.setSelected(wallpaperSetting.getShowTime());
    }
}
