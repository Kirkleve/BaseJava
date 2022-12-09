package com.unise.webapp.storage;

import com.unise.webapp.exception.ExistStorageException;
import com.unise.webapp.exception.NotExistStorageException;
import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);
    private static final String UUID_NOT_EXIST = "dummy";

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void get() {
        assertGet(UUID_1, RESUME_1);
        assertGet(UUID_2, RESUME_2);
        assertGet(UUID_3, RESUME_3);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    void update() {
        Resume updated = new Resume(UUID_1);
        storage.update(updated);
        assertSame(storage.get(UUID_1), updated);
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () ->
                storage.update(new Resume(UUID_NOT_EXIST)));
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertGet(UUID_4, RESUME_4);
        assertSize(4);
    }

    @Test
    void saveStorageOverFlow() {
        try {
            storage.clear();
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("overflow occurred ahead of time");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void saveExistResume() {
        assertThrows(ExistStorageException.class, () ->
                storage.save(RESUME_1));
    }

    @Test
    void delete() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_2);
            assertSize(2);
            storage.get(UUID_2);
        });
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () ->
                storage.delete(UUID_NOT_EXIST));
    }

    @Test
    void getAll() {
        Resume[] expected = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        assertArrayEquals(expected, storage.getAll());
        assertSize(expected.length);
    }

    private void assertSize(int check) {
        assertEquals(check, storage.size());
    }

    private void assertGet(String uuid, Resume resume1) {
        assertEquals(storage.get(uuid), resume1);
    }
}