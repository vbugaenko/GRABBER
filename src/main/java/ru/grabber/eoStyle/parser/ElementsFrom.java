package ru.grabber.eoStyle.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Get JSoup elements from JSoup document;
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

class ElementsFrom {
    private static final String BLANK = "<html><head><title>BlankPage</title></head><body></body></html>";
    private final Elements imagesLinks;
    private final Elements pagesLinks;
    private final Document doc;

    ElementsFrom(String webpage) {
        this.doc = check(webpage);
        this.imagesLinks = doc.getElementsByTag("img");
        this.pagesLinks = doc.getElementsByTag("a");
    }

    private Document check(String webpage) {
        if (webpage == null) webpage = BLANK;

        Document document = Jsoup.parse(webpage);

        if (document == null)
            document = Jsoup.parse(BLANK);

        return document;
    }

    public Elements getImagesLinks() {
        return imagesLinks;
    }
    public Elements getPagesLinks()  { return pagesLinks;  }
}