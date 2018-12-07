package ru.grabber.parser;

import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SaveResult {
    private final Logger logger = Logger.getLogger(SaveResult.class);

    public SaveResult(String filename, Parsed allWebsiteLinks) {

        try (ObjectOutputStream bOS = new ObjectOutputStream(new FileOutputStream(filename))) {
            bOS.writeObject(allWebsiteLinks);
            System.out.println("Сохранено " + allWebsiteLinks.getHolder().amount() + " внутренних ссылок");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Problem with serialization in (" + filename + ") file: " + e.getMessage());
        }

    }

}
