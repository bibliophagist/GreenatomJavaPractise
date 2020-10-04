SELECT distinct sportsman.year_of_birth
FROM public.sportsman sportsman,
     (SELECT result.competition_id, avg(result.result) as average
      FROM public.result result
               INNER JOIN public.sportsman sportsman USING (sportsman_id)
      GROUP BY result.competition_id) as average
         INNER JOIN result USING (competition_id)
WHERE result.result >= average
  and result.sportsman_id = sportsman.sportsman_id
