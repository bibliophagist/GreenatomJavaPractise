SELECT competition.competition_name, competition.set_date
FROM public.competition competition
WHERE competition.set_date = date '2010-05-15' or competition.set_date = date '2010-05-12'
