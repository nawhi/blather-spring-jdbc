package com.github.richardjwild.blather.persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

public class UserDaoMongoImpl implements UserDao {
    private MongoClient client;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public UserDaoMongoImpl(MongoClient client) {
        this.client = client;
        database = client.getDatabase("blather");
        collection = database.getCollection("collection");
    }

    @Override
    public void saveUser(String name) {
        collection.insertOne(new Document("name", name));
    }

    @Override
    public String findUser(String name) {
        BsonDocument filter = new BsonDocument("name", new BsonString(name));
        Document firstResult = collection.find(filter).first();
        return firstResult.getString("name");
    }
}
