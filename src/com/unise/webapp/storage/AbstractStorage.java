package com.unise.webapp.storage;

import com.unise.webapp.exception.ExistStorageException;
import com.unise.webapp.exception.NotExistStorageException;

public abstract class AbstractStorage implements Storage {

    protected abstract Integer getIndex(String str);

    protected int notExist(String str) {
        int index = getIndex(str);
        if (index < 0) {
            throw new NotExistStorageException(str);
        }
        return index;
    }

    protected boolean exist(String str) {
        int index = getIndex(str);
        if (index >= 0) {
            throw new ExistStorageException(str);
        }
        return true;
    }
}
