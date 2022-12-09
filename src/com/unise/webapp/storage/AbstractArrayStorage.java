package com.unise.webapp.storage;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count;

    public int size() {
        return count;
    }

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    @Override
    public Resume get(String uuid) {
        return storage[notExist(uuid)];
    }

    @Override
    public void update(Resume r) {
        storage[notExist(r.getUuid())] = r;
    }

    @Override
    public void save(Resume r) {
        if (count == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else if (exist(r.getUuid())) {
            saveResume(count, r);
            count++;
        }
    }

    @Override
    public void delete(String uuid) {
        removeResume(notExist(uuid));
        storage[count - 1] = null;
        count--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }


    protected abstract void removeResume(int index);

    protected abstract void saveResume(int index, Resume r);

    protected abstract Integer getIndex(String uuid);

}
