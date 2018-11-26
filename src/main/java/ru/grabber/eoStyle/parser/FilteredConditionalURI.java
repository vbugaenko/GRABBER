package ru.grabber.eoStyle.parser;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;


/**
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

public class FilteredConditionalURI {
    private final Set<URI> imagesLinks;
    private final Set<URI> internalPagesLinks;

    public FilteredConditionalURI(ConvertedToURI convertToSetURI) {
        this.imagesLinks = convertToSetURI.getImagesLinks();
        System.out.println(">>>"+filteredImageDirectLink (convertToSetURI.getPagesLinks()).size());
        this.imagesLinks.addAll(filteredImageDirectLink (convertToSetURI.getPagesLinks() ));
        this.internalPagesLinks = deleteNotIntLinks(convertToSetURI.getPagesLinks());
    }

    //TODO: 2018-11-26 22:25:39 ERROR JSoupDoc:29 - Problem to connect with (http://www._____.ru/all_items/) for parsing : HTTP error fetching URL

    /**
     * Иногда бывает прямая ссылка на картинку,
     * и тогда нужно эту ссылку отнести в соответствующую коллекциию.
     */
    private Set<URI> filteredImageDirectLink(Set<URI> pages){
        return pages.stream()
            .filter(p -> p.toString().contains(".jpg")) //TODO: почему регулярка не проходит
            .collect(Collectors.toSet());
    }


    private Set<URI> deleteNotIntLinks(Set<URI> pages) {

        return pages.stream()
            .filter(p -> p.getHost()!= null )
            .filter(p -> !p.toString().contains(".jpg")) //TODO: регулярка
            .collect( Collectors.toSet() );
    }

    public Set<URI> getImagesLinks() { return imagesLinks;        }
    public Set<URI> getPagesLinks()  { return internalPagesLinks; }
}
