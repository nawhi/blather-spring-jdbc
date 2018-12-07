package com.github.richardjwild.blather.persistence;

import com.mongodb.MongoClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserDaoMongoImplShould {

    private static MongoClient client;
    private static UserDaoMongoImpl dao;

    @BeforeClass
    public static void setUpBeforeClass() {
        client = new MongoClient();
        dao = new UserDaoMongoImpl(client);
    }

    @Test
    public void save_and_retrieve_user() {
        String username = "alice";

        dao.saveUser(username);

        assertEquals(username, dao.findUser(username));
    }

    @AfterClass
    public static void tearDownAfterClass() {
        client.close();
    }
}
