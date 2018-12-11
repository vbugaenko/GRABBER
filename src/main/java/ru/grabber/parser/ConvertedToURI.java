package ru.grabber.parser;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

class ConvertedToURI {
    private final Logger logger = Logger.getLogger(ConvertedToURI.class);
    private final Set<URI> images= new HashSet<>();
    private final Set<URI> pages = new HashSet<>();

    ConvertedToURI(ElementsFrom jsoup) {
        try {
            for (Element img : jsoup.imagesLinks())
                images.add(makeUri(img.attr("src")));

            for (Element a : jsoup.pagesLinks())
                pages.add(makeUri(a.attr("href")));

        } catch (URISyntaxException e) {
            logger.error("Problem with making URI: " + e.getMessage());
        }
    }

    /**
     * Normalising all links:
     * - change null to empty string "";
     * - change symbols size toLowerCase;
     * - delete last symbol, if it's "/";
     */
    private URI makeUri(String source) throws URISyntaxException {
        if (source == null)
            source = "";

        if ((source.length()>0)&&((source.substring(source.length() - 1)).equals("/") ))
            source=source.substring(0, source.length() - 1);

        return new URI( source.toLowerCase() );
    }

    Set<URI> imagesLinks() { return images; }
    Set<URI> pagesLinks()  { return pages;  }
}
