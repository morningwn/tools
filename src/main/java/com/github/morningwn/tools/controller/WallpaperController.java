package com.github.morningwn.tools.controller;

import com.github.morningwn.tools.component.WallpaperComponent;
import com.github.morningwn.tools.entity.WallpaperEntity;
import com.github.morningwn.tools.utils.JSONUtils;
import com.github.morningwn.tools.utils.WinUtils;
import com.github.morningwn.tools.utils.bean.BeanUtils;
import com.github.morningwn.tools.view.stage.ViewEnum;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.JavaFXFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author morningwn
 * @create in 2023/1/17 10:51
 */
public class WallpaperController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(WallpaperController.class);

    @FXML
    private AnchorPane root;
    private ImageView imageView;
    private WebView webView;
    private WallpaperEntity wallpaper;
    private volatile Thread playThread;

    private String fileUrl = "C:/Program Files (x86)/Steam/steamapps/workshop/content/431960/898123863/index.html";
//    private String fileUrl = "C:/Program Files (x86)/Steam/steamapps/workshop/content/431960/898123863/img/image.jpg";
//    private String fileUrl = "D:/迅雷下载/神话/神话DVD高清[第1集].mkv";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOGGER.info("initialize");
//        setImageWallpaper(fileUrl);
        setHtmlWallpaper(fileUrl);
//        setVideoWallpaper();
        LOGGER.info("initialize end");
    }

    @Override
    public void show() {
        WallpaperComponent wallpaperComponent = BeanUtils.getBean(WallpaperComponent.class);
        wallpaperComponent.start();
        WinUtils.setWinIconAfter(ViewEnum.WALLPAPER.getTitle());
    }

    @Override
    public void close() {
        WinUtils.setWinIconTop(ViewEnum.WALLPAPER.getTitle());
    }

    public void setWallpaper(WallpaperEntity wallpaper) {
        LOGGER.info("setWallpaper wallpaper: {}", JSONUtils.toJson(wallpaper));
        this.wallpaper = wallpaper;
        switch (wallpaper.getType()) {
            case Image -> setImageWallpaper(wallpaper.getUrl());
            case Video -> setVideoWallpaper(wallpaper.getUrl());
            case WebPage -> setHtmlWallpaper(wallpaper.getUrl());
            default -> LOGGER.error("未知类型：{}", JSONUtils.toJson(wallpaper));
        }
    }

    private void setVideoWallpaper(String url) {
        imageView = new ImageView();
        playThread = new Thread(() -> {
            try {
                FFmpegFrameGrabber.tryLoad();
                final String videoFilename = url;
                final SourceDataLine soundLine;
                final ExecutorService audioExecutor;
                final ExecutorService imageExecutor;
                try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFilename)) {
                    grabber.start();
                    imageView.setFitWidth(WinUtils.getWidth());
                    imageView.setFitHeight(WinUtils.getHeight());
                    imageView.setSmooth(true);
//                    imageView.setFitWidth(grabber.getImageWidth());
//                    imageView.setFitHeight(grabber.getImageHeight());
                    final PlaybackTimer playbackTimer;
                    if (grabber.getAudioChannels() > 0) {
                        final AudioFormat audioFormat = new AudioFormat(grabber.getSampleRate(), 16, grabber.getAudioChannels(), true, true);

                        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                        soundLine = (SourceDataLine) AudioSystem.getLine(info);
                        soundLine.open(audioFormat);
                        soundLine.start();
                        playbackTimer = new PlaybackTimer(soundLine);
                    } else {
                        soundLine = null;
                        playbackTimer = new PlaybackTimer();
                    }

                    final JavaFXFrameConverter converter = new JavaFXFrameConverter();

                    audioExecutor = Executors.newSingleThreadExecutor();
                    imageExecutor = Executors.newSingleThreadExecutor();

                    final long maxReadAheadBufferMicros = 1000 * 1000L;

                    long lastTimeStamp = -1L;
                    while (!Thread.interrupted()) {
                        final Frame frame = grabber.grab();
                        if (frame == null) {
                            break;
                        }
                        if (lastTimeStamp < 0) {
                            playbackTimer.start();
                        }
                        lastTimeStamp = frame.timestamp;
                        if (frame.image != null) {
                            final Frame imageFrame = frame.clone();

                            imageExecutor.submit(() -> {
                                final Image image = converter.convert(imageFrame);
                                final long timeStampDeltaMicros = imageFrame.timestamp - playbackTimer.elapsedMicros();
                                imageFrame.close();
                                if (timeStampDeltaMicros > 0) {
                                    final long delayMillis = timeStampDeltaMicros / 1000L;
                                    try {
                                        Thread.sleep(delayMillis);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                                Platform.runLater(() -> imageView.setImage(image));
                            });
                        } else if (frame.samples != null) {
                            if (soundLine == null) {
                                throw new IllegalStateException("Internal error: sound playback not initialized");
                            }
                            final ShortBuffer channelSamplesShortBuffer = (ShortBuffer) frame.samples[0];
                            channelSamplesShortBuffer.rewind();

                            final ByteBuffer outBuffer = ByteBuffer.allocate(channelSamplesShortBuffer.capacity() * 2);

                            for (int i = 0; i < channelSamplesShortBuffer.capacity(); i++) {
                                short val = channelSamplesShortBuffer.get(i);
                                outBuffer.putShort(val);
                            }

                            audioExecutor.submit(() -> {
                                soundLine.write(outBuffer.array(), 0, outBuffer.capacity());
                                outBuffer.clear();
                            });
                        }
                        final long timeStampDeltaMicros = frame.timestamp - playbackTimer.elapsedMicros();
                        if (timeStampDeltaMicros > maxReadAheadBufferMicros) {
                            Thread.sleep((timeStampDeltaMicros - maxReadAheadBufferMicros) / 1000);
                        }
                    }

                    if (!Thread.interrupted()) {
                        long delay = (lastTimeStamp - playbackTimer.elapsedMicros()) / 1000 +
                                Math.round(1 / grabber.getFrameRate() * 1000);
                        Thread.sleep(Math.max(0, delay));
                    }
                    grabber.stop();
                    grabber.release();
                }
                if (soundLine != null) {
                    soundLine.stop();
                }
                audioExecutor.shutdownNow();
                audioExecutor.awaitTermination(10, TimeUnit.SECONDS);
                imageExecutor.shutdownNow();
                imageExecutor.awaitTermination(10, TimeUnit.SECONDS);

                Platform.exit();
            } catch (IllegalStateException | FrameGrabber.Exception | LineUnavailableException exception) {
                LOGGER.error("", exception);
                System.exit(1);
            } catch (InterruptedException exception) {
                LOGGER.error("", exception);
                System.exit(1);
            }
        });
        addNode(imageView);
        playThread.start();
    }

    private void setHtmlWallpaper(String url) {
        webView = new WebView();
        WebEngine engine = webView.getEngine();
        File file = new File(url);
        try {
            engine.load(file.toURI().toURL().toString());
            webView.setPrefSize(WinUtils.getWidth(), WinUtils.getHeight());
            addNode(webView);
        } catch (MalformedURLException e) {
            LOGGER.error("", e);
        }
    }

    private void setImageWallpaper(String url) {

        imageView = new ImageView();
        Image image = new Image(url);
        imageView.setImage(image);
        imageView.setFitWidth(WinUtils.getWidth());
        imageView.setFitHeight(WinUtils.getHeight());

        addNode(imageView);
    }

    private void addNode(Node node) {
        root.getChildren().add(node);
        root.setMinSize(WinUtils.getWidth(), WinUtils.getHeight());
        root.setPrefSize(WinUtils.getWidth(), WinUtils.getHeight());
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
    }

    private static class PlaybackTimer {
        private Long startTime = -1L;
        private final DataLine soundLine;

        public PlaybackTimer(DataLine soundLine) {
            this.soundLine = soundLine;
        }

        public PlaybackTimer() {
            this.soundLine = null;
        }

        public void start() {
            if (soundLine == null) {
                startTime = System.nanoTime();
            }
        }

        public long elapsedMicros() {
            if (soundLine == null) {
                if (startTime < 0) {
                    throw new IllegalStateException("PlaybackTimer not initialized.");
                }
                return (System.nanoTime() - startTime) / 1000;
            } else {
                return soundLine.getMicrosecondPosition();
            }
        }
    }
}
