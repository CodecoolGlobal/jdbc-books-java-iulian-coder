package com.codecool.books;

import com.codecool.books.model.Author;
import com.codecool.books.model.AuthorDao;
import com.codecool.books.model.Book;
import com.codecool.books.model.BookDao;
import com.codecool.books.view.UserInterface;

import java.sql.SQLException;

public class BookManager extends Manager {
    BookDao bookDao;
    AuthorDao authorDao;

    public BookManager(UserInterface ui, BookDao bookDao, AuthorDao authorDao){
        super(ui);
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @Override
    protected String getName() {
        return "Book Manager";
    }

    @Override
    protected void list() throws SQLException {
        for (Book book: bookDao.getAll()){
            ui.println(book);
        }

    }

    @Override
    protected void add() throws SQLException {
        String title = ui.readString("Book title", "x");
        int authorId = ui.readInt("Author id", 1);
        bookDao.add(new Book(authorDao.get(authorId),title));


    }

    @Override
    protected void edit() throws SQLException {
        int id = ui.readInt("Book ID", 0);
        Book book = bookDao.get(id);
        if (book == null){
            ui.println("Book not found");
            return;
        }
        ui.println(book);

        String title = ui.readString("Book title", book.getTitle());
        int authorId = ui.readInt("Author id", book.getAuthor().getId());
        book.setTitle(title);
        book.setAuthor(authorDao.get(authorId));
        bookDao.update(book);
    }
}
