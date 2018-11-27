package ru.grabber.eoStyle.parser;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Get JSoup elements from JSoup document;
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

class ElementsFrom {
    private final String blank = "<html><head><title>BlankPage</title></head><body></body></html>";
    private final Elements imagesLinks;
    private final Elements pagesLinks;
    private final Document doc;

    ElementsFrom(String webpage) {
        this.doc = check(webpage);
        this.imagesLinks = doc.getElementsByTag("img");
        this.pagesLinks = doc.getElementsByTag("a");
    }

    private Document check(String webpage) {
        Document doc = null;
        if (webpage == null)
            webpage = blank;
        else
            doc = Jsoup.parse(webpage);

        if (doc == null)
            doc = Jsoup.parse(blank);

        return doc;
    }

    public Elements getImagesLinks() {
        return imagesLinks;
    }
    public Elements getPagesLinks()  { return pagesLinks;  }
}