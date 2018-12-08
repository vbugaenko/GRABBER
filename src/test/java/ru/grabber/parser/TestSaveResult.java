package ru.grabber.parser;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestSaveResult {


    @Test
    public void SaveResult_Test() throws URISyntaxException {
        LinksHolder holder = new LinksHolder("http://www.website.ru");
        Set<URI> linksForSave = new HashSet<>(Arrays.asList(
            new URI("http://www.website.ru/file1"),
            new URI("http://www.website.ru/file2")
        ));
        holder.addForGrabb(linksForSave);

        new SaveResult("testFile.save", holder);


        try (FileInputStream fis = new FileInputStream("testFile.save");
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            LinksHolder loadedHolder = (LinksHolder) ois.readObject();
            TestCase.assertTrue(loadedHolder.getAllWebsiteLinks().equals(holder.getAllWebsiteLinks()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @After
    public void deleteTestFile(){
        new File("testFile.save").delete();
    }
}
