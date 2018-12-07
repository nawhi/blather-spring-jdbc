package com.github.richardjwild.blather.persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDao {
    protected MongoClient client;
    protected final MongoDatabase database;
    protected final MongoCollection<Document> collection;

    public MongoDao(MongoClient client, String collectionName) {
        this.client = client;
        database = client.getDatabase("blather");
        collection = database.getCollection(collectionName);
    }
}
