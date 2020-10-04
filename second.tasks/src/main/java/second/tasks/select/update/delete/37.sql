UPDATE result
SET result = result.result + 2
WHERE extract(year from hold_date) = 2016
  and result.result >= 45
