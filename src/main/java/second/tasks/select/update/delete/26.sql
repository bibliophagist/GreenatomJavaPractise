SELECT count(distinct sportsman_name)
FROM public.sportsman sportsman
         INNER JOIN public.result USING (sportsman_id)
         INNER JOIN competition USING (competition_id)
WHERE position('Ivanov' in sportsman_name) != 0
  and position('jump' in competition_name) != 0

