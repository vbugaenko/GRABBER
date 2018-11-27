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
    private final Set<URI> images;
    private final Set<URI> internalPages;

    public Internal(String website, Conditioned conditionalURI) {
        this.images = conditionalURI.images();
        this.internalPages = deleteNotIntLinks(website, conditionalURI.pages());
    }

    private Set<URI> deleteNotIntLinks(String website, Set<URI> pages) {
        return pages.stream()
            .filter(p -> website.contains( p.getHost() ))
            .collect( Collectors.toSet() );
    }

    public Set<URI> images() { return images;        }
    public Set<URI> pages()  { return internalPages; }
}
