CREATE FUNCTION validation() RETURNS trigger AS $validation$
BEGIN
    IF NEW.competition_id IS NULL THEN
        RAISE EXCEPTION 'competition_id cannot be null';
END IF;
IF NEW.sportsman_id IS NULL THEN
        RAISE EXCEPTION 'sportsman_id cannot be null';
END IF;
IF NEW.result < 0 THEN
        RAISE EXCEPTION 'cannot have a negative result';
END IF;
RETURN NEW;
END;
$validation$ LANGUAGE plpgsql;

CREATE TRIGGER validation BEFORE INSERT OR UPDATE ON result
    FOR EACH ROW EXECUTE FUNCTION validation();

