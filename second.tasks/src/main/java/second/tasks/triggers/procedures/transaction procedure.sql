create or replace procedure transfer(sender int,
                                     receiver int,
                                     amount dec)
    language plpgsql
as
$$
DECLARE
    a_count integer;
    b_count integer;
begin
    update result
    set result = result - amount
    where sportsman_id = sender;
    GET DIAGNOSTICS a_count = ROW_COUNT;

    update result
    set result = result + amount
    where sportsman_id = receiver;
    GET DIAGNOSTICS b_count = ROW_COUNT;

    if (a_count = 0 or b_count = 0) THEN
            raise exception 'the transaction could not be completed';
    end if;


    commit;
end;
$$
