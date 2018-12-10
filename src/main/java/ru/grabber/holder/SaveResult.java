package ru.grabber.holder;

import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.util.Map;

/**
 * Save (LinksHolder) parsed links.
 *
 * @author Victor Bugaenko
 * @since 08.12.2018
 */

public class SaveResult {
    private final Logger logger = Logger.getLogger(SaveResult.class);

    public SaveResult(String project, Map<URI, Boolean> holder) {

        String file = getName(project) +".save";

        try (ObjectOutputStream bOS = new ObjectOutputStream(
            new FileOutputStream( file ))) {
            bOS.writeObject(holder);
        } catch (Exception e) {
            logger.error("Problem with serialization (" + file + "): " + e.getMessage());
        }
    }

    /**
     * Get WEBSITE name  http://www.website.ru/
     * @return           website
     */
    private static String getName(String project) {
        return project
            .replaceAll(".*(//)", "")
            .replaceAll(".*(www.)", "")
            .replaceAll("(.ru).*", "");
    }

}
