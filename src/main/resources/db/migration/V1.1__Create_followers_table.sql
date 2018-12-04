create table followers (
     follower varchar(100) not null,
     followee varchar(100) not null,
      PRIMARY KEY (follower,followee),
      FOREIGN KEY (follower) REFERENCES users(name),
      FOREIGN KEY (followee) REFERENCES users(name)
);
