package ru.grabber.eoStyle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Grab all static content (images, html) from web-site.
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

public class Main {
    private static final Set<String> links = new HashSet<>();
    private static final Map<String, Boolean> pagesLinks = new HashMap<>();
    private static final String WEBSITE = "http://wwww.website.ru/";

    public static void main(String[] args) {

        pagesLinks.put(WEBSITE, false);

        //TODO: дальше продолжать, без промежутков... новый декоратор для граббинга
        new PrepareForSaving(
            new DelNotIntLinks(WEBSITE,
                new ConvertToSetURI(
                    new GetElementsFrom(
                        new JSoup( nextLink() )
                    )
                )
            )
        );
    }

    private static String nextLink() {
        for (Map.Entry entry : pagesLinks.entrySet()) {
            if (entry.getValue().equals(false)) {
                entry.setValue(true);
                return entry.getKey().toString();
            }
        }
        return null;
    }
}