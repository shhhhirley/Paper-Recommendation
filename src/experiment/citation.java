package experiment;
import java.util.List;
import java.io.*;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
public class citation {
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
		File key = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords.txt");
		BufferedReader bk = new BufferedReader(new FileReader(key));
		File f = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		FileOutputStream fout = new FileOutputStream("/Users/apple/desktop/paper_recommendation/reference_paper.txt");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		FileOutputStream fc = new FileOutputStream("/Users/apple/desktop/paper_recommendation/reference_count.txt");
		ObjectOutputStream c = new ObjectOutputStream(fc);
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
					if(!al.contains(cit))
						al.add(cit);
					if(!map.containsKey(cit))
						map.put(cit, new ArrayList<String>());
					pap = map.get(cit);
					if(!pap.contains(paper))
					{
						
						pap.add(paper);
						map.put(cit, pap);
					}
						
				}			
			}
			else {
				for(int i = 0; i < al.size(); i++)
				{
					if(!count.containsKey(al.get(i)))
						count.put(al.get(i), 1);
					else
						count.put(al.get(i), count.get(al.get(i))+1);
				}
				al.clear();
			}
			
		
			
		}
		
		
		
		br.close();
		//bw.close();
		out.writeObject(map);
		c.writeObject(count);
		out.close();
		c.close();
	}

}
