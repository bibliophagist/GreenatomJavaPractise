SELECT max(rank) as lowest_rank
FROM public.sportsman sportsman
         INNER JOIN public.result USING (sportsman_id)
         INNER JOIN competition USING (competition_id)
WHERE world_record = result.result

