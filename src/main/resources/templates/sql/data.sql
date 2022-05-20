CREATE TABLE users
(
    id bigserial primary key ,
    username varchar(64) not null unique ,
    password varchar(128) not null ,
--     email varchar(64) not null unique ,
    created_at timestamp ,
    role varchar(32) not null
);

create table token
(
    id bigserial primary key ,
    token varchar(264),
    user_id bigint references users (id),
    expiry_date timestamp
);

create table refresh
(
    id bigserial primary key ,
    token varchar(264) ,
    created_date timestamp
);

create table post
(
    id serial primary key ,
    title varchar(64) not null unique ,
    description text ,
    votes int ,
    created_date timestamp ,
    image_url varchar(128) unique ,
    user_id bigint references users (id)
);

create table vote
(
    id bigserial primary key ,
    type varchar(32),
    post_id int references post (id),
    user_id bigint references users (id)
);

create table comment
(
    id serial primary key ,
    message varchar(264) not null ,
    created_date timestamp ,
    user_id bigint references users (id),
    post_id int references post (id)
);

create table tag
(
    id serial primary key ,
    title varchar(32) not null ,
    post_id int references post (id)
);

create table post_tag
(
    id serial primary key ,
    post_id int references post (id) ,
    tag_id int references tag (id)
);

-- Insert into post (title, description)
-- values('test', 'test');

-- Insert into users (title, text)
-- values('test', 'test');

