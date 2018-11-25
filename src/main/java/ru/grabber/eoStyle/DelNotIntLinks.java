package ru.grabber.eoStyle;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

public class DelNotIntLinks {
    private final Set<URI> imagesLinks;
    private final Set<URI> internalPagesLinks;

    public DelNotIntLinks(String website, ConvertToSetURI convertToSetURI) {
        this.imagesLinks = convertToSetURI.getImagesLinks();
        this.internalPagesLinks = deleteNotIntLinks(website, convertToSetURI.getPagesLinks());
    }

    private Set<URI> deleteNotIntLinks(String website, Set<URI> pages) {
        Set<URI> internalPages = new HashSet<>();
        for (URI uri : pages)
            if (website.contains(uri.getHost()))
                internalPages.add(uri);

        return internalPages;
    }

    public Set<URI> getImagesLinks() { return imagesLinks;        }
    public Set<URI> getpagesLinks()  { return internalPagesLinks; }
}
