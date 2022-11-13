package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;
import java.util.Arrays;

public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];

    protected static final int STORAGE_LIMIT = 10000;

    private int count;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public void update(Resume r, String str) {
        int index = getIndex(r.getUuid());
        if (index == -1) {
            System.out.println("Такого резюме не существует, попробуйте снова!");
        } else {
            r = new Resume();
            r.setUuid(str);
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        if (count >= STORAGE_LIMIT) {
            System.out.println("Увы места в памяти для хранения резюме нету!");
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("Такое резюме существует!");
        } else {
            storage[count++] = r;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        return index != -1 ? storage[index] : null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[count - 1];
            storage[count - 1] = null;
            count--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    private int getIndex(String str) {
        for (int i = 0; i < count; i++) {
            if (str.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
