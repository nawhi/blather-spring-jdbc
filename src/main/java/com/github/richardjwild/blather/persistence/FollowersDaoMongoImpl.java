package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import org.bson.BsonString;
import org.bson.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class FollowersDaoMongoImpl extends MongoDao implements FollowersDao {

    public FollowersDaoMongoImpl(MongoClient client) {
        super(client, "followers");
    }

    @Override
    public void saveFollowees(String follower, Set<User> followees) {
        followees.stream().map(user -> user.name()).forEach(followee -> {
            Document entry = new Document("user", new BsonString(follower))
                                .append("follows", new BsonString(followee));
            if (!alreadyExists(entry)) {
                collection.insertOne(entry);
            }
        });
    }

    private boolean alreadyExists(Document entry) {
        return collection.count(entry) > 0;
    }

    @Override
    public Set<String> getFollowees(String follower) {
        Document filter = new Document("user", new BsonString(follower));
        Set<String> results = new HashSet<>();
        collection.find(filter)
                .projection(new Document("follows", 1))
                .forEach((Consumer<? super Document>) document ->
                        results.add(document.getString("follows")));
        return results;
    }
}
