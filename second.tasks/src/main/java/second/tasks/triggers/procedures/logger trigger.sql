CREATE TABLE logger_table(
                          operation         char(1)   NOT NULL,
                          stamp             timestamp NOT NULL,
                          competition_id    bigint,
                          sportsman_id      bigint,
                          result            bigint,
                          city              character varying(50),
                          hold_date         date
);

CREATE FUNCTION logger_result() RETURNS TRIGGER AS $logger_result$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO logger_table SELECT 'D', now(), OLD.*;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO logger_table SELECT 'U', now(), NEW.*;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO logger_table SELECT 'I', now(), NEW.*;
        END IF;
        RETURN NULL;
    END;
$logger_result$ LANGUAGE plpgsql;

CREATE TRIGGER logger_result
    AFTER INSERT OR UPDATE OR DELETE ON result
    FOR EACH ROW EXECUTE FUNCTION logger_result();
