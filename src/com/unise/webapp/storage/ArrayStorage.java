package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {


    @Override
    protected void removeResume(int index) {
        storage[index] = storage[count - 1];
    }

    @Override
    protected void saveResume(int index, Resume r) {
        storage[index] = r;
    }

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
