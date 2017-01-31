import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class P1
{
	public class Coach
	{
		String coach_id;
		int season;
		String first_name;
		String last_name;
		int season_win;
		int season_loss;
		int playoff_win;
		int playoff_loss;
		String team;

		public Coach()
		{
			this.coach_id = null;
			this.season = -1;
			this.first_name = null;
			this.last_name = null;
			this.season_win = -1;
			this.season_loss = -1;
			this.playoff_win = -1;
			this.playoff_loss = -1;
			this.team = null;
		}

		public Coach(String coach_id, int season, String first_name, String last_name, int season_win,
			int season_loss, int playoff_win, int playoff_loss, String team)
		{
			this.coach_id = coach_id;
			this.season = season;
			this.first_name = first_name;
			this.last_name = last_name;
			this.season_win = season_win;
			this.season_loss = season_loss;
			this.playoff_win = playoff_win;
			this.playoff_loss = playoff_loss;
			this.team = team;
		}
	}

	public class Team
	{
		String team_id;
		String location;
		String name;
		char league;

		public Team()
		{
			this.team_id = null;
			this.location = null;
			this.name = null;
			this.league = '\0';
		}

		public Team(String team_id, String location, String name, char league)
		{
			this.team_id = team_id;
			this.location = location;
			this.name = name;
			this.league = league;
		}
	}

	public P1(){}

	public Vector<Coach> coaches = new Vector<Coach>();
	public Vector<Team> teams = new Vector<Team>();

	public void run()
	{
		CommandParser parser = new CommandParser();

		System.out.println("\nWelcome to the miniature database of NBA coaches and teams.");
		System.out.println("Please enter a command or type 'help' for a list of commands.\n");
		System.out.print("> ");

		Command cmd = null;

		while ((cmd = parser.fetchCommand()) != null)
		{
			boolean result = false;

			if (cmd.getCommand().equals("help"))
			{
				result = doHelp();
			}
			else if (cmd.getCommand().equals("add_coach"))
			{
				try
				{
					Coach new_coach = new Coach(); // Create a new coach object
					String[] parameters = cmd.getParameters(); // Retrieve parameters

					for (int i = 0; i < parameters.length; i++)
					{
						parameters[i] = parameters[i].trim().replaceAll("\\+", " ");
					}

					if (parameters[0].matches("^[A-Z]{0,7}[0-9]{2}$"))
					{
						new_coach.coach_id = parameters[0];

						if (parameters[1].matches("^[0-9]{4}$"))
						{
							new_coach.season = Integer.parseInt(parameters[1]);
						}
						else // If an invalid season is entered
						{
							System.out.println("\nInvalid season entered: set to 0.");
							new_coach.season = 0;
						}

						if (parameters[2].matches("^[A-Za-z]+$"))
						{
							new_coach.first_name = parameters[2];
						}
						else // If an invalid first name is entered
						{
							System.out.println("\nInvalid first name entered: set to \"N/A\".");
							new_coach.first_name = "N/A";
						}

						if (parameters[3].matches("^[A-Za-z]+$"))
						{
							new_coach.last_name = parameters[3];
						}
						else // If an invalid last name is entered
						{
							System.out.println("\nInvalid last name entered: set to \"N/A\".");
							new_coach.first_name = "N/A";
						}

						if (parameters[4].matches("^\\d+$"))
						{
							new_coach.season_win = Integer.parseInt(parameters[4]);
						}
						else // If an invalid number of season wins is entered
						{
							System.out.println("\nInvalid season wins entered: set to 0.");
							new_coach.season_win = 0;
						}

						if (parameters[5].matches("^\\d+$"))
						{
							new_coach.season_loss = Integer.parseInt(parameters[5]);
						}
						else // If an invalid number of season losses is entered
						{
							System.out.println("\nInvalid season losses entered: set to 0.");
							new_coach.season_loss = 0;
						}

						if (parameters[6].matches("^\\d+$"))
						{
							new_coach.playoff_win = Integer.parseInt(parameters[6]);
						}
						else // If an invalid number of playoff wins is entered
						{
							System.out.println("\nInvalid playoff wins entered: set to 0.");
							new_coach.playoff_win = 0;
						}

						if (parameters[7].matches("^\\d+$"))
						{
							new_coach.playoff_loss = Integer.parseInt(parameters[7]);
						}
						else // If an invalid number of playoff losses is entered
						{
							System.out.println("\nInvalid playoff losses entered: set to 0.");
							new_coach.playoff_loss = 0;
						}

						if (parameters[8].matches("^[A-Z0-9]+$"))
						{
							new_coach.team = parameters[8];
						}
						else // If an invalid team is entered
						{
							System.out.println("\nInvalid team name entered: set to \"N/A\".");
							new_coach.team = "N/A";
						}

						coaches.add(new_coach); // Add the newly created coach to the database

						System.out.println("\nCoach added successfully.");
					}
					else // If an invalid coach id is entered
					{
						System.out.println("\nInvalid coach ID entered: unable to add coach.");
					}
				}
				catch (Exception error)
				{
					System.out.println("\nError: " + error.toString());
				}
			}
			else if (cmd.getCommand().equals("add_team"))
			{
				try
				{
					Team new_team = new Team(); // Create a new team object
					String[] parameters = cmd.getParameters(); // Retrieve parameters

					for (int i = 0; i < parameters.length; i++)
					{
						parameters[i] = parameters[i].trim().replaceAll("\\+", " ");
					}

					if (parameters[0].matches("^[A-Z0-9]+$"))
					{
						new_team.team_id = parameters[0];

						if (parameters[1].matches("^[A-Za-z]+\\s?[A-Za-z]+$"))
						{
							new_team.location = parameters[1];
						}
						else
						{
							System.out.println("Invalid team location entered: set to \"N/A\".");
							new_team.location = "N/A";
						}

						if (parameters[2].matches("^[A-Za-z]+$"))
						{
							new_team.name = parameters[2];
						}
						else
						{
							System.out.println("Invalid team name entered: set to \"N/A\".");
							new_team.name = "N/A";
						}

						if (parameters[3].matches("^[A-Z]$"))
						{
							new_team.league = parameters[3].charAt(0);
						}
						else
						{
							System.out.println("Invalid league entered: set to \" \".");
							new_team.league = ' ';
						}

						teams.add(new_team); // Add the newly created team to the database

						System.out.println("\nTeam added successfully.");
					}
					else
					{
						System.out.println("Invalid team ID entered: unable to add team.");
					}
				}
				catch (Exception error)
				{
					System.out.println("\nError: " + error.toString());
				}
			}
			else if (cmd.getCommand().equals("print_coaches"))
			{
				try
				{
					for(Coach coach : coaches)
					{
						print_coach(coach);
					}

					System.out.println();
				}
				catch (Exception error)
				{
					System.out.println("\nError: " + error.toString());
				}
			}
			else if (cmd.getCommand().equals("print_teams"))
			{
				try
				{
					for(Team team : teams)
					{
						print_team(team);
					}

					System.out.println();
				}
				catch (Exception error)
				{
					System.out.println("\nError: " + error.toString());
				}
			}
			else if (cmd.getCommand().equals("coaches_by_name"))
			{
				try
				{
					String[] parameters = cmd.getParameters(); // Retrieve parameters

					// Format query, replacing '+' with ' '
					String last_name_query = parameters[0].replaceAll("\\+", " ");
					boolean flag = false;

					for(Coach coach : coaches)
					{
						if (coach.last_name.equals(last_name_query))
						{
							print_coach(coach);
							flag = true;
						}
					}

					if (!flag)
					{
						System.out.printf("\nNo coaches were found with the last name \'%s\'.", last_name_query);
					}

					System.out.println("");
				}
				catch (Exception error)
				{
					System.out.println("\nError: " + error.toString());
				}
			}
			else if (cmd.getCommand().equals("teams_by_city"))
			{
				try
				{
					String[] parameters = cmd.getParameters(); // Retrieve parameters

					// Format query, replacing '+' with ' '
					String location_query = parameters[0].replaceAll("\\+", " ");
					boolean flag = false;

					for(Team team : teams)
					{
						if (team.location.equals(location_query))
						{
							print_team(team);
							flag = true;
						}
					}

					if (!flag)
					{
						System.out.printf("\nNo teams were found with the location \'%s\'.\n", location_query);
					}

					System.out.println("");
				}
				catch (Exception error)
				{
					System.out.println("\nError: " + error.toString());
				}
			}
			else if (cmd.getCommand().equals("load_coaches"))
			{
				try
				{
					File infile = new File(cmd.getParameters()[0]);
					BufferedReader buffer = new BufferedReader(new FileReader(infile));
					String line = buffer.readLine(); // Don't need first line of file (column headers)
					int count = 0;

					while ((line = buffer.readLine()) != null)
					{
						Coach new_coach = new Coach();
						String[] parameters = line.split(",");
						
						for (int i = 0; i < parameters.length; i++)
						{
							parameters[i] = parameters[i].trim().replaceAll("\\+", " ");;
						}

						if (parameters[0].isEmpty())
						{
							new_coach.coach_id = "N/A";
						}
						else
						{
							new_coach.coach_id = parameters[0];
						}

						if (parameters[1].isEmpty())
						{
							new_coach.season = 0;
						}
						else
						{
							new_coach.season = Integer.parseInt(parameters[1]);
						}

						// parameters[2] contains 'year order'

						if (parameters[3].isEmpty())
						{
							new_coach.first_name = "N/A";
						}
						else
						{
							new_coach.first_name = parameters[3];
						}

						if (parameters[4].isEmpty())
						{
							new_coach.last_name = "N/A";
						}
						else
						{
							new_coach.last_name = parameters[4];
						}

						if (parameters[5].isEmpty())
						{
							new_coach.season_win = 0;
						}
						else
						{
							new_coach.season_win = Integer.parseInt(parameters[5]);
						}

						if (parameters[6].isEmpty())
						{
							new_coach.season_loss = 0;
						}
						else
						{
							new_coach.season_loss = Integer.parseInt(parameters[6]);
						}

						if (parameters[7].isEmpty())
						{
							new_coach.playoff_win = 0;
						}
						else
						{
							new_coach.playoff_win = Integer.parseInt(parameters[7]);
						}

						if (parameters[8].isEmpty())
						{
							new_coach.playoff_loss = 0;
						}
						else
						{
							new_coach.playoff_loss = Integer.parseInt(parameters[8]);
						}

						if (parameters[9].isEmpty())
						{
							new_coach.team = "N/A";
						}
						else
						{
							new_coach.team = parameters[9];
						}

						coaches.add(new_coach);
						new_coach = null;
						count++;
					}

					System.out.printf("\n%d coaches were loaded into the database.\n", count);
				}
				catch (Exception error)
				{
					System.out.println("\nError: " + error.toString());
				}
			}
			else if (cmd.getCommand().equals("load_teams"))
			{
				try
				{
					File infile = new File(cmd.getParameters()[0]);
					BufferedReader buffer = new BufferedReader(new FileReader(infile));
					String line = buffer.readLine(); // Don't need first line of file (column headers)
					int count = 0;

					while((line = buffer.readLine()) != null)
					{
						Team new_team = new Team();
						String[] parameters = line.split(",");

						for (int i = 0; i < parameters.length; i++)
						{
							parameters[i] = parameters[i].trim();
						}

						if (parameters[0].isEmpty())
						{
							new_team.team_id = "N/A";
						}
						else
						{
							new_team.team_id = parameters[0];
						}

						if (parameters[1].isEmpty())
						{
							new_team.location = "N/A";
						}
						else
						{
							new_team.location = parameters[1];
						}

						if (parameters[2].isEmpty())
						{
							new_team.name = "N/A";
						}
						else
						{
							new_team.name = parameters[2];
						}
						
						if (parameters[3].isEmpty())
						{
							new_team.league = ' ';
						}
						else
						{
							new_team.league = parameters[3].charAt(0);
						}
						
						teams.add(new_team);
						new_team = null;
						count++;
					}

					System.out.printf("\n%d teams were loaded into the database.\n", count);
				}
				catch (Exception error)
				{
					System.out.println("\nError: " + error.toString());
				}
			}
			else if (cmd.getCommand().equals("best_coach"))
			{
				try
				{
					String[] parameters = cmd.getParameters();
					int season = Integer.parseInt(parameters[0]);

					int best_net_wins = Integer.MIN_VALUE;

					for(Coach coach : coaches)
					{
						int net_wins = (coach.season_win - coach.season_loss) + (coach.playoff_win - coach.playoff_loss);

						if(coach.season == season && net_wins >= best_net_wins)
						{
							best_net_wins = net_wins;
						}
					}

					for(Coach coach : coaches)
					{
						int net_wins = (coach.season_win - coach.season_loss) + (coach.playoff_win - coach.playoff_loss);

						if(best_net_wins == net_wins)
						{
							System.out.printf("\n%s %s with %d net wins.\n", coach.first_name, coach.last_name, net_wins);
						}
					}
				}
				catch (Exception error)
				{
					System.out.println("\nError: " + error.toString());
				}
			}
			else if (cmd.getCommand().equals("search_coaches"))
			{
				try
				{
					String[] parameters = cmd.getParameters();
					Vector<Coach> current = new Vector<Coach>();
					Vector<Coach> temp = new Vector<Coach>();
					boolean flag = false;

					temp.addAll(coaches);

					for(String parameter : parameters)
					{
						String[] query = parameter.split("=", 2);
						query[1].replaceAll("\\+", " ");

						for(Coach coach : temp)
						{
							if(query[0].matches("coach_id | COACH_ID | coachid | COACHID"))
							{
								if(coach.coach_id.equals(query[1]))
								{
									current.add(coach);
								}
							}
							else if(query[0].matches("season|SEASON"))
							{
								if(coach.season == Integer.parseInt(query[1]))
								{
									current.add(coach);
								}
							}
							else if(query[0].matches("first_name|FIRST_NAME|firstname|FIRSTNAME"))
							{
								if(coach.first_name.equals(query[1]))
								{
									current.add(coach);
								}
							}
							else if(query[0].matches("last_name|LAST_NAME|lastname|LASTNAME"))
							{
								if(coach.last_name.equals(query[1]))
								{
									current.add(coach);
								}
							}
							else if(query[0].matches("season_win|SEASON_WIN|seasonwin|SEASONWIN"))
							{
								if(coach.season_win == Integer.parseInt(query[1]))
								{
									current.add(coach);
								}
							}
							else if(query[0].matches("season_loss|SEASON_LOSS|seasonloss|SEASONLOSS"))
							{
								if(coach.season_loss == Integer.parseInt(query[1]))
								{
									current.add(coach);
								}
							}
							else if(query[0].matches("playoff_win|PLAYOFF_WIN|playoffwin|PLAYOFFWIN"))
							{
								if(coach.playoff_win == Integer.parseInt(query[1]))
								{
									current.add(coach);
								}
							}
							else if(query[0].matches("playoff_loss|PLAYOFF_LOSS|playoffloss|PLAYOFFLOSS"))
							{
								if(coach.playoff_loss == Integer.parseInt(query[1]))
								{
									current.add(coach);
								}
							}
							else if(query[0].matches("team|TEAM"))
							{
								if(coach.team.equals(query[1]))
								{
									current.add(coach);
								}
							}
						}

						temp.clear();
						temp.addAll(current);
						current.clear();
					}

					for(Coach coach : temp)
					{
						print_coach(coach);
					}

					System.out.println();
				}
				catch (Exception error)
				{
					System.out.println("\nError: " + error.toString());
				}
			}
			else if (cmd.getCommand().equals("exit"))
			{
				System.out.println("\nLeaving the database, goodbye!");
				break;
			}
			else if (cmd.getCommand().equals(""))
			{
				System.out.println("\nNo command entered, please try again!");
			}
			else
			{
				System.out.println("\nInvalid command entered, please try again!");
			}

			if (result)
			{
				System.out.println("\nPlease enter a command.");
			}

			System.out.print("\n> ");
		}
	}

	private boolean doHelp()
	{
		System.out.println("\n\"add_coach\"       - adds a new coach to the database");
		System.out.println("\t\t    PARAMETERS: <ID> <SEASON> <FIRST_NAME> <LAST_NAME> <SEASON_WIN> <SEASON_LOSS>");
		System.out.println("\t\t\t\t<PLAYOFF_WIN> <PLAYOFF_LOSS> <TEAM>");
		System.out.println("\n\"add_team\"        - adds a new team to the database");
		System.out.println("\t\t    PARAMETERS: <ID> <LOCATION> <NAME> <LEAGUE> -- adds a new team");
		System.out.println("\n\"print_coaches\"   - outputs all coaches in the database");
		System.out.println("\n\"print_teams\"     - outputs all teams in the database");
		System.out.println("\n\"coaches_by_name\" - outputs all coaches with given last name");
		System.out.println("\t\t    PARAMETER: <NAME>");
		System.out.println("\n\"teams_by_city\"   - outputs all teams from a given city");
		System.out.println("\t\t    PARAMETER: <CITY>");
		System.out.println("\n\"load_coach\"      - imports coaches from file into database");
		System.out.println("\t\t    PARAMETER: <FILENAME>");
		System.out.println("\n\"load_team\"       - imports teams from file into database");
		System.out.println("\t\t    PARAMETER: <FILENAME>");
		System.out.println("\n\"best_coach\"      - outputs the name of the coach with the most net-wins in a given season");
		System.out.println("\t\t    PARAMETER: <SEASON>");
		System.out.println("\n\"search_coaches\"  - outputs the name of the coach satisfying the given queries");
		System.out.println("\t\t    PARAMETERS: field=<VALUE>");
		System.out.println("\n\"exit\"            - quits the program");

		return true;
	}

	private void print_coach(Coach coach)
	{
		System.out.format("\n%-9s  ", coach.coach_id);
		System.out.format("%4d  ", coach.season);
		System.out.format("%-15s  ", coach.first_name);
		System.out.format("%-15s  ", coach.last_name);
		System.out.format("%02d  ", coach.season_win);
		System.out.format("%02d  ", coach.season_loss);
		System.out.format("%02d  ", coach.playoff_win);
		System.out.format("%02d  ", coach.playoff_loss);
		System.out.format("%-3s", coach.team);
	}

	private void print_team(Team team)
	{
		System.out.format("\n%-5s  ", team.team_id);
		System.out.format("%-15s  ", team.location);
		System.out.format("%-15s  ", team.name);
		System.out.format("%1s", team.league);
	}

	public static void main(String[] args)
	{
		new P1().run();
	}
}
