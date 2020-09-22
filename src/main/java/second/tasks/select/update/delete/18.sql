SELECT sportsman.sportsman_name
FROM public.sportsman sportsman, public.result result
WHERE sportsman.personal_record = result.result and result.hold_date = date('2018-11-10')
