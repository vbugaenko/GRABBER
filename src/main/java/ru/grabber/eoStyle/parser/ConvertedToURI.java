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
    private final Set<URI> imagesLinks;
    private final Set<URI> pagesLinks;

    public ConvertedToURI(ElementsOf elements) {
            this.imagesLinks = new HashSet<>();
            for (Element aElement : elements.getImagesLinks())
                imagesLinks.add(
                    makeUri(aElement.attr("src")));

            this.pagesLinks = new HashSet<>();
            for (Element aElement : elements.getPagesLinks())
                pagesLinks.add(
                    makeUri(aElement.attr("href")));
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

    public Set<URI> getImagesLinks() { return imagesLinks; }
    public Set<URI> getPagesLinks()  { return pagesLinks;  }
}
