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

public class ConvertedToURI {
    private final Logger logger = Logger.getLogger(ConvertedToURI.class);
    private final Set<URI> images= new HashSet<>();
    private final Set<URI> pages = new HashSet<>();

    public ConvertedToURI(ElementsFrom elements) {
        try {
            for (Element aElement : elements.getImagesLinks())
                images.add( makeUri(aElement.attr("src")) );

            for (Element aElement : elements.getPagesLinks())
                pages.add( makeUri(aElement.attr("href")));

        } catch (URISyntaxException e) {
            logger.error("Problem цшер making URI: " + e.getMessage());
        }
    }

    private URI makeUri(String source) throws URISyntaxException {
        if (source == null) source = "";
        return new URI(source);
    }

    public Set<URI> images() { return images; }
    public Set<URI> pages()  { return pages;  }
}
