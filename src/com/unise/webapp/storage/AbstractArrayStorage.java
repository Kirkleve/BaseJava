package com.unise.webapp.storage;

import com.unise.webapp.exception.ExistStorageException;
import com.unise.webapp.exception.NotExistStorageException;
import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count;

    public int size() {
        return count;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (count == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            saveResume(index, r);
            count++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > 0) {
            throw new NotExistStorageException(uuid);
        } else {
            removeResume(index);
            storage[count - 1] = null;
            count--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    protected abstract void removeResume(int index);

    protected abstract void saveResume(int index, Resume r);

    protected abstract int getIndex(String uuid);

}
