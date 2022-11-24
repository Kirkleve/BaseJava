package com.unise.webapp.storage;

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
            System.out.println("Такого резюме нету");
            return null;
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
            System.out.println("Такого резюме не существует, попробуйте снова!");
        } else {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        if (count >= STORAGE_LIMIT) {
            System.out.println("Увы места в памяти для хранения резюме нету!");
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("Такое резюме существует!");
        } else {
            saveResume(count, r);
            count++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > 0) {
            System.out.println("Такого резюме нету!");
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
