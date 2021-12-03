create sequence hibernate_sequence start 1 increment 1;
create table Publisher (id int8 not null, createdDate timestamp, name varchar(255), primary key (id));
create table t_artists (id int8 not null, bio varchar(3000), created_date timestamp not null, name varchar(100) not null, primary key (id));
create table t_customers (id int8 not null, created_date timestamp not null, e_mail varchar(255) not null, first_name varchar(50) not null, last_name varchar(50) not null, primary key (id));
