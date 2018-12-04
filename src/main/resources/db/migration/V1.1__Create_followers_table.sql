CREATE TABLE followers (
     follower VARCHAR(100) NOT NULL,
     followee VARCHAR(100) NOT NULL,
      PRIMARY KEY (follower,followee),
      FOREIGN KEY (follower) REFERENCES users(name),
      FOREIGN KEY (followee) REFERENCES users(name)
);
