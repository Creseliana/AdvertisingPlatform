DROP DATABASE IF EXISTS advertising;

CREATE DATABASE advertising;

USE advertising;

CREATE TABLE users
(
	id BIGINT AUTO_INCREMENT NOT NULL,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL,
    registration_date TIMESTAMP NOT NULL,
    rating DECIMAL(5, 4) NULL,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

CREATE TABLE roles
(
	id BIGINT AUTO_INCREMENT NOT NULL,
    type VARCHAR(20) NOT NULL UNIQUE, 
    CONSTRAINT role_pk PRIMARY KEY (id)
);

CREATE TABLE user_role
(
	user_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL,
    CONSTRAINT user_role_user_id_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_role_role_id_roles_id_fk FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE user_rating
(
	user_id BIGINT NOT NULL,
    rating TINYINT NOT NULL,
    CONSTRAINT user_rating_user_id_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE chats
(
	id BIGINT AUTO_INCREMENT NOT NULL,
    unread_count INT NULL,
    CONSTRAINT chat_pk PRIMARY KEY (id)
);

CREATE TABLE messages
(
	id BIGINT AUTO_INCREMENT NOT NULL,
    chat_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    date TIMESTAMP NOT NULL,
    message TINYTEXT NOT NULL,
    is_read BOOLEAN NOT NULL,
    CONSTRAINT message_pk PRIMARY KEY (id),
    CONSTRAINT messages_chat_id_chats_id_fk FOREIGN KEY (chat_id) REFERENCES chats (id),
    CONSTRAINT messages_sender_id_users_id_fk FOREIGN KEY (sender_id) REFERENCES users (id)
);

CREATE TABLE chat_message
(
	chat_id BIGINT NOT NULL,
    message_id BIGINT NOT NULL,
    CONSTRAINT chat_message_chat_id_chats_id_fk FOREIGN KEY (chat_id) REFERENCES chats (id),
    CONSTRAINT chat_message_message_id_messages_id_fk FOREIGN KEY (message_id) REFERENCES messages (id)
);

CREATE TABLE user_chat
(
	user_id BIGINT NOT NULL,
    chat_id BIGINT NOT NULL,
    CONSTRAINT user_chat_user_id_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_chat_chat_id_chats_id_fk FOREIGN KEY (chat_id) REFERENCES chats (id)
);

CREATE TABLE comments
(
	id BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT NOT NULL,
    comment TINYTEXT NOT NULL,
    date TIMESTAMP NOT NULL,
    CONSTRAINT comment_pk PRIMARY KEY (id),
    CONSTRAINT comments_user_id_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE categories
(
	id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT category_pk PRIMARY KEY (id)
);

CREATE TABLE images
(
	id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    content BLOB NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (id)
);

CREATE TABLE payments
(
	id BIGINT AUTO_INCREMENT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (id)
);

CREATE TABLE ads
(
	id BIGINT AUTO_INCREMENT NOT NULL,
    title VARCHAR(50) NOT NULL,
    description TINYTEXT NOT NULL,
    price DECIMAL(10, 2) NULL,
    creation_date TIMESTAMP NOT NULL,
    is_closed BOOLEAN NOT NULL,
    is_deleted BOOLEAN NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (id)
);

CREATE TABLE ad_comment
(
	ad_id BIGINT NOT NULL,
    comment_id BIGINT NOT NULL,
    CONSTRAINT ad_comment_ad_id_ads_id_fk FOREIGN KEY (ad_id) REFERENCES ads (id),
    CONSTRAINT ad_comment_comment_id_comments_id_fk FOREIGN KEY (comment_id) REFERENCES comments (id)
);

CREATE TABLE ad_category
(
	ad_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    CONSTRAINT ad_category_ad_id_ads_id_fk FOREIGN KEY (ad_id) REFERENCES ads (id),
    CONSTRAINT ad_category_category_id_categories_id_fk FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE ad_image
(
	ad_id BIGINT NOT NULL,
    image_id BIGINT NOT NULL,
    CONSTRAINT ad_image_ad_id_ads_id_fk FOREIGN KEY (ad_id) REFERENCES ads (id),
    CONSTRAINT ad_image_image_id_images_id_fk FOREIGN KEY (image_id) REFERENCES images (id)
);

CREATE TABLE ad_payment
(
	ad_id BIGINT NOT NULL,
    payment_id BIGINT NOT NULL,
    CONSTRAINT ad_payment_ad_id_ads_id_fk FOREIGN KEY (ad_id) REFERENCES ads (id),
    CONSTRAINT ad_payment_payment_id_payments_id_fk FOREIGN KEY (payment_id) REFERENCES payments (id)
);

CREATE TABLE user_ad
(
	user_id BIGINT NOT NULL,
    ad_id BIGINT NOT NULL,
    CONSTRAINT user_ad_user_id_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_ad_ad_id_ads_id_fk FOREIGN KEY (ad_id) REFERENCES ads (id)
);








