insert into entities(id) values(1);
insert into users(username, password, email, enabled, id) values("admin", "admin", "admin@pinecone.cc", 1, 1);
insert into entities(id) values(2);
insert into authorities(username, authority, user_id, id) values("admin", "ROLE_ADMIN", 1, 2);