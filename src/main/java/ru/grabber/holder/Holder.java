package ru.grabber.holder;

import java.net.URI;
import java.util.Set;

public interface Holder {

    boolean haveNextLink();

    String chooseNext();

    void add(Set<URI> links);

    void add(URI link);

    int amount();
}
