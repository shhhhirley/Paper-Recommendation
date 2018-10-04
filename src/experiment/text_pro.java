package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.math3.linear.*;

public class text_pro {
	public static void main(String[] args) throws IOException
	{
	
		File f = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		File k = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords_after_2014.txt");
		BufferedReader bk = new BufferedReader(new FileReader(k));
		File m = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords_in_2014.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(m));
		String line, line1;
		HashMap<String, Integer> map = new HashMap<>();
		int year = 0;
		String index = ""; 
		while((line = bf.readLine())!=null)
		{
			
			if(line.length()!=0)
			{
				if(line.charAt(1) == 't')
				{
					 year = Integer.parseInt(line.substring(2));
				//	 System.out.println(year);
				}
				else if(line.charAt(1) == 'i')
				{
					index = line.substring(6);
				}
				
			}
			
			else 
				{
				System.out.println(year);
				if(year == 2015)
					map.put(index, 0);
				}
					
					
					
			
			
			}
		System.out.println(map.size());
		while((line1 = bk.readLine())!=null)
		{
			if(map.containsKey(line1.split(",")[0]))
				bw.write(line1+"\n");
		}
			
		bw.close();
		bf.close();
		bk.close();
		
		
	}
	
	
}
