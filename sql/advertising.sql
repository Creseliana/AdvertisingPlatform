DROP DATABASE IF EXISTS advertising;

CREATE DATABASE advertising;

USE advertising;

CREATE TABLE users
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    username          VARCHAR(50)           NOT NULL UNIQUE,
    password          VARCHAR(50)           NOT NULL,
    first_name        VARCHAR(50)           NOT NULL,
    last_name         VARCHAR(50)           NOT NULL,
    email             VARCHAR(50)           NOT NULL UNIQUE,
    phone_number      VARCHAR(15)           NOT NULL UNIQUE,
    is_active         BOOLEAN               NOT NULL,
    registration_date TIMESTAMP             NOT NULL,
    rating            DECIMAL(5, 4)         NULL,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    type VARCHAR(20)           NOT NULL UNIQUE,
    CONSTRAINT role_pk PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT user_role_user_id_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_role_role_id_roles_id_fk FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE ratings
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    level    VARCHAR(20)           NOT NULL,
    rater_id BIGINT                NOT NULL,
    user_id  BIGINT                NOT NULL,
    date     TIMESTAMP             NOT NULL,
    CONSTRAINT rating_pk PRIMARY KEY (id),
    CONSTRAINT ratings_rater_id_users_id_fk FOREIGN KEY (rater_id) REFERENCES users (id),
    CONSTRAINT ratings_user_id_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE chats
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    first_user_id  BIGINT                NOT NULL,
    second_user_id BIGINT                NOT NULL,
    CONSTRAINT chat_pk PRIMARY KEY (id),
    CONSTRAINT chats_first_user_id_users_id_fk FOREIGN KEY (first_user_id) REFERENCES users (id),
    CONSTRAINT chats_second_user_id_users_id_fk FOREIGN KEY (second_user_id) REFERENCES users (id)
);

CREATE TABLE messages
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    chat_id BIGINT                NOT NULL,
    user_id BIGINT                NOT NULL,
    date    TIMESTAMP             NOT NULL,
    message TINYTEXT              NOT NULL,
    is_read BOOLEAN               NOT NULL,
    CONSTRAINT message_pk PRIMARY KEY (id),
    CONSTRAINT messages_chat_id_chats_id_fk FOREIGN KEY (chat_id) REFERENCES chats (id),
    CONSTRAINT messages_user_id_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE categories
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50)           NOT NULL UNIQUE,
    CONSTRAINT category_pk PRIMARY KEY (id)
);

CREATE TABLE ads
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_id       BIGINT                NOT NULL,
    category_id   BIGINT                NOT NULL,
    title         VARCHAR(50)           NOT NULL,
    description   TINYTEXT              NOT NULL,
    price         DECIMAL(10, 2)        NULL,
    creation_date TIMESTAMP             NOT NULL,
    is_closed     BOOLEAN               NOT NULL,
    is_deleted    BOOLEAN               NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (id),
    CONSTRAINT ads_user_id_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT ads_category_id_categories_id_fk FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE images
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    ad_id   BIGINT                NOT NULL,
    name    VARCHAR(50)           NOT NULL,
    content BLOB                  NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (id),
    CONSTRAINT images_ad_id_ads_id_fk FOREIGN KEY (ad_id) REFERENCES ads (id)
);

CREATE TABLE comments
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT                NOT NULL,
    ad_id   BIGINT                NOT NULL,
    comment TINYTEXT              NOT NULL,
    date    TIMESTAMP             NOT NULL,
    CONSTRAINT comment_pk PRIMARY KEY (id),
    CONSTRAINT comments_user_id_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT comments_ad_id_ads_id_fk FOREIGN KEY (user_id) REFERENCES ads (id)
);

CREATE TABLE payments
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    ad_id      BIGINT                NOT NULL,
    start_date TIMESTAMP             NOT NULL,
    end_date   TIMESTAMP             NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (id),
    CONSTRAINT payments_ad_id_ads_id_fk FOREIGN KEY (ad_id) REFERENCES ads (id)
);