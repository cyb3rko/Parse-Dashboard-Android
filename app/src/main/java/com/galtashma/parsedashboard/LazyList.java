package com.galtashma.parsedashboard;

import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LazyList<T extends ParseObject> extends com.galtashma.lazyparse.LazyList<T> {
    public LazyList(ParseQuery<T> query, int stepSize) {
        super(query, stepSize);
    }

    public LazyList(ParseQuery<T> query) {
        super(query);
    }
}
