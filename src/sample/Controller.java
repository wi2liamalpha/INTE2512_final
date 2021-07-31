package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Jsoup;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.news.Tuoitre;
import sample.news.Vnexpress;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextArea titleNewspaper;

    @FXML
    private ListView<News> listView;
    @FXML
    private TextArea contentTextArea;

    //Delare webview
    @FXML
    private WebView newsScene;

    private WebEngine engine;
    private List<News> newsList;

    @Override
    public void initialize(URL url1, ResourceBundle resourceBundle) {
        try {
//            Vnexpress new1 = new Vnexpress("Java IO Tutorial","http");
//            Vnexpress new2 = new Vnexpress("1 IO Tutorial","http");
//            Vnexpress new3 = new Vnexpress("2 IO Tutorial","http");
            News articlesVnexpress = new Vnexpress();
            newsList = articlesVnexpress.crawlNews();
            News articlesTuoitre = new Tuoitre();
            newsList.addAll(articlesTuoitre.crawlNews());

            // Function to update image next to cell of dat article
            listView.setCellFactory(param -> new ListCell<News>() {
                private ImageView imageView = new ImageView();
                @Override
                public void updateItem(News page, boolean empty) {
                    super.updateItem(page, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        if (page.getImage() != null) {
                            imageView.setImage(page.getImage());
                            imageView.setFitHeight(70);
                            imageView.setFitWidth(70);
                        }
                        setGraphic(imageView);
                        setText("<" + page.getNewsName() + "> " + page.getTitle());
                        setWrapText(true);
                    }
                }
            });
            listView.getItems().setAll(newsList);
            listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            engine = newsScene.getEngine(); //initialise the engine web view

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    // Function load page
    public void loadPage(String url) throws Exception{
        engine.load(url);
    }
    @FXML
    public void handleClickView() throws Exception{
        News news = (News) listView.getSelectionModel().getSelectedItem();
        if (news == null) return;
//        System.out.println("The select item is " + news);
        loadPage(news.getUrl());
    }
}
