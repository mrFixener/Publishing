package com.task.service.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Dimon on 30.06.2018.
 */
public class BookDAOTest extends DAOTest {

    @Override
    @Before
    public void init() {
        super.init();
    }

    @Override
    @Test
    public void lazyInitTest() {
        assertTrue(getBook().getAuthors().size() == 1 &&
                getBook().getAuthors().size() == getBook2().getAuthors().size() &&
                getBook().getAuthors().get(0).equals(getBook2().getAuthors().get(0)));
    }

    @After
    @Override
    public void delete() {
        super.delete();
    }
}
