package com.github.morningwn.tools.controller;

import com.github.morningwn.tools.utils.IOUtils;
import com.github.morningwn.tools.utils.WinUtils;
import cz.vutbr.web.css.MediaSpec;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.cell.ColorGridCell;
import org.controlsfx.control.cell.ImageGridCell;
import org.fit.cssbox.awt.GraphicsEngine;
import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.demo.ImageRenderer;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentSource;
import org.fit.cssbox.io.DocumentSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author morningwn
 * @create in 2023/1/20 13:23
 */
public class WallpaperFileController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(WallpaperFileController.class);
    @FXML
    private AnchorPane root;
    @FXML
    private VBox previewRoot;
    @FXML
    private Pagination imageListPagination;
    @FXML
    private GridView<Image> imageList;
    private String htmlFileUrl = "C:/Program Files (x86)/Steam/steamapps/workshop/content/431960/898123863/index.html";
    private String imageFileUrl = "C:/Program Files (x86)/Steam/steamapps/workshop/content/431960/898123863/img/image.jpg";
    private String videoFileUrl = "D:/迅雷下载/神话/神话DVD高清[第1集].mkv";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initImageView();
        try {
            initImageViewData();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }


    }

    private void initImageView() {
        imageList.setCellFactory(new Callback<GridView<Image>, GridCell<Image>>() {
            @Override
            public GridCell<Image> call(GridView<Image> imageGridView) {
                ImageGridCell imageGridCell = new ImageGridCell();
                imageGridCell.setMinSize(200, 200);
                imageGridCell.setPrefSize(200, 200);
                imageGridCell.setMaxSize(200, 200);
                return imageGridCell;
            }
        });
    }

    private void initImageViewData() throws MalformedURLException {
        // 图片
        imageList.getItems().add(new Image(imageFileUrl));
        // 网页
//        WebView webView = new WebView();
//        WebEngine engine = webView.getEngine();
//        File file = new File(htmlFileUrl);
//
//        engine.load(file.toURI().toURL().toString());
////        webView.setPrefSize(WinUtils.getWidth(), WinUtils.getHeight());
//
//
//        root.getChildren().add(webView);
//        AnchorPane.setBottomAnchor(webView, 0.0);
//        AnchorPane.setTopAnchor(webView, 500.0);
//        AnchorPane.setLeftAnchor(webView, 0.0);
//        AnchorPane.setRightAnchor(webView, 0.0);
//
//        SnapshotParameters snapshotParameters = new SnapshotParameters();
//        snapshotParameters.setViewport(new Rectangle2D(0, 0, WinUtils.getWidth(), WinUtils.getHeight()));
//        WritableImage writableImage = new WritableImage((int) WinUtils.getWidth(), (int) WinUtils.getHeight());
//        WritableImage snapshot = webView.snapshot(snapshotParameters, writableImage);
        Image htmlImage3 = getHtmlImage3();
        imageList.getItems().add(htmlImage3);
    }

    private Image getHtmlImage1() {
        try {
//            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            Document document = builder.parse(new FileInputStream(htmlFileUrl));
            Java2DRenderer renderer = new Java2DRenderer(new File(htmlFileUrl), (int) WinUtils.getWidth(), (int) WinUtils.getHeight());

            BufferedImage image = renderer.getImage();
            return SwingFXUtils.toFXImage(image, new WritableImage((int) WinUtils.getWidth(), (int) WinUtils.getHeight()));
        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }
    }

    private Image getHtmlImage2() {
        String html = IOUtils.readString(new File(htmlFileUrl));
        System.out.println(html);
        JEditorPane jEditorPane = new JEditorPane("text/html", html);
        jEditorPane.setEditable(false);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Dimension prefSize = jEditorPane.getPreferredSize();
        BufferedImage img = new BufferedImage(prefSize.width, prefSize.height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = img.getGraphics();
        jEditorPane.setSize(prefSize);
        jEditorPane.paint(graphics);
        graphics.dispose();
        return SwingFXUtils.toFXImage(img, new WritableImage(prefSize.width, prefSize.height));
    }

    private String mediaType = "screen";
    private org.fit.cssbox.layout.Dimension windowSize;
    private boolean cropWindow = false;
    private boolean loadImages = true;
    private boolean loadBackgroundImages = true;

    private Image getHtmlImage3() {
        try {
            //Open the network connection
            DocumentSource docSource = new DefaultDocumentSource(new File(htmlFileUrl).toURI().toURL().toString());

            //Parse the input document
            DOMSource parser = new DefaultDOMSource(docSource);
            Document doc = parser.parse();
            windowSize = new org.fit.cssbox.layout.Dimension((float) WinUtils.getWidth(), (float) WinUtils.getHeight());
            //create the media specification
            MediaSpec media = new MediaSpec(mediaType);
            media.setDimensions(windowSize.width, windowSize.height);
            media.setDeviceDimensions(windowSize.width, windowSize.height);

            //Create the CSS analyzer
            DOMAnalyzer da = new DOMAnalyzer(doc, docSource.getURL());
            da.setMediaSpec(media);
            da.attributesToStyles(); //convert the HTML presentation attributes to inline styles
            da.addStyleSheet(null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT); //use the standard style sheet
            da.addStyleSheet(null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT); //use the additional style sheet
            da.addStyleSheet(null, CSSNorm.formsStyleSheet(), DOMAnalyzer.Origin.AGENT); //render form fields using css
            da.getStyleSheets(); //load the author style sheets

            GraphicsEngine contentCanvas = new GraphicsEngine(da.getRoot(), da, docSource.getURL());
            contentCanvas.setAutoMediaUpdate(false); //we have a correct media specification, do not update
            contentCanvas.getConfig().setClipViewport(cropWindow);
            contentCanvas.getConfig().setLoadImages(loadImages);
            contentCanvas.getConfig().setLoadBackgroundImages(loadBackgroundImages);

            contentCanvas.createLayout(windowSize);
            BufferedImage image = contentCanvas.getImage();
            docSource.close();
            return SwingFXUtils.toFXImage(image, new WritableImage((int) WinUtils.getWidth(), (int) WinUtils.getHeight()));
        } catch (Exception e) {
            LOGGER.error("", e);
            return null;
        }
    }
}
