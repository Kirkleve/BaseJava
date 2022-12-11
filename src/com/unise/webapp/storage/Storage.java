package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

public interface Storage {

    void save(Resume r);

    Resume get(String uuid);

    void update(Resume r);

    void delete(String uuid);

    void clear();

    int size();

    Object[] getAll();
}
