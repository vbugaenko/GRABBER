package ru.grabber;

import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Grab static content (images, html) from URL's collection.
 *
 * @author Victor Bugaenko
 * @since 23.11.2018
 */
public class Grab {
  private final org.apache.log4j.Logger logger = Logger.getLogger(Grab.class);
  private int savedFilesCount;
  private final Set<URI> links;
  private final String folder;

  Grab(Set<URI> links, String folder) {
    this.links = links;
    this.folder = folder;

    for (URI uri : links)
      grabbing( uri );

    logger.info("Сохранено ссылок "+ savedFilesCount);
  }

  private void grabbing(URI uri){
    String path = getPath(uri);
    String file = getFileNameFromLink(path).toString();
    String folders = excludeFileName(path, file);

    make (folder+"/"+folders);
    save (uri, folder+"/"+path);
  }

  /**
   * Получить из URL полный путь к файлу со всеми папками.
   *
   * @param uri http://www.website.ru/folder/folder/file/
   * @return    folder/folder/file/
   */
  private String getPath(URI uri) {
    return uri.getPath().replaceFirst("/", "");
  }

  /**
   * Получить из URL только имя файла.
   *
   * @param source http://www.website.ru/folder/folder/file/
   * @return       file
   */
  private Path getFileNameFromLink(String source) {
    return Paths.get(source).getFileName();
  }

  /**
   * Исключить имя файла из строки.
   */
  private String excludeFileName(String path, String fileName) {
    return path.replace(fileName, "");
  }

  /**
   * Создать все директории, если их вдруг ещё нет.
   */
  private void make(String folders) {
    try {
      Files.createDirectories(Paths.get(folders));
    } catch (IOException e) {
      logger.error("Make folders (" + folders + ") error: " + e.getMessage());
    }
  }

  /**
   * Сохранение файла (по-байтово).
   */
  private void save(URI uri, String path) {
    try (ReadableByteChannel rbc = Channels.newChannel( uri.toURL().openStream() );
         FileOutputStream fos = new FileOutputStream( path )) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            savedFilesCount++;
    } catch (IOException e) {
      logger.error("Ошибка (" + uri + ")" + e.getMessage());
    }
  }

}
