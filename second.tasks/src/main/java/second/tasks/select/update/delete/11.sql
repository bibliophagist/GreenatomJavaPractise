SELECT sportsman.sportsman_name
FROM public.sportsman sportsman
WHERE LEFT(sportsman.sportsman_name, 1) = 'M'
  and "right"(cast(sportsman.year_of_birth as text), 1) != '0'
