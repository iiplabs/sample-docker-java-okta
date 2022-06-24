use dale-auth;

commit;

insert into scopes(inet_id, scope_name) values(uuid(), 'admin');

commit;

insert into users(inet_id, email, enabled) values(uuid(), 'admin@online.com', true);

commit;

insert into user_scopes(user, scope, inet_id) values(1, 1, uuid());

commit;
