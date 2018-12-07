package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.mongodb.MongoClient;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.KeyStore;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FollowersDaoMongoImplShould {

    private static MongoClient client;
    private static FollowersDao dao;

    @BeforeClass
    public static void setUpBeforeClass() {
        client = new MongoClient();
        dao = new FollowersDaoMongoImpl(client);
    }

    @Before
    public void clearDatabase() {
        client.getDatabase("blather").getCollection("followers").drop();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        client.close();
    }

    @Test
    public void not_insert_duplicate_records() {
        String user = "nick";
        String target = "dave";
        dao.saveFollowees(user, Set.of(new User(target)));
        dao.saveFollowees(user, Set.of(new User(target)));

        var collection = client.getDatabase("blather").getCollection("followers");
        var count = collection.count(new BsonDocument(user, new BsonString(target)));
        assertEquals(1, count);
    }
}
