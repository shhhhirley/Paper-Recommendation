package experiment;
import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;
public class phrases {
	public static void main(String[] args)throws IOException
	{
		HashMap<String,String> key = new HashMap<>();
		//ArrayList<String> key = new ArrayList<>();
		File f = new File("/Users/apple/desktop/paper_recommendation/data/parsed.txt");
		File p = new File("/Users/apple/desktop/paper_recommendation/data/phrases_id.txt");
		File np = new File("/Users/apple/desktop/paper_recommendation/data/paper_phrases.txt");
		BufferedReader bp = new BufferedReader(new FileReader(p));
		BufferedReader bf = new BufferedReader(new FileReader(f));
		BufferedWriter bw = new BufferedWriter(new FileWriter(np));
		String line;

		while((line=bp.readLine())!=null)
		{
			key.put(line.split(",")[0], line.split(",")[1]);
		}
		while((line=bf.readLine())!=null)
		{
			
			String index = line.split("\\*")[0];
			index = index.substring(0, index.length());
			//System.out.println(index);
			line = line.split("\\*")[1];
			String[] sp = line.split("\\[");
				int nums = sp.length-1;
				if(sp.length>0)
					bw.write(index);
				
				for(int i = 1; i <= nums; i++)
				{
					String kw[] = sp[i].split("]");
					if(key.containsKey(kw[0]))
						bw.write(","+key.get(kw[0]));
						
					
				}
				bw.write("\n");
				//System.out.println(count);	
			
			
		
		}
		
	
		bw.close();
		bf.close();
		
	}

}
