CREATE TABLE public.competition
(
    competition_id   bigint NOT NULL,
    competition_name character varying(50),
    world_record     bigint,
    set_date         date,
    PRIMARY KEY (competition_id)
);
