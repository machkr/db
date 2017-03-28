-- change xxx in this line to your NetID

\o proj2-kramerm.out

-- Put your SQL statement under the following lines:

--1. Find all the coaches who have coached exactly ONE team. List their first names followed by their last names.
SELECT C.firstname, C.lastname FROM coaches_season AS C GROUP BY C.firstname, C.lastname, C.cid HAVING count(tid) = 1;

--2. Find all the players who played in a Boston team and a Denver team (this does not have to happen in the same season). List their first names only.
SELECT P1.firstname FROM player_rs AS P1, teams AS T1 WHERE upper(P1.tid) = upper(T1.tid) AND upper(T1.location) = 'BOSTON' INTERSECT SELECT P2.firstname FROM player_rs AS P2, teams AS T2 WHERE upper(P2.tid) = upper(T2.tid) AND upper(T2.location) = 'DENVER';

--3. Find those who happened to be a coach and a player in the same team in the same season. List their first names, last names, the team where this happened, and the year(s) when this happened.
SELECT P.firstname, P.lastname, T.name, P.year FROM player_rs AS P, coaches_season AS C, teams AS T WHERE upper(P.ilkid) = upper(C.cid) AND P.year = C.year AND upper(P.tid) = upper(C.tid) AND upper(P.tid) = upper(T.tid);

--4. Find the average height (in centimeters) of each team coached by Phil Jackson in each season. Print the team name, season and the average height value (in centimeters), and sort the results by the average height.
SELECT T.name, C.year, round((avg(((P1.h_feet * 12) + P1.h_inches) * 2.54))::numeric, 2) AS avg_height FROM teams AS T, coaches_season AS C, players AS P1, player_rs AS P2 WHERE upper(C.firstname) = 'PHIL' AND upper(C.lastname) = 'JACKSON' AND upper(C.tid) = upper(T.tid) AND upper(T.tid) = upper(P2.tid) AND P2.year = C.year AND upper(P1.ilkid) = upper(P2.ilkid) GROUP BY T.name, C.year ORDER BY avg_height;

--5. Find the coach(es) (first name and last name) who have coached the largest number of players in year 1999.
SELECT C1.firstname, C1.lastname FROM coaches_season AS C1, player_rs AS P1 WHERE C1.year = '1999' AND P1.year = '1999' AND upper(C1.tid) = upper(P1.tid) AND upper(C1.tid) IN (SELECT upper(C2.tid) FROM coaches_season AS C2, player_rs AS P2 WHERE C2.year = '1999' AND P2.year = '1999' AND upper(C2.tid) = upper(P2.tid) GROUP BY upper(C2.tid) ORDER BY count(P2.ilkid) DESC LIMIT 1) GROUP BY C1.firstname, C1.lastname;

--6. Find the coaches who coached in ALL leagues. List their first names followed by their last names.
SELECT DISTINCT C.firstname, C.lastname FROM coaches_season AS C WHERE NOT EXISTS (SELECT DISTINCT T1.league FROM teams AS T1 EXCEPT SELECT T2.league FROM teams AS T2 WHERE upper(C.tid) = upper(T2.tid));

--7. Find those who happened to be a coach and a player in the same season, but in different teams. List their first names, last names, the season and the teams this happened.
SELECT P.firstname, P.lastname, P.year, T1.tid, T2.tid FROM player_rs AS P, coaches_season AS C, teams AS T1, teams AS T2 WHERE upper(P.ilkid) = upper(C.cid) AND P.year = C.year AND upper(P.tid) = upper(T1.tid) AND upper(C.tid) = upper(T2.tid) AND upper(T1.tid) != upper(T2.tid);

--8. Find the players who have scored more points than Michael Jordan did. Print out the first name, last name, and total number of points they scored.
SELECT P1.firstname, P1.lastname, P1.pts FROM player_rs_career AS P1 WHERE P1.pts > (SELECT P2.pts FROM player_rs_career AS P2 WHERE upper(P2.firstname) = 'MICHAEL' AND upper(P2.lastname) = 'JORDAN');

--9. Find the second most successful coach in regular seasons in history. The level of success of a coach is measured as season_win /(season_win + season_loss). Note that you have to count in all seasons a coach attended to calculate this value.
SELECT C.firstname, C.lastname, sum(C.season_win) AS wins, sum(C.season_loss) + sum(C.season_win) AS total, round(sum(C.season_win::decimal) / (sum(C.season_win::decimal) + sum(C.season_loss::decimal))::numeric, 2) AS ratio FROM coaches_season AS C GROUP BY C.firstname, C.lastname, C.cid ORDER BY ratio DESC OFFSET 1 LIMIT 1;

--10. List the top 10 schools that sent the largest number of drafts to NBA. List the name of each school and the number of drafts sent. Order the results by number of drafts (hint: use "order by" to sort the results and 'limit xxx' to limit the number of rows returned);
SELECT D.draft_from AS school, count(D.draft_from) AS count FROM draft AS D WHERE D.league = 'N' GROUP BY D.draft_from, D.league ORDER BY count DESC LIMIT 10;

-- redirecting output to console
\o
