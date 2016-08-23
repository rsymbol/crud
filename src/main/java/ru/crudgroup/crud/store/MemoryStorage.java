package ru.crudgroup.crud.store;

import ru.crudgroup.crud.models.User;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryStorage implements Storage {

    private final AtomicInteger ids = new AtomicInteger();
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<Integer, User>();

    /**
     * генерирование тестовой базы пользователей
     */
    public MemoryStorage() {
        for (int i = 0; i < 45; i++) {
            ids.addAndGet(1);
            add(new User(ids.intValue(), "Name" + (i + 1), 30 + (int) (20 * Math.random()), Math.random()>0.5, new Timestamp(new Date().getTime())));
        }
    }

    // комментарий в интерфейсе
    @Override
    public Collection<User> values() {
        return this.users.values();
    }

    // комментарий в интерфейсе
    @Override
    public Collection<User> values(final String sort, int offset, int row_count) {
        int s = users.values().size();
        if (offset >= s || offset < 0 || row_count < 1) return new ArrayList<User>();
        List<User> base = new ArrayList<User>();
        base.addAll(users.values());
        base.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                switch (sort) {
                    case "id":
                        return o1.getId() - o2.getId();
                    case "name":
                        return o1.getName().compareTo(o2.getName());
                    case "age":
                        return o1.getAge() - o2.getAge();
                    case "isAdmin":
                        return o1.isAdmin() ? -1 : 1;
                    case "createdDate":
                        return o1.getCreatedDate().getTime() < o2.getCreatedDate().getTime() ? 1 : -1;
                }
                return 1;
            }
        });
        int endPos = offset + row_count > s ? s - 1 : offset + row_count - 1;
        List<User> result = new ArrayList<User>();
        for (int i = offset; i <= endPos; i++) result.add(base.get(i));
        return result;
    }

    // комментарий в интерфейсе
    @Override
    public int add(final User user) {
        this.users.put(user.getId(), user);
        return user.getId();
    }

    // комментарий в интерфейсе
    @Override
    public void edit(final User user) {
        this.users.replace(user.getId(), user);
    }

    // комментарий в интерфейсе
    @Override
    public void delete(final int id) {
        this.users.remove(id);
    }

    // комментарий в интерфейсе
    @Override
    public User get(final int id) {
        return this.users.get(id);
    }

    // комментарий в интерфейсе
    @Override
    public int generateId() {
        return this.ids.incrementAndGet();
    }

    // комментарий в интерфейсе
    @Override
    public void close() {
    }
}
