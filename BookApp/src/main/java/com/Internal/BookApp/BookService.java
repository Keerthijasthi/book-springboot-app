package com.Internal.BookApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDAO dao;

    public void addBook(Book book) throws SQLException {
        if (book.getId() < 0 || book.getId() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Invalid ID: must be a positive number within integer range.");
        }

        dao.insertBook(book);
    }

    public List<Book> getAllBooks() throws SQLException {
        return dao.getAllBooks();
    }

    public void updateBook(Book book) throws SQLException {
        dao.updateBook(book);
    }

    public void deleteBook(int id) throws SQLException {
        dao.deleteBook(id);
    }

    public List<Book> getBooksByAuthor(String author) throws SQLException {
        return dao.getBook(author);
    }
}

