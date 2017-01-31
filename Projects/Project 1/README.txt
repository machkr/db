Database Design: Project 1
Matthew Kramer

To compile, navigate to the working directory and enter:

 javac *.java

Once compiled, run the program by executing:

 java P1

The add_coach and add_team method require a coach_id or team_id,
respectively. If a valid id is not entered, the program will
reject the command and an entry will not be made. Each
parameter is checked for validity before being entered into
the database. If a parameter is invalid, strings will be set
to "N/A", integers to 0, and chars to ' '.

The load_coach and load_team methods assume valid parameters
and only check for empty attributes, for which the program
will substitute the same values as the add_coach and add_team
methods do for invalid parameters.