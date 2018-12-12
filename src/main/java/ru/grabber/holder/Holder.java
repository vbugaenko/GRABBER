package ru.grabber.holder;

import java.net.URI;
import java.util.Map;
import java.util.Set;

/**
 * @author Victor Bugaenko
 * @since 10.12.2018
 */

public interface Holder {

    boolean haveNextLink();

    URI chooseNext();

    void add(Map<URI, Boolean> links);

    void add(Set<URI> links);

    void add(URI link);

    int amount();

}
