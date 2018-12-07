package com.github.richardjwild.blather.persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

public class UserDaoMongoImpl extends MongoDao implements UserDao {

    private MongoCollection<Document> collection;

    public UserDaoMongoImpl(MongoClient client) {
        super(client, "users");
    }

    @Override
    public void saveUser(String name) {
        collection.insertOne(new Document("name", name));
    }

    @Override
    public String findUser(String name) {
        BsonDocument filter = new BsonDocument("name", new BsonString(name));
        Document firstResult = collection.find(filter).first();
        if (firstResult != null) {
            return firstResult.getString("name");
        }
        return null;
    }
}
