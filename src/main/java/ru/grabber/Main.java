package ru.grabber;

import java.net.URI;
import java.util.Set;

/**
 * Grab all static content (images, html) from web-site.
 *
 * @author Victor Bugaenko
 * @since 22.11.2018
 */

public class Main {
  private static final String WEBSITE = "";

  public static void main(String[] args)  {
    Set<URI> uris = new Parse(WEBSITE).getURIs();
    System.out.println("Ссылок всего "+uris.size());
    new Grab ( uris , folder(WEBSITE) );
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

}
