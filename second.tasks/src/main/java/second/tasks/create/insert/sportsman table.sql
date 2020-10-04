CREATE TABLE public.sportsman
(
    sportsman_id    bigint,
    sportsman_name  character varying(100),
    rank            integer,
    year_of_birth   integer,
    personal_record bigint,
    country         character varying(50)
);

ALTER TABLE public.sportsman
    ALTER COLUMN sportsman_id SET NOT NULL;
ALTER TABLE public.sportsman
    ADD PRIMARY KEY (sportsman_id);
