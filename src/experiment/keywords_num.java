package experiment;
import java.io.*;
import java.util.HashMap;
public class keywords_num {

	public static void main(String[] args)throws IOException
	{
		
		File f = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords.txt");
		File n = new File("/Users/apple/desktop/paper_recommendation/keywords_stats.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		BufferedWriter bw = new BufferedWriter(new FileWriter(n));
		HashMap<Integer,Integer> map = new HashMap<>();
		int count;
		String line;
		while((line=bf.readLine())!=null)
		{
			int len = line.split(",").length-1;
			if(map.containsKey(len))
			{
				count = map.get(len);
				count++;
				map.put(len, count);
			}
			else
			map.put(len,0);
				
		}
		for(int i = 0; i < map.size(); i++)
			bw.write(i+" "+map.get(i)+"\n");
		bf.close();
		bw.close();
		
	}
}
