package com.task.controller.test;

import com.task.TestApplication;
import com.task.domain.AuthorModel;
import com.task.domain.Genre;
import com.task.domain.Sex;
import com.task.model.Author;
import com.task.model.Book;
import com.task.service.AuthorDAO;
import com.task.service.BookDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.task.util.Util.jsonString;
import static com.task.util.Util.stringToJson;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Dimon on 04.07.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = TestApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
public class AuthorControllerTest {
    public static final String AUTHOR_SHORT_ENDPOINT = "/author/info/short/";
    public static final Long NOT_EXIST_ID = -1l;

    private Author author;
    private Book book;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private BookDAO bookDAO;

    @Before
    public void init() {
        author = new Author("TestName", "TestLastName", Sex.FEMALE, Date.valueOf(LocalDate.of(1959, 6, 21)));
        book = new Book("Test book", "978-5-94074-920-6", Genre.ADVENTURE);
    }

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "${auth.users.psws}")
    public void authorShouldBeExist() throws Exception {
        author = authorDAO.save(author);
        mockMvc.perform(get("/authors")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("content[?(@.id ==" + " " + author.getId() + ")]").exists());
        authorDAO.delete(author);
    }

    @Test
    @WithMockUser(username = "${auth.admins.login}", password = "${auth.admins.psws}", roles = "ADMIN")
    public void authorShouldBeSaved() throws Exception {
        MvcResult result = mockMvc.perform(post("/author/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString(author))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").isNotEmpty())
                .andReturn();
        Author savedAuthor = stringToJson(result.getResponse().getContentAsString(), Author.class);
        assertNotNull(savedAuthor);

        authorDAO.delete(savedAuthor);
    }

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "${auth.users.psws}")
    public void userHasNotAccessToCreateAuthor() throws Exception {
        mockMvc.perform(post("/author/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString(author))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "${auth.users.psws}")
    public void dataFromShortAuthorNotFound() throws Exception {
        mockMvc.perform(get(AUTHOR_SHORT_ENDPOINT + NOT_EXIST_ID))
                .andDo(print()).
                andExpect(status()
                        .isNotFound());
    }

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "${auth.users.psws}")
    public void dataFromShortAuthorViewCorrect() throws Exception {
        author = authorDAO.save(author);
        MvcResult result = mockMvc.perform(get(AUTHOR_SHORT_ENDPOINT + author.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").isNotEmpty())
                .andReturn();
        AuthorModel resultAuthorModel = stringToJson(result.getResponse().getContentAsString(), AuthorModel.class);
        assertNotNull(resultAuthorModel);
        AuthorModel generatedAuthorModel = new AuthorModel(author);
        assertEquals(generatedAuthorModel, resultAuthorModel);
        authorDAO.delete(author);
    }

    @Test
    @WithMockUser(username = "${auth.admins.login}", password = "${auth.admins.psws}", roles = "ADMIN")
    public void cantUpdateAuthorNotFound() throws Exception {
        mockMvc.perform(put("/author/update/" + NOT_EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString(author)))
                .andDo(print()).
                andExpect(status()
                        .isNotFound());
    }

    @Test
    @WithMockUser(username = "${auth.admins.login}", password = "${auth.admins.psws}", roles = "ADMIN")
    public void adminShouldBeUpdatedAuthor() throws Exception {
        final String newBookTitle = "New book title";
        book = bookDAO.save(book);

        author = authorDAO.save(author);
        book.setTitle(newBookTitle);
        Map<String, List<Book>> mapBook = new HashMap<>();
        mapBook.put("books", Arrays.asList(book));

        mockMvc.perform(put("/author/update/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString(mapBook))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("@.books[?(@.title  == '" + newBookTitle + "')]").exists());
        authorDAO.delete(author);
        bookDAO.delete(book);
    }

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "${auth.users.psws}")
    public void userCantUpdatedAuthor() throws Exception {
        mockMvc.perform(put("/author/update/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString(book))
        )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "${auth.admins.login}", password = "${auth.admins.psws}", roles = "ADMIN")
    public void cantDeleteAuthorNotFound() throws Exception {
        mockMvc.perform(delete("/author/delete/" + NOT_EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    @WithMockUser(username = "${auth.admins.login}", password = "${auth.admins.psws}", roles = "ADMIN")
    public void afterDeleteCalledNoAuthor() throws Exception {
        author = authorDAO.save(author);

        mockMvc.perform(delete("/author/delete/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
        assertFalse(authorDAO.findById(author.getId()).isPresent());
    }

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "${auth.users.psws}")
    public void userCantDeleteAuthor() throws Exception {
        mockMvc.perform(delete("/author/delete/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
