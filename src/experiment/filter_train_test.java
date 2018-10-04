package experiment;
import java.io.*;
import java.util.HashMap;
public class filter_train_test {

	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords.txt");
		File before = new File("/Users/apple/desktop/paper_recommendation/data/published_before_2014.txt");
		File after = new File("/Users/apple/desktop/paper_recommendation/data/published_after_2014.txt");
		File f_before = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords_before_2014.txt");
		File f_after = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords_after_2014.txt");
		File strange = new File("/Users/apple/desktop/paper_recommendation/strange.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		BufferedReader b1 = new BufferedReader(new FileReader(before));
		BufferedReader b2 = new BufferedReader(new FileReader(after));
		BufferedWriter b3 = new BufferedWriter(new FileWriter(f_before));
		BufferedWriter b4 = new BufferedWriter(new FileWriter(f_after));
		BufferedWriter s = new BufferedWriter(new FileWriter(strange));
		HashMap<String,Integer> map = new HashMap<>();
		HashMap<String,Integer> map1 = new HashMap<>();
		String line;
		while((line = b1.readLine())!=null)
			map.put(line, 0);
		while((line = b2.readLine())!=null)
		{
			if(map.containsKey(line))
			{
				s.write(line+"\n");
				map.remove(line);
			}
				
			else map1.put(line, 0);
		}
	
			
		while((line = bf.readLine())!=null)
			if(map.containsKey(line.split(",")[0]))
				b3.write(line+"\n");
		
			else if(map1.containsKey(line.split(",")[0]))
				b4.write(line+"\n");
		
		bf.close();b1.close();b2.close();b3.close();b4.close();s.close();
	}
}
