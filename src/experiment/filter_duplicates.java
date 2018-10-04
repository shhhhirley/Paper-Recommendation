package experiment;
import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
public class filter_duplicates {

	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords_after_2014.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		File d = new File("/Users/apple/desktop/paper_recommendation/data/d_filtered_paper_keywords_after_2014.txt");
		BufferedWriter bd = new BufferedWriter(new FileWriter(d));
		String line;
		HashMap<String,String> map = new HashMap<>();
		while((line = bf.readLine())!=null)
		{
			String key = ",";
			String sp[] = line.split(",");
			for(int i = 1; i < sp.length-1; i++)
			{
				key += sp[i];
				key += ",";
			}
			key += sp[sp.length-1];
			if(!map.containsKey(sp[0]))
				map.put(sp[0], key);
			else
				map.remove(sp[0]);
		}
		for(Entry<String,String>entry:map.entrySet())
		{
			bd.write(entry.getKey()+entry.getValue()+"\n");
		}
			
			bd.close();
			bf.close();
	}
}
