create table FOLLOWERS (
     FOLLOWER varchar(100) not null,
     FOLLOWEE varchar(100) not null,
      PRIMARY KEY (FOLLOWER,FOLLOWEE),
      FOREIGN KEY (FOLLOWER) REFERENCES USERS(name),
      FOREIGN KEY (FOLLOWEE) REFERENCES USERS(name)
);
