package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected final List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume r) {
        storage.set(notExist(r.getUuid()), r);
    }

    @Override
    public void save(Resume r) {
        if(exist(r.getUuid())) {
            storage.add(r);
        }
    }

    @Override
    public Resume get(String uuid) {
        return storage.get(notExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        storage.remove(storage.get(notExist(uuid)));
    }

    @Override
    public Resume[] getAll() {
        Resume[] getStorage = new Resume[storage.size()];
        return storage.toArray(getStorage);
    }

    @Override
    protected Integer getIndex(String str) {
        for (int i = 0; i < storage.size(); i++) {
            if (str.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
