SELECT sportsman_name, avg(result.result)
FROM public.result result
         INNER JOIN public.sportsman sportsman USING (sportsman_id)
GROUP BY result.sportsman_id, sportsman.sportsman_name
ORDER BY result.sportsman_id
