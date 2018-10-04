package experiment;
import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
public class filter_author {
// filter paper with more than 10 papers	
	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/author_paper_id.txt");
		File w = new File("/Users/apple/desktop/paper_recommendation/results_1000/filtered_author.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		BufferedWriter bw = new BufferedWriter(new FileWriter(w));
		String line;
		int auth;
		HashMap<Integer, Integer> papernum = new HashMap<>();
		while((line = bf.readLine())!=null)
		{
			String[] sp = line.split(",");
			auth = Integer.parseInt(sp[1]);
			if(papernum.containsKey(auth))
				papernum.put(auth, papernum.get(auth)+1);
			else
				papernum.put(auth, 0);
			
		}
		for(Entry<Integer, Integer>entries: papernum.entrySet())
		{
			if(entries.getValue() >= 10)
				bw.write(entries.getKey()+"\n");
		}
		bf.close();
		bw.close();
	}

}
