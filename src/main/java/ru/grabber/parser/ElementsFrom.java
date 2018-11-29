package ru.grabber.parser;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * Get JSoup elements from JSoup document;
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

class ElementsFrom {
    private final Logger logger = Logger.getLogger(ElementsFrom.class);
    private static final String BLANK = "<html><head><title>BlankPage</title></head><body></body></html>";
    private final Elements images;
    private final Elements pages;

    ElementsFrom(String webpage) {
        this.images = check(webpage).getElementsByTag("img");
        this.pages = check(webpage).getElementsByTag("a");
        logger.info("collected: " + images.size() + " images " + pages.size() + " pages links ");
    }

    private Document check(String webpage) {
        if (webpage == null) {
            webpage = BLANK;
            logger.info("webpage for parsing is null");
        }
        //Document document = Jsoup.parse(webpage);

        Document document = null;
        try {
            document = Jsoup.connect(webpage).get();
        } catch (IOException e) {
            logger.error("Problem to connect with (" + webpage + ") for parsing : " + e.getMessage());
        }

        if (document == null) {
            document = Jsoup.parse(BLANK);
            logger.info("webpage is absent, used empty blank");
        }

        return document;
    }

    public Elements images() {
        return images;
    }
    public Elements pages()  { return pages;  }
}