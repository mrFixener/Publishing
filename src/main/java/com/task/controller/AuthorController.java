package com.task.controller;

import com.task.domain.AuthorModel;
import com.task.exception.ResourceNotFound;
import com.task.model.Author;
import com.task.service.AuthorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Dimon on 01.07.2018.
 */
@RestController
public class AuthorController {
    public static final String NOT_FOUND_AUTHOR_ID = "AuthorId";
    @Autowired
    private AuthorDAO authorDAO;

    @GetMapping("/authors")
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorDAO.findAll(pageable);
    }

    @GetMapping("/author/info/short/{authorId}")
    public AuthorModel getShortAuthor(@PathVariable Long authorId) {
        return new AuthorModel(
                authorDAO.findById(
                        authorId).orElseThrow(() -> new ResourceNotFound(NOT_FOUND_AUTHOR_ID, authorId)
                ));
    }

    @PostMapping("/author/create")
    public Author createAuthor(@Valid @RequestBody Author author) {
        return authorDAO.save(author);
    }

    @PutMapping("/author/update/{authorId}")
    public Author updateAuthor(@PathVariable Long authorId, @Valid @RequestBody Author authorRequest) {
        return authorDAO.findById(authorId).map(author -> {
            author.setBirthDate(authorRequest.getBirthDate());
            author.setFirstName(authorRequest.getFirstName());
            author.setLastName(authorRequest.getLastName());
            author.setSex(authorRequest.getSex());
            author.setBooks(authorRequest.getBooks());
            author.setRewards(authorRequest.getRewards());
            return authorDAO.update(author, authorRequest);
        }).orElseThrow(() -> new ResourceNotFound(NOT_FOUND_AUTHOR_ID, authorId));
    }

    @DeleteMapping("/author/delete/{authorId}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long authorId) {
        return authorDAO.findById(authorId).map(author -> {
            authorDAO.delete(author);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFound(NOT_FOUND_AUTHOR_ID, authorId));
    }
}
