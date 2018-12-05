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

public class Filtered {
    private final Set<URI> images;
    private final Set<URI> internalPages;

    public Filtered(String website, ConvertedToURI uri) {
        this.images = filteringImages(uri.images());
        this.images.addAll( imagesHrefLinks ( uri.pages() ));
        this.internalPages = onlyPagesLinks ( website, uri.pages() );
    }

    /**
     * Фильтрация ранее собранных image ссылок
     * (в частности, удаление из них null или ссылок с null хостом)
     */
    private Set<URI> filteringImages(Set<URI> images){
        return images.stream()
            .filter(Objects::nonNull)
            .filter(i -> i.getHost() != null)
            .collect(Collectors.toSet());
    }

    /**
     * Иногда бывает прямая ссылка на картинку < a href = ...,
     * и тогда нужно эту ссылку отнести в соответствующую коллекциию (images)
     */
    private Set<URI> imagesHrefLinks(Set<URI> pages){
        return pages.stream()
            .filter(Objects::nonNull)
            .filter(i -> i.getHost() != null )
            .filter(i -> Pattern.compile("\\.(jpg|jpeg|png|gif)")
                .matcher(i.toString().toLowerCase()).find())
            .collect(Collectors.toSet());
    }

    /**
     * Отбираем только внутренние ссылки
     * Выключаем из отбора ссылки на картинки и на JS
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

    public Set<URI> images() { return images;        }
    public Set<URI> pages()  { return internalPages; }
}
