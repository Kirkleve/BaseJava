package com.unise.webapp.storage;

import static org.junit.jupiter.api.Assertions.*;

class MapStorageTest extends AbstractArrayStorageTest{

    protected MapStorageTest() {
        super(new MapStorage());
    }
}