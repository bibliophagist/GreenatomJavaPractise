create table jdbc_hibernate."users"
(
    id                serial                 not null,
    name              character varying(100) not null,
    surname           character varying(100),
    nickname          character varying(100),
    password          character varying(100) not null,
    registration_date date                   not null
);

create unique index user_id_uindex
    on jdbc_hibernate."users" (id);

alter table jdbc_hibernate."users"
    add constraint user_pk
        primary key (id);
