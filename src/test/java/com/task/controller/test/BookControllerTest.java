package com.task.controller.test;

import com.task.TestApplication;
import com.task.domain.Genre;
import com.task.domain.Sex;
import com.task.model.Author;
import com.task.model.Book;
import com.task.service.AuthorDAO;
import com.task.service.BookDAO;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

import static com.task.controller.test.AuthorControllerTest.NOT_EXIST_ID;
import static com.task.util.Util.jsonString;
import static com.task.util.Util.stringToJson;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Dimon on 05.07.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = TestApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookControllerTest {
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
        author = new Author("BookControllerName", "BookControllerLastName", Sex.FEMALE, Date.valueOf(LocalDate.of(1959, 6, 21)));
        book = new Book("BookControllerTest", "22222222", Genre.ADVENTURE);
    }

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "${auth.users.psws}")
    public void bookShouldBeExist() throws Exception {
        book = bookDAO.save(book);
        mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("content[?(@.id ==" + " " + book.getId() + ")]").exists());
        bookDAO.delete(book);
    }

    @Test
    @WithMockUser(username = "${auth.admins.login}", password = "${auth.admins.psws}", roles = "ADMIN")
    public void bookShouldBeSaved() throws Exception {
        MvcResult result = mockMvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString(book))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").isNotEmpty())
                .andReturn();
        Book savedBook = stringToJson(result.getResponse().getContentAsString(), Book.class);
        assertNotNull(savedBook);
        bookDAO.delete(savedBook);
    }

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "${auth.users.psws}")
    public void userHasNotAccessToCreateBook() throws Exception {
        mockMvc.perform(post("/author/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString(book))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "${auth.admins.login}", password = "${auth.admins.psws}", roles = "ADMIN")
    public void adminShouldBeUpdatedBook() throws Exception {
        final String newAuthorName = "New Name";
        book = bookDAO.save(book);

        author = authorDAO.save(author);
        author.setFirstName(newAuthorName);
        Map<String, List<Author>> mapAuthor = new HashMap<>();
        mapAuthor.put("authors", Arrays.asList(author));

        mockMvc.perform(put("/book/update/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString(mapAuthor))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("@.authors[?(@.firstName  == '" + newAuthorName + "')]").exists());
        authorDAO.delete(author);
        bookDAO.delete(book);
    }

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "${auth.users.psws}")
    public void userCantUpdatedBook() throws Exception {
        mockMvc.perform(put("/book/update/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString(author))
        )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "${auth.admins.login}", password = "${auth.admins.psws}", roles = "ADMIN")
    public void cantUpdateBookNotFound() throws Exception {
        mockMvc.perform(put("/book/update/" + NOT_EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString(author)))
                .andDo(print()).
                andExpect(status()
                        .isNotFound());
    }

    @Test
    @WithMockUser(username = "${auth.admins.login}", password = "${auth.admins.psws}", roles = "ADMIN")
    public void afterDeleteCalledNoBook() throws Exception {
        book = bookDAO.save(book);

        mockMvc.perform(delete("/book/delete/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
        assertFalse(bookDAO.findById(book.getId()).isPresent());
    }

    @Test
    @WithMockUser(username = "${auth.admins.login}", password = "${auth.admins.psws}", roles = "ADMIN")
    public void cantDeleteBookNotFound() throws Exception {
        mockMvc.perform(delete("/book/delete/" + NOT_EXIST_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                        .isNotFound());
    }

    @Test
    @WithMockUser(username = "${auth.users.login}", password = "${auth.users.psws}")
    public void userCantDeleteBook() throws Exception {
        mockMvc.perform(delete("/book/delete/" + author.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
