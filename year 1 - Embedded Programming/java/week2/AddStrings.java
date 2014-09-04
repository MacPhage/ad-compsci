package austinjackson.week2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddStrings 
{
	public static List<String> str = new ArrayList<String>();
	public static Scanner console = new Scanner(System.in);
	public static boolean flag = false;
	public static String input;
	
	public static void main(String args[])
	{
		while(!flag)
		{
			System.out.println("Enter a String, to stop type \'!!stop!!\':");
			input = console.next();
			if(input.equals("!!stop!!"))
			{
				System.out.println("Concatenated as:\n"+concatList(str));
				flag = true;
			}
			else
			{
				str.add(input);
				System.out.println("\'"+input+"\' was added to the String list...");
			}
		}
	}
	
	public static String concatList(List<String> s)
	{
		String result = "";
		for(int i = 0; i < s.size(); i++)
		{
			result += (s.get(i)+" ");
		}
		return result;
	}
}
