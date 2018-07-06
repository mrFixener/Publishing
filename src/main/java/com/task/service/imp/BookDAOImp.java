package com.task.service.imp;

import com.task.model.Author;
import com.task.model.Book;
import com.task.repository.AuthorRepository;
import com.task.repository.BookRepository;
import com.task.service.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Dimon on 27.06.2018.
 */
@Service
public class BookDAOImp implements BookDAO {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    @Override
    public Book save(Book book) {
        Book savedBook = bookRepository.save(book);
        saveChild(savedBook, book);
        return savedBook;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    @Override
    public void delete(Book book) {
        book.setAuthors(null);
        bookRepository.save(book);
        bookRepository.delete(book);
    }

    @Transactional
    @Override
    public Iterable<Book> saveAll(Iterable<Book> bookEntities) {
        return bookRepository.saveAll(bookEntities);
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Book update(Book book, Book temp) {
        saveChild(book, temp);
        return bookRepository.save(book);
    }

    private void saveChild(Book book, Book bookRequest) {
        bookRequest.getAuthors().stream().forEach(author -> {
            Author relatedAuthor = authorRepository.findById(author.getId()).get();
            if (author.getBooks() != null
                    && relatedAuthor != null && relatedAuthor.getBooks() != null) {
                author.getBooks().addAll(relatedAuthor.getBooks());
                author.getBooks().add(book);
            }
            authorRepository.save(author);
        });

    }
}
