SELECT country_results.country
FROM (SELECT sportsman.country, count(sportsman.country) as count_results
      FROM public.sportsman sportsman
               INNER JOIN public.result USING (sportsman_id)
      group by sportsman.country
      order by count_results desc) country_results
LIMIT 1
