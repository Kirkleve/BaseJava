package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void removeResume(int index) {
        if (index < STORAGE_LIMIT) {
            System.arraycopy(storage, index + 1, storage, index, count - index);
        }
    }

    @Override
    protected void saveResume(int index, Resume r) {
        System.arraycopy(storage, index, storage, index + 1, count - index);
        storage[index] = r;
    }

    @Override
    protected Integer getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, count, searchKey);
    }
}
