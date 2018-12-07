package com.github.richardjwild.blather.persistence;

import com.mongodb.MongoClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserDaoMongoImplShould {

    private static MongoClient client;
    private static UserDaoMongoImpl dao;

    @BeforeClass
    public static void setUpBeforeClass() {
        client = new MongoClient();
        dao = new UserDaoMongoImpl(client);
    }

    @Before
    public void clearDatabase() {
        client.getDatabase("blather").getCollection("users").drop();
    }

    @Test
    public void save_and_retrieve_user() {
        String username = "alice";

        dao.saveUser(username);

        assertEquals(username, dao.findUser(username));
    }

    @Test
    public void return_null_if_user_not_found() {
        String username = "nobody";

        assertNull(dao.findUser("nobody"));
    }

    @AfterClass
    public static void tearDownAfterClass() {
        client.close();
    }
}
