SELECT result.hold_date
FROM public.result result
WHERE LEFT(result.city, 1) = 'M'
