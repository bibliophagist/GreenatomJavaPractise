UPDATE sportsman
SET rank = common_table.rank + 1
    FROM (Select *
          FROM public.sportsman
                   INNER JOIN public.result USING (sportsman_id)
                   INNER JOIN competition USING (competition_id)
          where world_record = result.result) common_table
where common_table.sportsman_id = sportsman.sportsman_id
