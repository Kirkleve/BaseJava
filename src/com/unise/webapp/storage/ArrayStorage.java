package com.unise.webapp.storage;

import com.unise.webapp.model.Resume;
import java.util.Arrays;

public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int count;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public void update(Resume r, String str) {
        int index = findResume(r);
        if (index == -1)
            System.out.println("Такого резюме не существует, попробуйте снова!");
        else storage[index].setUuid(str);
    }

    public void save(Resume r) {
        if (count <= storage.length) {
            if (findResume(r) == -1)
                storage[count++] = r;
            else System.out.println("Извините, но такое резюме существует!");
        } else System.out.println("Увы места в памяти для хранения резюме нету!");
    }

    public Resume get(String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].getUuid())) return storage[i];
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < count; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                if (i < count)
                    System.arraycopy(storage, i + 1, storage, i, (count--) - i);
                storage[count] = null;
            }
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    public int findResume(Resume r) {
        for (int i = 0; i < count; i++) {
            if (r.equals(storage[i]))
                return i;
        }
        return -1;
    }
}
