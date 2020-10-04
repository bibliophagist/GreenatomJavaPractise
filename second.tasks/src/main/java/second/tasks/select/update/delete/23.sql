SELECT const.athlete, sportsman.sportsman_name, const.show_result, result.result, const.in_city, result.city
FROM public.sportsman sportsman,
     public.result result,
     (WITH vals (athlete, show_result, in_city) AS ( VALUES ('Athlete ', 'showed the result of ', 'in the city of '))
SELECT *
FROM vals) as const
WHERE result.sportsman_id = sportsman.sportsman_id;


