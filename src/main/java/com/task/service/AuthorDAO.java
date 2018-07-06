package com.task.service;

import com.task.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Created by Dimon on 27.06.2018.
 */
public interface AuthorDAO {
    Author save(Author author);

    Optional<Author> findById(Long id);

    void delete(Author author);

    Page<Author> findAll(Pageable pageable);

    Author update(Author author, Author temp);
}
