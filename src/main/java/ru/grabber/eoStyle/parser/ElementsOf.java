package ru.grabber.eoStyle.parser;

import org.apache.log4j.Logger;
import org.jsoup.select.Elements;

/**
 * Get JSoup elements from JSoup document;
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

class ElementsOf {
    private final Logger logger = Logger.getLogger(ElementsOf.class);
    private final Elements imagesLinks;
    private final Elements pagesLinks;

    ElementsOf(JSoupDoc jSoup) {
        if (jSoup.getDoc() != null) {
            this.imagesLinks = jSoup.getDoc().getElementsByTag("img");
            this.pagesLinks = jSoup.getDoc().getElementsByTag("a");
        } else {
            this.imagesLinks = null;
            this.pagesLinks = null;
        }
    }

    public Elements getImagesLinks() {
        return imagesLinks;
    }
    public Elements getPagesLinks()  { return pagesLinks;  }
}