package ru.grabber;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.util.Set;

/**
 * Grab all static content (images, html) from web-site.
 *
 * @author Victor Bugaenko
 * @since 22.11.2018
 */

public class Main {
    private static final org.apache.log4j.Logger logger = Logger.getLogger(Main.class);
    private static final String WEBSITE = "";

    public static void main(String[] args) {
        //Распарсить сайт по ссылкам
        Set<URI> uriSet = new Parse(WEBSITE).getURIs();
        String projectName = getProjectName(WEBSITE);
        serialize(uriSet, projectName + ".ser");

        //Граб
        new Grab(uriSet, projectName);
        logger.info(uriSet.size() + " links prepared for loading from web");
    }

    /**
     * Получить из web-адреса только имя сайта.
     * @param  website http://www.website.ru/folder/folder/file/
     * @return         website
     */
    private static String getProjectName(String website) {
        return website
                .replaceAll(".*(//)", "")
                .replaceAll(".*(www.)", "")
                .replaceAll("(.ru).*", "");
    }

    private static Set<URI> deserialize(String fileName) {
        Set<URI> uris = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            Object object = inputStream.readObject();
            uris = (Set<URI>) object;
        } catch (Exception e) {
            logger.error("Problem with deserialization from (" + fileName + ") file: " + e.getMessage());
        }
        return uris;
    }

    private static void serialize(Set<URI> storage, String filename) {
        try (ObjectOutputStream bOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            bOutputStream.writeObject(storage);
        } catch (Exception e) {
            logger.error("Problem with serialization in (" + filename + ") file: " + e.getMessage());
        }
    }


}
