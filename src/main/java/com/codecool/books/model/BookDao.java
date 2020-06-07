package com.codecool.books.model;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {

    void add(Book book) throws SQLException;

    void update(Book book) throws SQLException;

    Book get(int id) throws SQLException;

    List<Book> getAll() throws SQLException;

}
