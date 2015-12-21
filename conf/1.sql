CREATE SEQUENCE user_id_seq;

CREATE TABLE userinfo (
  userid INTEGER NOT NULL DEFAULT NEXTVAL('user_id_seq') PRIMARY KEY,
  username VARCHAR(20) NOT NULL UNIQUE,
  userpwd VARCHAR(20) NOT NULL,
  userage NUMBER,
  usergender NUMBER,
  usermail VARCHAR(30) NOT NULL,
  usertel VARCHAR(15),
  usergrade INTEGER,
  addrid NUMBER,
  balance NUMBER(10,2),
  problem VARCHAR(30),
  answer VARCHAR(30)
);

SELECT * FROM userinfo;
INSERT INTO userinfo (username, userpwd, usergender, usermail) VALUES ('admin', 'admin', 1, 'admin@bookshop.com');