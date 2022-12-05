package com.unise.webapp.storage;

import com.unise.webapp.exception.ExistStorageException;
import com.unise.webapp.exception.NotExistStorageException;
import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp(){
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void size(){
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    void get(){
        Assertions.assertEquals(storage.get(UUID_1), RESUME_1);
    }

    @Test
    void getNotExist(){
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume updateR = new Resume(UUID_1);
        storage.update(updateR);
        Assertions.assertSame(storage.get(UUID_1), updateR,
                String.valueOf(storage.get(updateR.getUuid())));
    }

    @Test
    void updateNotExist(){
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.update(new Resume("dummy")));
    }

    @Test
    void save() {
        storage.save(new Resume("uuid4"));
        Assertions.assertEquals(4, storage.size());
    }

    @Test
    void saveStorageOverFlow(){
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assertions.fail("overflow occurred ahead of time");
        }
        Assertions.assertThrows(StorageException.class, () -> {
            storage.save(new Resume());
        });
    }

    @Test
    void saveExistResume(){
        Assertions.assertThrows(ExistStorageException.class, () ->
                storage.save(new Resume(RESUME_1.getUuid())));
    }

    @Test
    void delete(){
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_2);
            Assertions.assertEquals(2, storage.size());
        });
    }

    @Test
    void deleteNotExist(){
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () ->
                storage.delete("dummy"));
    }
    @Test
    void getAll() {
        Resume[] copy = storage.getAll();
        Assertions.assertEquals(copy[0], RESUME_1);
        Assertions.assertEquals(copy[1], RESUME_2);
        Assertions.assertEquals(copy[2], RESUME_3);
    }
}