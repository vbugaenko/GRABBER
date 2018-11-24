package ru.grabber;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Find all internal links (images, html) of web-site.
 *
 * @author Victor Bugaenko
 * @since 23.11.2018
 */

class Parse {
    private final org.apache.log4j.Logger logger = Logger.getLogger(Parse.class);
    private int stopCount;
    private Map<URI, Boolean> urisPages = new HashMap<>();
    private Map<URI, Boolean> urisImages = new HashMap<>();
    private final String website;

    Parse(String website) {
        this.website = website;
        parse(website);
    }

    /**
     * Объединяем оба Map адресов в один;
     *
     * @return возвращаем единый (и html и картинок) сет Uri адресов;
     */
    public Set<URI> getURIs() {
        urisPages.putAll(urisImages);
        System.out.println("Ссылок на страницы "+urisPages.size());
        System.out.println("Ссылок на картинки "+urisImages.size());
        return new HashSet(urisPages.keySet());
    }

    private void parse(String website) {
        Document doc = getDocument(website);
        if (doc != null) {
            getImagesLinks(doc);
            getPagesLinks(doc);
        }
        String next = getNextLink();
        if (next != null)
            parse(next);
    }

    private Document getDocument(String website) {
        Document doc = null;
        try {
            doc = Jsoup.connect(website).get();
        } catch (IOException e) {
            logger.error("Problem to connect with (" + website + ") for parsing : " + e.getMessage());
        }
        return doc;
    }

    private void getImagesLinks(Document doc) {
        Elements iElements = doc.getElementsByTag("img");
        for (Element aElement : iElements) {
            String src = aElement.attr("src");
            URI uri = makeUri(src);
            if (uri != null)
                urisImages.putIfAbsent(uri, false);
        }
    }

    /**
     * Для добавления в коллекцию допускаются только ссылки отвечающие условию.
     * Позже можно подумать над более универсальным кодом.
     */
    private boolean check(String href) {
        return ((href.contains("item")) || (href.contains("group")));
    }

    private void getPagesLinks(Document doc) {
        Elements aElements = doc.getElementsByTag("a");
        for (Element aElement : aElements) {
            String href = aElement.attr("href");
            if (check(href)) {
                URI uri = makeUri(href);
                if (uri != null)
                    urisPages.putIfAbsent(uri, false);
            }
        }
    }

    /**
     * Проверка чтобы линк был внутренним,
     * обязательно содержал указатель на целевой сайт
     * (!!!относительные ссылки будут игнорироваться)
     */
    private URI makeUri(String source) {
        URI uri = null;
        if (source.contains(website))
        try {
            return new URI(source);
        } catch (URISyntaxException e) {
            logger.error("Problem make URI from (" + source + ") : " + e.getMessage());
        }
        return uri;
    }

    /**
     * Проверяет, есть ли еще в карте urlsPages ссылка,
     * которая еще не была обработана парсером,
     * если такая имеется, то возвращает ее
     * (+ тут же выставляет Boolean, что ссылка обработана).
     */
    private String getNextLink() {
            for (Map.Entry entry : urisPages.entrySet()) {
                if (entry.getValue().equals(false)) {
                    entry.setValue(true);
                    return entry.getKey().toString();
                }
            }
        return null;
    }
}
