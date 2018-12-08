package ru.grabber.parser;

import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Save (LinksHolder) parsed links.
 *
 * @author Victor Bugaenko
 * @since 08.12.2018
 */

public class SaveResult {
    private final Logger logger = Logger.getLogger(SaveResult.class);

    public SaveResult(String filename, LinksHolder holder) {
        try (ObjectOutputStream bOS = new ObjectOutputStream( new FileOutputStream( filename ))) {
            bOS.writeObject(holder);
        } catch (Exception e) {
            logger.error("Problem with serialization in (" + filename + ") file: " + e.getMessage());
        }
    }

}
