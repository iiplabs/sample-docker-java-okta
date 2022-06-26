use dale-auth;

commit;

CREATE TABLE scopes (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  inet_id varchar(50) NOT NULL,
  scope_name varchar(50) NOT NULL,
  created datetime,
  updated datetime,
  created_by varchar(50),
  updated_by varchar(50),
  optlock int unsigned DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE KEY inet_id_UNIQUE (inet_id),
  UNIQUE KEY scope_name_UNIQUE (scope_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE users (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  inet_id varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  password varchar(50),
  enabled boolean DEFAULT false,
  created datetime,
  updated datetime,
  created_by varchar(50),
  updated_by varchar(50),
  optlock int unsigned DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE KEY inet_id_UNIQUE (inet_id),
  KEY email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

commit;

CREATE TABLE user_scopes (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  scope_user bigint unsigned,
  scope bigint unsigned,
  inet_id varchar(50) NOT NULL,
  created datetime,
  updated datetime,
  created_by varchar(50),
  updated_by varchar(50),
  optlock int unsigned DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE KEY inet_id_UNIQUE (inet_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

commit;

alter table user_scopes add foreign key (scope)
REFERENCES scopes(id) ON DELETE CASCADE ON UPDATE CASCADE;
alter table user_scopes add foreign key (scope_user)
REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE;

commit;
