SELECT sportsman.sportsman_name
FROM public.sportsman sportsman
WHERE sportsman.year_of_birth = 2000
  and NOT (sportsman.rank = any (ARRAY [8, 9]))
