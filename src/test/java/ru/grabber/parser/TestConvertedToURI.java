package ru.grabber.parser;

import junit.framework.TestCase;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestConvertedToURI {

    private Elements images;
    private Elements pages;

    @Before
    public void prepareLinks() {
        images = new Elements();
        pages = new Elements();
    }

    public ConvertedToURI prepareMock( ){
        ElementsFrom elms = mock(ElementsFrom.class);
        when(elms.images()).thenReturn(images);
        when(elms.pages()).thenReturn(pages);
        return new ConvertedToURI(elms);
    }

    @Test
    public void correctAmountLinks_Test() {

        images.add (new Element("img").attr("src", "http://www.website.ru/folder/file1/"));
        images.add (new Element("img").attr("src", "http://www.website.ru/folder/file2/"));
        images.add (new Element("img").attr("src", "http://www.website.ru/folder/file3/"));

        pages.add (new Element("a").attr("href", "http://www.website.ru/folder/file1/"));
        pages.add (new Element("a").attr("href", "http://www.website.ru/folder/file2/"));
        pages.add (new Element("a").attr("href", "http://www.website.ru/folder/file13"));
        pages.add (new Element("a").attr("href", "http://www.website.ru/folder/file4/"));

        TestCase.assertTrue(prepareMock().images().size() == 3);
        TestCase.assertTrue(prepareMock().pages().size() == 4);
    }

    /**
     * Все потенциально-возможные null обрабатываются и заменяются пустой строкой.
     */
    @Test
    public void nullDef_Test() {
        images.add (new Element("img").attr("src", null));
        pages.add (new Element("img").attr("src", null));

        TestCase.assertFalse(prepareMock().images().contains(null));
        TestCase.assertFalse(prepareMock().pages().contains(null));
    }

    @Test
    public void toLowerCase_Test() throws URISyntaxException {
        images.add (new Element("img").attr("src", "http://www.website.ru/folder/file1.IMG/"));
        images.add (new Element("img").attr("src", "http://www.website.ru/folder/file1.img/"));
        images.add (new Element("img").attr("src", "http://www.website.ru/folder/FILE1.img/"));

        pages.add (new Element("a").attr("href", "http://www.website.ru/folder/FiLe2/"));
        pages.add (new Element("a").attr("href", "http://www.website.ru/folder/FILE2/"));
        pages.add (new Element("a").attr("href", "http://www.website.ru/FOLDER/file2/"));

        System.out.println(prepareMock().pages());
        TestCase.assertTrue(prepareMock().images().size() == 1);
        TestCase.assertTrue(prepareMock().pages().size() == 1);
    }

    //TODO Тесттирование нормализации: устранения последнего символа в строке если это косая черта
}

