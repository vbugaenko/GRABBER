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
            for (Element img : elements.images())
                images.add(makeUri(img.attr("src")));

            for (Element a : elements.pages())
                pages.add(makeUri(a.attr("href")));

        } catch (URISyntaxException e) {
            logger.error("Problem with making URI: " + e.getMessage());
        }
    }

    /**
     * Созданные ссылки обязательно должны быть нормированы.
     * (иначе может невольно происходить дублирование одного и того же контента)
     * - null заменяется на пустую строку;
     * - символы приводятся к нижнему регистру;
     * - если последний символ - косая черта, то он удаляется;
     */
    private URI makeUri(String source) throws URISyntaxException {
        if (source == null)
            source = "";

        if ((source.length()>0)&&((source.substring(source.length() - 1)).equals("/") ))
            source=source.substring(0, source.length() - 1);

        return new URI( source.toLowerCase() );
    }

    public Set<URI> images() { return images; }
    public Set<URI> pages()  { return pages;  }
}
