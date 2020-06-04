package com.codecool.books.model;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
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
        Statement statement = connection.createStatement();
        String sql;
        sql = "UPDATE author SET first_name='"+author.getFirstName()+"',last_name='"+author.getLastName() +"',birth_date='"+author.getBirthDate()+"' WHERE id=1";

        statement.executeUpdate(sql);

        statement.close();
        connection.close();
    }

    @Override
    public Author get(int id) throws SQLException {
        // TODO
//        Connection connection = dataSource.getConnection();
//        Statement statement = connection.createStatement();
//        String sql;
//        sql = "SELETCT * from authors WHERE id=" +id;
//        ResultSet resultSet = statement.executeQuery(sql);
//        while (resultSet.next()){
//            String firstName = resultSet.getString("first_name");
//            System.out.println(firstName);
//        }
        return null;
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
