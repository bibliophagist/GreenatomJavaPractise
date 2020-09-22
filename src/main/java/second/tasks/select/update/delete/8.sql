SELECT result.city
FROM public.result result
WHERE result.result = any (ARRAY [200, 300, 600, 800])
