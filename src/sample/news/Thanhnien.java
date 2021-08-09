package sample.news;

import javafx.scene.image.Image;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sample.Article;
import sample.Category;
import sample.News;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Thanhnien implements News {

    @Override
    public String findTime(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements date = doc.getElementsByClass("date");
        String time = date.first().text();
        return time;
    }

    @Override
    public ArrayList<Article> scrapeArticle(String url) throws IOException {
        Elements articleElementList = new Elements(); // Create list of element
        ArrayList<Article> articleList = new ArrayList<>(); //Create list of article
        Document doc = Jsoup.connect(url).get();
        articleElementList.addAll(doc.getElementsByTag("item"));
        // Loop into article Element
        for (Element articleElement : articleElementList) {
            String titleArticle = articleElement.child(0).ownText(); // Title of the article
            String date = articleElement.getElementsByTag("Pubdate").first().ownText();
            Image image = null;
            Document description = Jsoup.parse(articleElement.child(1).ownText());
            String urlArticle = description.getElementsByTag("a").attr("href"); //Link of the article
            String imageurl = description.getElementsByTag("img").attr("src");
            try{
                image = new Image(imageurl);
            } catch (IllegalArgumentException e){
            }

            Article article = new Article(image, titleArticle, urlArticle, date, "Thanh Nien", scrapeContent(urlArticle));

            articleList.add(article);
        }
        return articleList;
    }


}
