DELETE
FROM result
WHERE hold_date in (SELECT hold_date
                    FROM public.sportsman sportsman
                             INNER JOIN public.result USING (sportsman_id)
                    where year_of_birth = 2001)
