--  Before you try the code in this file from the psql client, you need to create your database NBA-xxx and copy data from NBA to it. For example,
--  createdb NBA-tuy
--  pg_dump -t player_rs_career NBA | psql NBA-tuy
--  Note that those should be done under the Linux console. Then you can log into NBA-xxx and try the following scripts.

--  The following line only needs to be executed once before you do anything at all with pgplsql functions
--  CREATE LANGUAGE 'plpgsql';

-- function 1 declaration

CREATE OR REPLACE FUNCTION player_height_rank (firstname VARCHAR, lastname VARCHAR) RETURNS INTEGER AS $$
DECLARE
	rank INTEGER := 0;
	offset INTEGER := 0;
	temp FLOAT := NULL;
	r record;
BEGIN

FOR r in SELECT P.firstname, P.lastname, (P.h_feet * 30.48 + P.h_inches * 2.54) AS height
	FROM players AS P
	ORDER BY height DESC

	LOOP
		IF r.height = temp THEN
			offset := offset + 1;
		ELSE
			rank := rank + offset + 1;
			offset := 0;
			temp := r.height;
		END IF;

		IF r.firstname = $1 AND r.lastname = $2 THEN
			RETURN rank;
		END IF;
	END LOOP;

RETURN -1;

END;
$$ LANGUAGE plpgsql;

-- executing the above function
-- select * from player_height_rank(‘Reggie’, ‘Miller’);

-- function 2 declaration

CREATE OR REPLACE FUNCTION player_weight_var (tid VARCHAR, yr INTEGER) RETURNS FLOAT AS $$
DECLARE
   var FLOAT := 0.0;
BEGIN

SELECT INTO var (var_pop(P1.weight))
	FROM players AS P1, player_rs AS P2
	WHERE P1.ilkid = P2.ilkid AND P2.tid = $1 AND P2.year = $2;

IF var is NULL THEN
	RETURN -1.0;
ELSE
	RETURN var;
END IF;

END;
$$ LANGUAGE plpgsql;
