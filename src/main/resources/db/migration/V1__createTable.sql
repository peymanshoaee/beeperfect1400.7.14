

CREATE TABLE bp_user(
  id BIGSERIAL PRIMARY KEY,
  username    varchar(50)   NOT NULL,
  password    varchar(500)  NOT NULL,
  token       varchar(500),
  first_name  varchar(200)  DEFAULT NULL,
  last_name   varchar(200)  DEFAULT NULL,
  nick_name   varchar(200)  DEFAULT NULL,
  url         varchar(2000) DEFAULT NULL,
  verify_code  varchar(10)   DEFAULT NULL,
  image       bytea                       ,
  enabled     BOOLEAN     DEFAULT FALSE ,
  business    BOOLEAN     DEFAULT FALSE,
  tos         BOOLEAN     DEFAULT FALSE
);



CREATE TABLE bp_authority (
  id BIGSERIAL PRIMARY KEY,
  name varchar(1000)  NOT NULL,
  code varchar(999)   DEFAULT NULL
);



CREATE TABLE bp_user_authority(
        id BIGSERIAL PRIMARY KEY,
        user_id  BIGINT not null references bp_user(id),
        authority_id  BIGINT not null references bp_authority(id),
        create_date timestamp DEFAULT NULL

);


CREATE TABLE bp_status (
  id BIGSERIAL PRIMARY KEY,
  code        varchar(100)       DEFAULT NULL,
  description varchar(1000)    DEFAULT NULL
);

CREATE TABLE bp_category (
  id BIGSERIAL PRIMARY KEY,
  code        varchar(100)       DEFAULT NULL,
  title       varchar(1000)      DEFAULT NULL
);

CREATE TABLE bp_request (
  id BIGSERIAL PRIMARY KEY,
  title             varchar(3000)       DEFAULT NULL,
  description       varchar(5000)      DEFAULT NULL,
  minAmount          BIGINT      DEFAULT NULL,
  maxAmount          BIGINT      DEFAULT NULL,
  create_date        date      DEFAULT NULL,
  user_id   BIGINT not null references bp_user (id)
);

CREATE TABLE bp_request_category(
        id BIGSERIAL PRIMARY KEY,
        request_id  BIGINT not null references bp_request(id),
        category_id  BIGINT not null references bp_category(id),
        create_date timestamp DEFAULT NULL

);


