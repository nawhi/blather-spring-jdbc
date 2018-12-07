package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.mongodb.MongoClient;
import org.bson.BsonString;
import org.bson.Document;

import java.util.Set;

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
        return null;
    }
}
