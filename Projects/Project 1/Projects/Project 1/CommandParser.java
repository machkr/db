import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandParser
{
	public Command fetchCommand()
	{
		try
		{
			String s = reader.readLine();

			if (s == null) return null;

			String[] pieces = s.split("\\s+", 10);
			String[] params = new String[pieces.length - 1];
			System.arraycopy(pieces, 1, params, 0, pieces.length - 1);
			
			return new Command(pieces[0], params);
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}
	
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
}
