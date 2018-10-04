package experiment;
import java.util.List;
import java.io.*;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
public class paper_citations {
	public static ArrayList<Map.Entry<String,Integer>> sortMap(Map map){
	     List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
	     Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
	         public int compare(Map.Entry<String, Integer> obj1 , Map.Entry<String, Integer> obj2) {
	             return obj2.getValue() - obj1.getValue();
	         }
	     });
	      return (ArrayList<Entry<String, Integer>>) entries;
	    }
	public static void main(String[] args)throws IOException
	{
		ArrayList<String> al = new ArrayList<>();
		HashMap<String,Integer> count = new HashMap<>(); 
		HashMap<String, ArrayList<String>> map = new HashMap<>(); 
	
		File f = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		FileOutputStream fout = new FileOutputStream("/Users/apple/desktop/paper_recommendation/data/paper_reference.txt");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		
		String line;
		
		//int k = 0;
		
		String cit="";
		String paper="";
		ArrayList<String> pap = new ArrayList<>();
		while((line = br.readLine()) != null)
		{
			if(line.length() != 0)	
			{
				if(line.charAt(1)=='i')
				{
					paper = line.substring(6);
				}
				if(line.charAt(1)=='%')
				{
					cit = line.substring(2);			
					if(!map.containsKey(paper))
						map.put(paper, new ArrayList<String>());
					pap = map.get(paper);
					if(!pap.contains(cit))
					{
						pap.add(cit);
						map.put(paper, pap);
					}
						
				}			
			}
			
			
			
		
			
		}
		
		
		
		br.close();
		//bw.close();
		out.writeObject(map);
		out.close();
		
	}

}
