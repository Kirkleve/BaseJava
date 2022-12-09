package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

public interface Storage {

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Object[] getAll();

    int size();
}
