package ru.crudgroup.crud.store;

import ru.crudgroup.crud.models.User;
import java.util.Collection;

public interface Storage {

	public Collection<User> values();

	public Collection<User> values(String sort, int offset, int row_count);

	public int add(final User user);

	public void edit(final User user);

	public void delete(final int id);

	public User get(final int id);

	public int generateId();

	public void close();
}
