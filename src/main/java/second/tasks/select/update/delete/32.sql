SELECT distinct sportsman_name, (extract(year from current_date) - year_of_birth) as age
FROM public.sportsman
         INNER JOIN public.result USING (sportsman_id)
WHERE city = 'Moscow'
