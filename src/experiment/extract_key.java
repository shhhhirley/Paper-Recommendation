package experiment;
import java.util.*;
import java.io.*;
public class extract_key {
	public static String  to_lower(String word)
	{
		
			char lower = (char) (word.charAt(0)-'A'+'a');
			String replace = lower+word.substring(1);
			return replace;
		
	}
    public static void main(String[] args)throws IOException
    {
    	String s = "Sequence";
    	String replace = to_lower(s);
    	System.out.println(replace);
    	
    }
     
	 
 }
