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
    private Set<URI> images;
    private Set<URI> pages;
    private final String host = "http://www.website.ru/";

    @Before
    public void prepare(){
        images = new HashSet<>();
        pages = new HashSet<>();
    }

    public Filtered prepareMock(){
        ConvertedToURI uri = mock(ConvertedToURI.class);
        when(uri.imagesLinks()).thenReturn( images );
        when(uri.pagesLinks() ).thenReturn( pages  );
        return new Filtered( host, uri );
    }

    /**
     * Ссылки на страницы чей хост не соотвествует игнорируются,
     * только картинкам можно располагаться на внешнем ресурсе
     */
    @Test
    public void outsideLinks_Test() throws URISyntaxException {
        images.add(new URI("http://www.otherWebsite.ru/folder/folder2/image4.jpg"));
        pages.add(new URI("http://www.otherWebsite.ru/folder/folder2/page/"));

        TestCase.assertTrue(prepareMock().images().size() == 1);
        TestCase.assertTrue(prepareMock().pages().size() == 0);
    }

    /**
     * В pages могут находиться прямые ссылки на картинки < a href="картинка" >
     * они должны быть перенесены в images (отбор идет по расшинению *.jpg | jpeg | gif | png)
     */
    @Test
    public void amountLinks_Test() throws URISyntaxException {
        images.add(new URI("http://www.website.ru/folder/folder2/image4.jpg"));

        pages.add(new URI("http://www.website.ru/folder/folder2/image5.jpg/"));
        pages.add(new URI("http://www.website.ru/folder/folder2/image6.JPG"));
        pages.add(new URI("http://www.website.ru/folder/folder2/image7.gif"));

        pages.add(new URI("http://www.website.ru/folder/folder2/page/"));
        pages.add(new URI("http://www.website.ru/folder/folder2/pagejpg.html/"));
        pages.add(new URI("http://www.website.ru/folder/folder2/pagejpg.htm"));

        TestCase.assertTrue(prepareMock().images().size() == 4);
        TestCase.assertTrue(prepareMock().pages().size() == 3);
    }

    /**
     * В pages могут находиться прямые ссылки на картинки < a href="картинка" >
     * они должны быть перенесены в images (отбор идет по расшинению *.js | doc | pdf)
     */
    @Test
    public void amountPagesLinks_Test() throws URISyntaxException {
        pages.add(new URI("http://www.website.ru/folder/folder/file/"));
        pages.add(new URI("http://www.website.ru/folder/folder/file1.jpg/"));
        pages.add(new URI("http://www.website.ru/folder/folder/file2.js/"));
        pages.add(new URI("http://www.website.ru/folder/folder/file3.doc"));
        pages.add(new URI("http://www.website.ru/folder/folder/file4.pdf"));

        TestCase.assertTrue(prepareMock().pages().size() == 1);
    }

    @Test
    public void emptyAddress_Test() throws URISyntaxException {
        images.add(new URI(""));
        pages.add(new URI(""));
        pages.add(new URI("#"));

        TestCase.assertTrue(prepareMock().images().size() == 0);
        TestCase.assertTrue(prepareMock().pages().size() == 0);
    }

    /**
     * Локальные ссылки игнорируются
     */
    @Test
    public void localLinks_Test() throws URISyntaxException {
        images.add(new URI("/folder/folder/image1.jpg"));
        pages.add(new URI("/folder/folder/file"));

        TestCase.assertTrue(prepareMock().images().size() == 0);
        TestCase.assertTrue(prepareMock().pages().size() == 0);
    }

    @Test
    public void null_Test() throws URISyntaxException {
        images.add(null);
        pages.add(null);

        TestCase.assertTrue(prepareMock().images().size() == 0);
        TestCase.assertTrue(prepareMock().pages().size() == 0);
    }

    /**
     * pages может содержать дубликат уже находящейся ссылки в images,
     * дубликат должен быть схлопнут
     */
    @Test
    public void deleteRepeatedLinks_Test() throws URISyntaxException {
        images.add(new URI("http://www.website.ru/folder/folder1/image1.jpg"));
        images.add(new URI("http://www.website.ru/folder/folder1/IMAGE2.JPG"));
        pages.add(new URI("http://www.website.ru/folder/folder1/IMAGE2.JPG"));
        pages.add(new URI("http://www.website.ru/folder/folder/file"));
        pages.add(new URI("http://www.website.ru/folder/folder/file"));
        TestCase.assertTrue(prepareMock().images().size() == 2);
        TestCase.assertTrue(prepareMock().pages().size() == 1);
    }

}
