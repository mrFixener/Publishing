package com.task.service;


import com.task.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Created by Dimon on 27.06.2018.
 */
public interface BookDAO {
    Book save(Book book);

    Optional<Book> findById(Long id);

    void delete(Book book);

    Iterable<Book> saveAll(Iterable<Book> entities);

    Page<Book> findAll(Pageable pageable);

    Book update(Book book, Book temp);
}
