SELECT competition.competition_name
FROM public.competition competition
WHERE position('jump' in competition.competition_name) != 0
