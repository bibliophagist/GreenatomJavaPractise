SELECT distinct result.city
FROM public.result result
         INNER JOIN competition USING (competition_id)
WHERE world_record = result.result
