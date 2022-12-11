package com.unise.webapp.storage;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count;

    protected abstract void removeResume(int index);

    protected abstract void saveResume(int index, Resume r);

    protected abstract Integer getSearchKey(String uuid);

    @Override
    protected void doSave(Resume r, Object key) {
        if (count == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            saveResume((Integer) key, r);
            count++;
        }
    }

    @Override
    protected Resume doGet(Object key) {
        return storage[(int) key];
    }

    @Override
    protected void doUpdate(Resume r, Object key) {
        storage[(int) key] = r;
    }

    @Override
    protected void doDelete(Object key) {
        removeResume((Integer) key);
        storage[count - 1] = null;
        count--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }


    public int size() {
        return count;
    }

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

}
