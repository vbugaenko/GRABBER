package ru.grabber.eoStyle.parser;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

public class Internal {
    private final Set<URI> imagesLinks;
    private final Set<URI> internalPagesLinks;

    public Internal(String website, FilteredConditionalURI conditionalURI) {
        this.imagesLinks = conditionalURI.getImagesLinks();
        this.internalPagesLinks = deleteNotIntLinks(website, conditionalURI.getPagesLinks());
    }

    private Set<URI> deleteNotIntLinks(String website, Set<URI> pages) {
        return pages.stream()
            .filter(p -> website.contains( p.getHost() ))
            .collect( Collectors.toSet() );
    }

    public Set<URI> getImagesLinks() { return imagesLinks;        }
    public Set<URI> getPagesLinks()  { return internalPagesLinks; }
}
