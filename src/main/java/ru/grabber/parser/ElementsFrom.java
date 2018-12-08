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
    private final Elements images;
    private final Elements pages;

    ElementsFrom(String webpage) throws IOException {
        this.images = connectWith(webpage).getElementsByTag("img");
        this.pages = connectWith(webpage).getElementsByTag("a");
    }

    private Document connectWith(String webpage) throws IOException {
            return Jsoup.connect(webpage).get();
    }

    public Elements images() {
        return images;
    }
    public Elements pages()  { return pages;  }
}