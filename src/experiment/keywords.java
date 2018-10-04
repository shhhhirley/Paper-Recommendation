package experiment;
import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
public class keywords {

	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords.txt");
		File s = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper.txt");
		
		BufferedReader bf = new BufferedReader(new FileReader(f));
		BufferedWriter bs = new BufferedWriter(new FileWriter(s));

		HashMap<Integer,Integer> map = new HashMap<>();
		String keywords;
		
		int count = 0;
		
		while((keywords=bf.readLine())!=null)
		{
			String[] sp = keywords.split(",");
			bs.write(sp[0]+"\n");
			if(!map.containsKey(sp.length-1))
				map.put(sp.length-1, 1);
			else
				map.put(sp.length-1, map.get(sp.length-1)+1);
			
			
				
		}
		for(Entry<Integer,Integer>entry:map.entrySet())
		{
			count += entry.getValue(); 
			System.out.println(entry);
		}
		System.out.println(count);
		bf.close();
		bs.close();
		
	
	
	}
}
