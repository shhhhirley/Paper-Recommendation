package experiment;
import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
public class cal_idf {
	public static void main(String[] args)throws IOException
	{
		File p = new File("/Users/apple/desktop/paper_recommendation/data/paper_unigrams.txt");
		File ph = new File("/Users/apple/desktop/paper_recommendation/data/paper_phrases.txt");
		File w = new File("/Users/apple/desktop/paper_recommendation/data/idf.txt");
		BufferedReader bph = new BufferedReader(new FileReader(ph));
		BufferedReader bp = new BufferedReader(new FileReader(p));
		BufferedWriter bw = new BufferedWriter(new FileWriter(w));
		String line;
		HashMap<Integer, Integer>map = new HashMap<>();
		while((line = bp.readLine())!=null)
		{
			String sp[] = line.split(",");
			for(int i = 1; i < sp.length; i++)
			{
				if(!map.containsKey(Integer.parseInt(sp[i])))
					map.put(Integer.parseInt(sp[i]), 1);
				else
					map.put(Integer.parseInt(sp[i]), map.get(Integer.parseInt(sp[i]))+1);
			}
		}
		while((line = bph.readLine())!=null)
		{
			String sp[] = line.split(",");
			for(int i = 1; i < sp.length; i++)
			{
				if(!map.containsKey(Integer.parseInt(sp[i])))
					map.put(Integer.parseInt(sp[i]), 1);
				else
					map.put(Integer.parseInt(sp[i]), map.get(Integer.parseInt(sp[i]))+1);
			}
		}
		for(Entry<Integer, Integer>entry: map.entrySet())
		{
			bw.write(entry.getKey()+" "+Math.log(map.size()/entry.getValue())+"\n");
		}
		bw.close();
		bp.close();
		bph.close();
		
	}

}
