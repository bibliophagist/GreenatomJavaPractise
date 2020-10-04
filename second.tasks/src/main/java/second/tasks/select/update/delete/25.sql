SELECT distinct portsman_id, sportsman_name, rank, year_of_birth, personal_record, country
FROM public.sportsman sportsman
         INNER JOIN public.result USING (sportsman_id)
         INNER JOIN competition USING (competition_id)
WHERE personal_record = world_record
