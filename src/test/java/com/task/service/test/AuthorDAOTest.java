package com.task.service.test;

import com.task.domain.Genre;
import com.task.domain.Sex;
import com.task.model.Author;
import com.task.model.Book;
import com.task.model.Reward;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

/**
 * Created by Dimon on 30.06.2018.
 */
public class AuthorDAOTest extends DAOTest {
    private Reward reward;
    private Author author2;
    private Book book, book2;

    @Before
    public void init() {
        super.init();
        author2 = new Author("Gary", "Cornell", Sex.MALE, Date.valueOf(LocalDate.of(1961, 9, 25)));
        getAuthorDAO().save(author2);

        book = new Book("Java. Library professional", "978-5-94074-920-3", Genre.PROGRAMMING);
        book.getAuthors().add(getAuthor());
        book.getAuthors().add(author2);
        getBookDAO().save(book);

        book2 = new Book("Java. Library professional. Volume 2", "978-5-94074-920-4", Genre.PROGRAMMING);
        book2.getAuthors().add(getAuthor());
        book2.getAuthors().add(author2);
        getBookDAO().save(book2);

        reward = new Reward(2016, "America Award", author2);
        getRewardDAO().save(reward);
    }

    @Test
    public void lazyInitTest() {
        assertTrue(book.getAuthors().containsAll(book2.getAuthors())
                && book2.getAuthors().containsAll(book.getAuthors())
                && book.getAuthors().size() == book2.getAuthors().size());
    }

    @After
    @Override
    public void delete() {
        getAuthorDAO().delete(author2);
        getRewardDAO().delete(reward);
        getBookDAO().delete(book2);
        getBookDAO().delete(book);
        super.delete();
    }
}
