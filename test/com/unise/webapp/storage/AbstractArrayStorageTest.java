package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class AbstractArrayStorageTest {
    private final Storage storage = new ArrayStorage();

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    void get() {
    }

    @Test
    void clear() {
    }

    @Test
    void update() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }

//    @Test(expected = NotExistStorageException.class)
//    public void getNotExist() throws Exception {
//        storage.get("dummy");
//    }
}