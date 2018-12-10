package ru.grabber.holder;

import java.net.URI;
import java.util.Set;

public interface Holder {

    void setWebsite(String website);

    boolean haveNextLink();

    String chooseNext();

    void add(Set<URI> links);
}
