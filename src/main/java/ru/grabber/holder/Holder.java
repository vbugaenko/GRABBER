package ru.grabber.holder;

import java.net.URI;
import java.util.Set;

/**
 * @author Victor Bugaenko
 * @since 10.12.2018
 */

public interface Holder {

    boolean haveNextLink();

    URI chooseNext();

    void add(Set<URI> links);

    void add(URI link);

    int amount();

    void save(String website);
}
