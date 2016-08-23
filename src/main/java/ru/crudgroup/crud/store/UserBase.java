package ru.crudgroup.crud.store;

import ru.crudgroup.crud.models.User;

import java.util.Collection;

public class UserBase implements Storage {


//   --- ЗНАЧЕНИЯ ПО УМОЛЧАНИЮ ---
    public static final int COUNT_ROW_PAGE = 10;
    public static int current_page = 1;
    public static String current_sort = "id";

    private static UserBase instance = null;

//    --- ВЫБОР ТИПА STORAGE ---
//    private final Storage storage = new MemoryStorage();
    private final Storage storage = HibernateStorage.getInstance();

    public static UserBase getInstance() {
        if (instance==null) instance = new UserBase();
        return instance;
    }

    // комментарий в интерфейсе
    @Override
    public Collection<User> values() {
        return values(current_sort, (current_page - 1) * COUNT_ROW_PAGE, COUNT_ROW_PAGE);
    }

    // комментарий в интерфейсе
    @Override
    public Collection<User> values(String sort, int offset, int row_count) {
        return storage.values(sort, offset, row_count);
    }

    // комментарий в интерфейсе
    @Override
    public int add(final User user) {
        return this.storage.add(user);
    }

    // комментарий в интерфейсе
    @Override
    public void edit(final User user) {
        this.storage.edit(user);
    }

    // комментарий в интерфейсе
    @Override
    public void delete(final int id) {
        this.storage.delete(id);
    }

    // комментарий в интерфейсе
    @Override
    public User get(final int id) {
        return this.storage.get(id);
    }

    // комментарий в интерфейсе
    @Override
    public int generateId() {
        return this.storage.generateId();
    }

    // комментарий в интерфейсе
    @Override
    public void close() {
        this.storage.close();
    }

    /**
     * проверка корректности страницы, на которую необходимо перейти
     * @param page
     */
    public static void setCurrentPage(int page){
        int maxPage = instance.storage.values().size()/COUNT_ROW_PAGE+1;
        if (page>=1 && page<=maxPage) current_page=page;
    }
}
