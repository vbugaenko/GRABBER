package ru.grabber.parser;

import junit.framework.TestCase;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestConvertedToURI {

    Elements images;
    Elements pages;
    ConvertedToURI uri;

    @Before
    public void prepareLinks() {
        images = new Elements();
        pages = new Elements();

        images.add (new Element("img").attr("src", "http://www.website.ru/folder/file1/"));
        images.add (new Element("img").attr("src", "http://www.website.ru/folder/file2/"));
        images.add (new Element("img").attr("src", "http://www.website.ru/folder/file3/"));
        images.add (new Element("img").attr("src", null));

        pages.add (new Element("a").attr("href", "http://www.website.ru/folder/file1/"));
        pages.add (new Element("a").attr("href", "http://www.website.ru/folder/file2/"));
        pages.add (new Element("a").attr("href", "http://www.website.ru/folder/file13"));
        pages.add (new Element("a").attr("href", "http://www.website.ru/folder/file4/"));

        ElementsFrom elms = mock(ElementsFrom.class);
        when(elms.images()).thenReturn(images);
        when(elms.pages()).thenReturn(pages);
        uri = new ConvertedToURI(elms);
    }

    @Test
    public void correctAmountLinks_Test() {
        TestCase.assertTrue(uri.images().size() == 4);
        TestCase.assertTrue(uri.pages().size() == 4);
    }

    /**
     * Все потенциально-возможные null обрабатываются и заменяются пустой строкой.
     */
    @Test
    public void nullDef_Test() {
        TestCase.assertFalse(uri.images().contains(null));
    }

}

