package com.task.service.test;

import com.task.TestApplication;
import com.task.domain.Genre;
import com.task.domain.Sex;
import com.task.model.Author;
import com.task.model.Book;
import com.task.model.Reward;
import com.task.service.AuthorDAO;
import com.task.service.BookDAO;
import com.task.service.RewardDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Dimon on 26.06.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class RewardDAOTest {
    @Autowired
    private RewardDAO rewardDAO;

    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private BookDAO bookDAO;

    private Author author;
    private Book book1, book2, book3;
    private Reward reward;

    @Before
    public void init() {
        author = new Author("Cay", "S. Hostmann", Sex.MALE, Date.valueOf(LocalDate.of(1959, 6, 16)));
        authorDAO.save(author);

        book1 = new Book("Scala for the impatient", "978-5-94074-920-2", Genre.PROGRAMMING);
        book1.getAuthors().add(author);
        bookDAO.save(book1);

        book2 = new Book("Core Java for the impatient", "123-456-789", Genre.PROGRAMMING);
        book2.getAuthors().add(author);
        bookDAO.save(book2);

        book3 = new Book("Java SE 8 for the Really impatient", "123-456-789-0", Genre.PROGRAMMING);
        book3.getAuthors().add(author);
        bookDAO.save(book3);

        reward = new Reward(2013, "America Award", author);
        reward = rewardDAO.save(reward);
        author.getRewards().add(reward);
    }

    @Test
    public void findByYearTest() {
        assertTrue(reward.equals(rewardDAO.findById(reward.getId()).get()));
        delete();
        assertFalse(rewardDAO.findById(reward.getId()).isPresent());
    }

    private void delete() {
        authorDAO.delete(author);
        bookDAO.delete(book3);
        bookDAO.delete(book2);
        bookDAO.delete(book1);
    }
}
