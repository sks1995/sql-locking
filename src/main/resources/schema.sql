--create schema  practice

create table if not exists practice_table
(
    id varchar(255) not null
        constraint id_pkey primary key,
    name varchar(255),
    count integer ,
    version integer
);


create table if not exists audit_table
(
    id varchar(255) not null
        constraint id_audit_pkey primary key,
    count integer
);
