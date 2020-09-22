SELECT result.*
FROM public.result result
WHERE result.result = (SELECT MAX(result.result) FROM public.result result);

SELECT MAX(result.result)
FROM public.result result
