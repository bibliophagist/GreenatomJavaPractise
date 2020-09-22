UPDATE sportsman
SET country = 'Russia'
WHERE country = 'USA'
  and rank = ANY (ARRAY [1,2])
