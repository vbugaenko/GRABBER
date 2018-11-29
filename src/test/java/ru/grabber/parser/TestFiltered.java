package ru.grabber.parser;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestFiltered {
    Filtered cond;

    @Before
    public void prepare(){
        Set<URI> links1 = new HashSet<>();
        try {
            links1.add(new URI("http://www.website.ru/folder/folder1/image1.jpg"));
            links1.add(new URI("http://www.website.ru/folder/folder1/image2.JPG"));
            links1.add(new URI("http://www.website.ru/folder/folder1/image3.gif"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Set<URI> links2 = new HashSet<>();
        try {
            links2.add(new URI("http://www.website.ru/folder/folder2/image4.jpg"));
            links2.add(new URI("http://www.website.ru/folder/folder2/image5.jpg/"));
            links2.add(new URI("http://www.website.ru/folder/folder2/image6.JPG"));
            links2.add(new URI("http://www.website.ru/folder/folder2/image7.gif"));
            links2.add(new URI("http://www.website.ru/folder/folder2/image8.gif/"));
            links2.add(new URI("http://www.website.ru/folder/folder2/image9.png"));
            links2.add(new URI("http://www.website.ru/folder/folder2/image10.png/"));
            links2.add(new URI("http://www.website.ru/folder/folder2/image11.jpeg"));
            links2.add(new URI("http://www.website.ru/folder/folder2/image12.jpeg/"));
            links2.add(new URI("http://www.website.ru/folder/folder2/page/"));
            links2.add(new URI("http://www.website.ru/folder/folder2/pagejpg.html/"));
            links2.add(new URI("http://www.website.ru/folder/folder2/pagejpg.htm"));
            links2.add(new URI("http:///folder/folder1/image3.gif"));
            links2.add(new URI("/folder1/image3.gif"));
            links2.add(new URI("folder/folder2/pagejpg.htm"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ConvertedToURI uri = mock(ConvertedToURI.class);
        when(uri.images()).thenReturn(links1);
        when(uri.pages()).thenReturn(links2);
        cond = new Filtered( "http://www.website.ru/", uri );
    }

    @Test
    public void imagesAmount_Test(){
        TestCase.assertTrue(cond.images().size() == 12);
    }

    @Test
    public void pagesAmount_Test(){
        TestCase.assertTrue(cond.pages().size() == 3);
    }

}
