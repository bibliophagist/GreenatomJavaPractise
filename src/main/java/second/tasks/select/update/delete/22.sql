SELECT sportsman.sportsman_name
FROM public.sportsman sportsman,
     public.competition competition
WHERE competition.world_record = 245
  and sportsman.year_of_birth >= extract(year from competition.set_date)
