package ru.grabber.eoStyle.parser;

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

public class ConvertedToURI {
    private final Logger logger = Logger.getLogger(ConvertedToURI.class);
    private final Set<URI> images= new HashSet<>();
    private final Set<URI> pages = new HashSet<>();

    public ConvertedToURI(ElementsFrom elements) {
            for (Element aElement : elements.getImagesLinks())
                images.add( makeUri(aElement.attr("src")));

            for (Element aElement : elements.getPagesLinks())
                pages.add( makeUri(aElement.attr("href")));
    }

    private URI makeUri(String source) {
        URI uri = null;
        try {
            return new URI(source);
        } catch (URISyntaxException e) {
            logger.error("Problem make URI from (" + source + ") : " + e.getMessage());
        }
        return uri;
    }

    public Set<URI> images() { return images; }
    public Set<URI> pages()  { return pages;  }
}
