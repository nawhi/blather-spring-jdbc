package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.mongodb.MongoClient;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.junit.*;

import java.security.KeyStore;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FollowersDaoMongoImplShould {

    private static MongoClient client;
    private static FollowersDao dao;

    @BeforeClass
    public static void setUpBeforeClass() {
        client = new MongoClient();
        dao = new FollowersDaoMongoImpl(client);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        client.close();
    }

    @Before
    public void setUp() {
        clearDatabase();
    }

    @After
    public void tearDown() {
        clearDatabase();
    }

    private void clearDatabase() {
        client.getDatabase("blather").getCollection("followers").drop();
    }

    @Test
    public void save_and_retrieve_followers() {
        dao.saveFollowees("alice", Set.of(new User("bob")));
        Set<String> followees = dao.getFollowees("alice");
        assertEquals(1, followees.size());
        assertTrue(followees.contains("bob"));
    }

    @Test
    public void not_insert_duplicate_records() {
        String user = "nick";
        String target = "dave";
        dao.saveFollowees(user, Set.of(new User(target)));
        dao.saveFollowees(user, Set.of(new User(target)));

        var collection = client.getDatabase("blather").getCollection("followers");
        BsonDocument entry = new BsonDocument("user", new BsonString(user))
                                    .append("follows", new BsonString(target));
        var count = collection.count(entry);
        assertEquals(1, count);
    }
}
