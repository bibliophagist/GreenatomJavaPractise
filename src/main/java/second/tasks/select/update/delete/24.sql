SELECT sportsman.sportsman_name
FROM public.sportsman sportsman,
     (SELECT avg(sportsman.rank) as rank FROM public.sportsman) as average
WHERE sportsman.rank >= average.rank
