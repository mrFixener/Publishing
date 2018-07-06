package com.task.service.imp;

import com.task.model.Author;
import com.task.model.Book;
import com.task.repository.AuthorRepository;
import com.task.repository.BookRepository;
import com.task.service.AuthorDAO;
import com.task.service.RewardDAO;
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
public class AuthorDAOImp implements AuthorDAO {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RewardDAO rewardDAO;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    @Override
    public Author save(Author author) {
        Author saveAuthor = authorRepository.save(author);
        saveChild(saveAuthor, author);
        return saveAuthor;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Transactional
    @Override
    public void delete(Author author) {
        author.setBooks(null);
        author.setRewards(null);
        authorRepository.save(author);
        authorRepository.delete(author);
    }

    @Override
    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Author update(Author author, Author temp) {
        saveChild(author, temp);
        return authorRepository.save(author);
    }

    private void saveChild(Author author, Author authorRequest) {
        authorRequest.getRewards().stream().forEach(reward -> {
            reward.setAuthor(author);
            rewardDAO.save(reward);
        });

        authorRequest.getBooks().stream().forEach(book -> {
            Book relatedBook = bookRepository.findById(book.getId()).get();
            if (book.getAuthors() != null &&
                    relatedBook != null && relatedBook.getAuthors() != null) {
                book.getAuthors().addAll(relatedBook.getAuthors());
                book.getAuthors().add(author);
            }
            bookRepository.save(book);
        });
    }


}
