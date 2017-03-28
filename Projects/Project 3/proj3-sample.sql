--  Before you try the code in this file from the psql client, you need to create your database NBA-xxx and copy data from NBA to it. For example,
--  createdb NBA-tuy
--  pg_dump -t player_rs_career NBA | psql NBA-tuy
--  Note that those should be done under the Linux console. Then you can log into NBA-xxx and try the following scripts.

--  The following line only needs to be executed once before you do anything at all with pgplsql functions
-- CREATE LANGUAGE 'plpgsql';


-- function 1

CREATE OR REPLACE FUNCTION get_height (firstn VARCHAR, lastn VARCHAR) RETURNS float AS $$
DECLARE
   height float := 0.0;
BEGIN

select into height (P.h_feet *12*2.54 + P.h_inches *2.54) 
from players as P 
where P.firstname=$1 and P.lastname=$2;

--check for null result
if height IS NULL
THEN
return 0.0;

ELSE
return height;

END IF;

END;
$$ LANGUAGE plpgsql;

-- calling the function, try more test cases by changing the name
-- select * from get_height('Michael', 'Jordan');


-- function 2

CREATE OR REPLACE FUNCTION get_coach_rank (year INTEGER, firstn VARCHAR, lastn VARCHAR) RETURNS float AS $$
DECLARE
   rank INTEGER := 0;
   offset INTEGER := 0;
   tempValue INTEGER :=NULL;
   r record;
BEGIN

FOR r IN SELECT (CS.season_win + CS.playoff_win - CS.season_loss - CS.playoff_loss) as netWin,CS.firstname, CS.lastname , CS.year
	FROM coaches_season CS
	WHERE CS.year = $1
	ORDER BY (CS.season_win + CS.playoff_win - CS.season_loss - CS.playoff_loss) DESC,CS.firstname, CS.lastname , CS.year
    LOOP
    
	IF r.netWin = tempValue then
		offset := offset +1;
	ELSE
		rank := rank + offset + 1;
		offset := 0;
		tempValue := r.netWin;
	END IF;

        IF r. lastname = $3 AND r.firstname = $2 THEN
		RETURN rank;
	END IF;
    END LOOP;

--not in DB    
RETURN -1;

END;
$$ LANGUAGE plpgsql;

-- executing the above function
-- select * from get_coach_rank(1992, ‘Phil’, ‘Jackson’);


