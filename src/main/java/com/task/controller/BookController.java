package com.task.controller;

import com.task.exception.ResourceNotFound;
import com.task.model.Book;
import com.task.service.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Dimon on 02.07.2018.
 */
@RestController
public class BookController {
    public static final String NOT_FOUND_BOOK_ID = "BookId";
    @Autowired
    private BookDAO bookDAO;

    @GetMapping("/books")
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookDAO.findAll(pageable);
    }


    @PostMapping("/book/create")
    public Book createBook(@Valid @RequestBody Book book) {
        return bookDAO.save(book);
    }

    @PutMapping("/book/update/{bookId}")
    public Book updateBook(@PathVariable Long bookId, @Valid @RequestBody Book bookRequest) {
        return bookDAO.findById(bookId).map(book -> {
            book.setTitle(bookRequest.getTitle());
            book.setGenre(bookRequest.getGenre());
            book.setIsbn(bookRequest.getIsbn());
            book.getAuthors().addAll(bookRequest.getAuthors());
            return bookDAO.update(book, bookRequest);
        }).orElseThrow(() -> new ResourceNotFound(NOT_FOUND_BOOK_ID, bookId));
    }

    @DeleteMapping("/book/delete/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        return bookDAO.findById(bookId).map(book -> {
            bookDAO.delete(book);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFound(NOT_FOUND_BOOK_ID, bookId));
    }
}
