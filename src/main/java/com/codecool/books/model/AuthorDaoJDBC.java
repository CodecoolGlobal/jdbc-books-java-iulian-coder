package com.codecool.books.model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoJDBC implements AuthorDao {
    private final DataSource dataSource;

    public AuthorDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Author author) throws SQLException {
        // Done
        Connection connection = dataSource.getConnection();
        try {
            Statement statement = connection.createStatement();
            try {
                String sql;
                sql = "INSERT INTO author (first_name, last_name, birth_date) VALUES ('" +author.getFirstName() + "', '" + author.getLastName() + "', '" + author.getBirthDate() + "')";
                statement.executeUpdate(sql);
            } finally {
                statement.close();
            }
        } finally {
            connection.close();
        }
    }

    @Override
    public void update(Author author) throws SQLException {
        // TODO
        Connection connection = dataSource.getConnection();
        String sql;
        sql = "UPDATE author " + "SET first_name = ? , last_name = ?, birth_date = ?" + "WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, author.getFirstName());
        preparedStatement.setString(2,author.getLastName());
        preparedStatement.setDate(3,author.getBirthDate());
        preparedStatement.setInt(4,author.getId());
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    @Override
    public Author get(int id) throws SQLException {
        // Done
        Connection connection = dataSource.getConnection();
        String sql = "SELECT * from author where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Author author = null;
        while (resultSet.next()){
            author = new Author(resultSet.getString("first_name"),resultSet.getString("last_name"),resultSet.getDate("birth_date"));
            author.setId(resultSet.getInt("id"));
        }


        resultSet.close();
        preparedStatement.close();
        connection.close();

        return author;
    }

    @Override
    public List<Author> getAll() throws SQLException {
        // Done
        List<Author> authorList = new ArrayList<>();

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from author;");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            Date dateBirth = resultSet.getDate("birth_date");

            Author author = new Author(firstName, lastName, dateBirth);
            author.setId(id);
            authorList.add(author);

        }
        resultSet.close();
        statement.close();
        connection.close();
        return authorList;
    }
}
