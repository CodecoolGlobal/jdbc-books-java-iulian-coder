package com.codecool.books.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJDBC implements BookDao {
    private final DataSource dataSource;
    private AuthorDao authorDao;

    public BookDaoJDBC(DataSource dataSource, AuthorDao authorDao){
        this.dataSource = dataSource;
        this.authorDao = authorDao;
    }

    @Override
    public void add(Book book) throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "INSERT INTO book (author_id, title) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,book.getAuthor().getId());
        preparedStatement.setString(2,book.getTitle());
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
        System.out.println("Book add");
    }

    @Override
    public void update(Book book) throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "UPDATE book SET author_id = ?, title = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,book.getAuthor().getId());
        preparedStatement.setString(2,book.getTitle());
        preparedStatement.setInt(3,book.getId());
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
        System.out.println("Book update");
    }

    @Override
    public Book get(int id) throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql ="Select * from book where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Book book = null;
        while (resultSet.next()){
            int authorId = resultSet.getInt("author_id");
            String title = resultSet.getString("title");
            book = new Book(authorDao.get(authorId),title);
            book.setId(id);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return book;
    }

    @Override
    public List<Book> getAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            int id = resultSet.getInt("id");
            int authorId = resultSet.getInt("author_id");
            String title = resultSet.getString("title");
            Book book = new Book(authorDao.get(authorId),title);
            book.setId(id);
            books.add(book);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return books;
    }
}
