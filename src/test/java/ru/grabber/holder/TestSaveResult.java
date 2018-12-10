package ru.grabber.holder;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestSaveResult {

    @Test
    public void SaveResult_Test() throws URISyntaxException {

        Map<URI, Boolean> links = new ConcurrentHashMap<>();
        links.put( new URI("http://www.website.ru/file1"), false);
        links.put( new URI("http://www.website.ru/file2"), false);
        new SaveResult("testFile", links);

        try (FileInputStream fis = new FileInputStream("testFile.save");
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            Map<URI, Boolean> loaded = (Map<URI, Boolean>) ois.readObject();
            TestCase.assertTrue(loaded.equals(links));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteTestFile(){
        new File("testFile.save").delete();
    }
}
