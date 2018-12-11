package ru.grabber.parser;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

public class TestElementsFrom {

    @Test(expected = IllegalArgumentException.class)
    public void getElementsFromNull() throws IOException {
        ElementsFrom elms = new ElementsFrom(null);
    }

    @Test(expected = IOException.class)
    public void getElementsFrom404Resource() throws IOException {
        ElementsFrom elms = new ElementsFrom("http://www.website404.ru/all_items/");
    }

    @Test
    public void amountLinksFromRealResource_Test() throws IOException {
        ElementsFrom elmsFrm = new ElementsFrom("http://yandex.ru");
        ConvertedToURI uri = new ConvertedToURI(elmsFrm);
        TestCase.assertTrue(uri.imagesLinks().size() > 0);
        TestCase.assertTrue(uri.pagesLinks().size() > 0);
    }
}
