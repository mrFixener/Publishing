package com.task.service.test;

import com.task.TestApplication;
import com.task.domain.Genre;
import com.task.domain.Sex;
import com.task.model.Author;
import com.task.model.Book;
import com.task.service.AuthorDAO;
import com.task.service.BookDAO;
import com.task.service.RewardDAO;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Dimon on 30.06.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public abstract class DAOTest {
    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private RewardDAO rewardDAO;

    private Author author;
    private Book book, book2;

    public void init() {
        author = new Author("Cay", "S. Hostmann", Sex.MALE, Date.valueOf(LocalDate.of(1959, 6, 16)));
        authorDAO.save(author);

        book = new Book("Scala for the impatient", "978-5-94074-920-2", Genre.PROGRAMMING);
        book.getAuthors().add(author);
        bookDAO.save(book);

        book2 = new Book("Core Java for the impatient", "123-456-789", Genre.PROGRAMMING);
        book2.getAuthors().add(author);
        bookDAO.save(book2);
    }

    abstract public void lazyInitTest();

    protected void delete() {
        getAuthorDAO().delete(author);
        getBookDAO().delete(book2);
        getBookDAO().delete(book);
    }

    public RewardDAO getRewardDAO() {
        return rewardDAO;
    }

    public BookDAO getBookDAO() {
        return bookDAO;
    }

    public AuthorDAO getAuthorDAO() {
        return authorDAO;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook2() {
        return book2;
    }

    public void setBook2(Book book2) {
        this.book2 = book2;
    }
}
