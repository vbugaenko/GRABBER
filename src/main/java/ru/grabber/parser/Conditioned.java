package ru.grabber.parser;

import java.net.URI;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

public class Conditioned {
    private final Set<URI> images;
    private final Set<URI> internalPages;

    public Conditioned(String website, ConvertedToURI uri) {
        this.images = uri.images();
        this.images.addAll( imagesHrefLinks ( uri.pages() ));
        this.internalPages = onlyPagesLinks ( website, uri.pages() );
    }

    /**
     * Иногда бывает прямая ссылка на картинку < a href = ...,
     * и тогда нужно эту ссылку отнести в соответствующую коллекциию (images)
     */
    private Set<URI> imagesHrefLinks(Set<URI> pages){
        return pages.stream()
            .filter(p -> p.getHost() != null )
            .filter(p -> Pattern.compile("\\.(jpg|jpeg|png|gif)")
                .matcher(p.toString().toLowerCase()).find())
            .collect(Collectors.toSet());
    }

    /**
     * Отбираем только внутренние ссылки
     * Выключаем из отбора ссылки на картинки и на JS
     */
    private Set<URI> onlyPagesLinks(String website, Set<URI> pages) {
        return pages.stream()
            .filter(p -> p.getHost() != null )
            .filter(p -> website.contains( p.getHost() ))
            .filter(p -> !Pattern.compile("\\.(jpg|jpeg|png|gif|doc|pdf|js)")
                .matcher(p.toString().toLowerCase()).find())
            .collect( Collectors.toSet() );
    }

    public Set<URI> images() { return images;        }
    public Set<URI> pages()  { return internalPages; }
}
