package com.github.richardjwild.blather.persistence;

import com.mongodb.MongoClient;

public class UserDaoMongoImpl implements UserDao {
    public UserDaoMongoImpl(MongoClient client) {

    }

    @Override
    public void saveUser(String name) {

    }

    @Override
    public String findUser(String name) {
        return null;
    }
}
