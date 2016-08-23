package ru.crudgroup.crud.store;

import ru.crudgroup.crud.models.User;

import java.util.Collection;

/**
 * интерфейс для всех типов хранилищ
 */
public interface Storage {

    /**
     * получение списка всех пользователей из хранилища
     * @return список пользователей
     */
    public Collection<User> values();

    /**
     * получение списка пользователей отсортированных по полю sort с позиции offset в количестве row_count из хранилища
     * @param sort
     * @param offset
     * @param row_count
     * @return список пользователей
     */
    public Collection<User> values(String sort, int offset, int row_count);

    /**
     * добавление пользователя user в хранилище
     * @param user
     * @return id добавленного пользователя
     */
    public int add(final User user);

    /**
     * замена существующего пользователя в хранилище на нового user с аналогичным id
     * @param user
     */
    public void edit(final User user);

    /**
     * удаление пользователя с переданным id
     * @param id
     */
    public void delete(final int id);

    /**
     * получение пользователя из хранилища с переданным id
     * @param id
     * @return пользователь
     */
    public User get(final int id);

    /**
     * определение следующего незанятого id (max id + 1)
     * @return
     */
    public int generateId();

    /**
     * закрытие хранилища (отключение от внешней базы)
     */
    public void close();
}
