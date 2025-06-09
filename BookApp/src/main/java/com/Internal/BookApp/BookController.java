package com.Internal.BookApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;

    @PostMapping
    public String addBook(@RequestBody Book book) throws SQLException {
        service.addBook(book);
        return "Book added successfully!";
    }

    @GetMapping
    public List<Book> getAllBooks() throws SQLException {
        return service.getAllBooks();
    }

    @PutMapping
    public String updateBook(@RequestBody Book book) throws SQLException {
        service.updateBook(book);
        return "Book updated successfully!";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) throws SQLException {
        service.deleteBook(id);
        return "Book deleted successfully!";
    }

    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) throws SQLException {
        return service.getBooksByAuthor(author);
    }
}
