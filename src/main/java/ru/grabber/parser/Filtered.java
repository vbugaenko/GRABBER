package ru.grabber.parser;

import java.net.URI;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

class Filtered {
    private final Set<URI> images;
    private final Set<URI> internalPages;

    Filtered(String website, ConvertedToURI uri) {
        this.images = deleteNullFrom ( uri.imagesLinks() );
        this.images.addAll( imagesHrefLinks ( uri.pagesLinks() ));
        this.internalPages = onlyPagesLinks ( website, uri.pagesLinks() );
    }

    /**
     * Delete null and links without host.
     */
    private Set<URI> deleteNullFrom (Set<URI> images) {
        return images.stream()
            .filter(Objects::nonNull)
            .filter(i -> i.getHost() != null)
            .collect(Collectors.toSet());
    }

    /**
     * Move direct image link (< a href = ...) to images collection.
     */
    private Set<URI> imagesHrefLinks(Set<URI> pages) {
        return pages.stream()
            .filter(Objects::nonNull)
            .filter(i -> i.getHost() != null )
            .filter(i -> Pattern.compile("\\.(jpg|jpeg|png|gif)")
                .matcher(i.toString().toLowerCase()).find())
            .collect(Collectors.toSet());
    }

    /**
     * Filtering external links and also pictures, documents, scripts.
     */
    private Set<URI> onlyPagesLinks(String website, Set<URI> pages) {
        return pages.stream()
            .filter(Objects::nonNull)
            .filter(p -> p.getHost() != null )
            .filter(p -> website.contains( p.getHost() ))
            .filter(p -> !Pattern.compile("\\.(jpg|jpeg|png|gif|doc|pdf|js)")
                .matcher(p.toString().toLowerCase()).find())
            .collect( Collectors.toSet() );
    }

    Set<URI> images() { return images;        }
    Set<URI> pages()  { return internalPages; }
}
