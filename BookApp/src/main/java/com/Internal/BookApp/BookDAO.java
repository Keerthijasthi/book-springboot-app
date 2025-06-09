package com.Internal.BookApp;

import java.sql.SQLException;
import java.util.List;

public interface BookDAO {
    void createTable() throws SQLException;
    void insertBook(Book book) throws SQLException;
    List<Book> getAllBooks() throws SQLException;
    void updateBook(Book book) throws SQLException;
    void deleteBook(int id) throws SQLException;
    List<Book> getBook(String author) throws SQLException;
}
