# Немного неправильная структура таблицы для этого задания, поэтому сделаю немного другое:
# Выведите названия соревнований, на которых было установлено максимальное количество результатов.
# В WHERE не видит count_results из FROM
SELECT competition.competition_name
FROM public.competition competition,
     (SELECT result.competition_id, count(competition_id) as count_ids
      FROM public.result result
      GROUP BY result.competition_id) as count_results
WHERE competition.competition_id = count_results.competition_id
  and count_ids = (SELECT max(count_ids)
                   FROM (SELECT result.competition_id, count(competition_id) as count_ids
                         FROM public.result result
                         GROUP BY result.competition_id) as count_results)

