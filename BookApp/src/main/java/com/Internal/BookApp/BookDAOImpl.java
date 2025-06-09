package com.Internal.BookApp;

import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.*;

@Repository
public class BookDAOImpl implements BookDAO {

    private final String JDBC_URL = "jdbc:h2:~/test";
    private final String JDBC_USER = "sa";
    private final String JDBC_PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    @Override
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS books (" +
                "id INT PRIMARY KEY, " +
                "title VARCHAR(255), " +
                "author VARCHAR(255))";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public void insertBook(Book book) throws SQLException {
        String check = "SELECT COUNT(*) FROM books WHERE id = ?";
        String insert = "INSERT INTO books (id, title, author) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(check)) {
            checkStmt.setInt(1, book.getId());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) == 0) {
                try (PreparedStatement pstmt = conn.prepareStatement(insert)) {
                    pstmt.setInt(1, book.getId());
                    pstmt.setString(2, book.getTitle());
                    pstmt.setString(3, book.getAuthor());
                    pstmt.executeUpdate();
                }
            }
        }
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(new Book(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author")));
            }
        }
        return books;
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE books SET title = ?, author = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setInt(3, book.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteBook(int id) throws SQLException {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Book> getBook(String author) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE author = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, author);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author")
                ));
            }
        }
        return books;
    }
}
