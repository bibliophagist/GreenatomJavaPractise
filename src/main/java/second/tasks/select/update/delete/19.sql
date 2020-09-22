SELECT competition.competition_name, result.hold_date
FROM public.competition competition,
     public.result result
WHERE competition.world_record = result.result
  and competition.competition_id = result.competition_id
  and result.hold_date = date('2010-05-15')
  and result.city = 'Moscow'
