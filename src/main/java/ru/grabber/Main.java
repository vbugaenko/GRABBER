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

  public static void main(String[] args)  {
    //Распарсить сайт по ссылкам
    Set<URI> uris = new Parse(WEBSITE).getURIs();
    String project = folder(WEBSITE);
    File file = new File(project+".ser");
    serializeObject(file.getName(), uris);

    //Граб
    Set<URI> urisLoaded = readObject(file.getName());
    if (urisLoaded != null) {
      logger.info(urisLoaded.size() + " links prepared for loading from web");
      new Grab(uris, project);
    }
  }

  /**
   * Получить только имя сайта
   * @param website http://www.website.ru/folder/folder/file/
   * @return        website.ru
   */
  private static String folder(String website) {
    return website
            .replaceAll(".*(//)","")
            .replaceAll(".*(www.)","")
            .replaceAll("(.ru).*", "");
  }

  private static Set<URI> readObject(String fileName)  {
    Set<URI> uris = null;
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName)) ) {
        Object object = inputStream.readObject();
        uris = (Set<URI>)object;
        logger.info("data loaded from ["+ fileName +"]");
    } catch (Exception e) {
      logger.error("Problem with deserialization from (" + fileName + ") file: " + e.getMessage());
    }
    return uris;
  }

  private static void serializeObject(String filename, Set<URI> storage) {
    try (ObjectOutputStream bOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
      bOutputStream.writeObject(storage);
      logger.info("data saved to ["+ filename +"]");
    } catch (Exception e) {
      logger.error("Problem with serialization in (" + filename + ") file: " + e.getMessage());
    }
  }


}
