SELECT competition.competition_name
FROM public.competition competition
WHERE competition.world_record = 15
  and competition.set_date != date '2010-02-12'
