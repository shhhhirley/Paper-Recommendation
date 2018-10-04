package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import java.util.Iterator;
public class reference_paper {
	public static void main(String[] args)throws IOException, ClassNotFoundException
	{//引用的是2014年之后发表的，被引用的不限制
		FileInputStream fin = new FileInputStream("/Users/apple/desktop/paper_recommendation/data/paper_reference.txt");
		ObjectInputStream in = new ObjectInputStream(fin);
//		FileInputStream fin2 = new FileInputStream("/Users/apple/desktop/paper_recommendation/data/reference_count.txt");
//		ObjectInputStream in2 = new ObjectInputStream(fin2);
//		HashMap<String, Integer> count = (HashMap<String, Integer>) in2.readObject();
		HashMap<String, ArrayList<String>> map = (HashMap<String, ArrayList<String>>) in.readObject();
		File f = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
//		File s = new File("/Users/apple/desktop/paper_recommendation/new_data/selected_paper_keywords_after_2014.txt");
//		BufferedWriter bs = new BufferedWriter(new FileWriter(s));
		File c = new File("/Users/apple/desktop/paper_recommendation/new_data/selected_paper_count_after_2014.txt");
		BufferedWriter bc = new BufferedWriter(new FileWriter(c));
		FileOutputStream fout = new FileOutputStream("/Users/apple/desktop/paper_recommendation/new_data/selected_paper_reference.txt");
		ObjectOutputStream out = new ObjectOutputStream(fout);
//		FileOutputStream fout2 = new FileOutputStream("/Users/apple/desktop/paper_recommendation/new_data/selected_reference_count_after_2014.txt");
//		ObjectOutputStream out2 = new ObjectOutputStream(fout2);
		String line; 
		HashMap<String, ArrayList<String>> replace = new HashMap<>();
	//	HashMap<String, Integer> replace_count = new HashMap<>();
		HashMap<String,Integer> al = new HashMap<>();
		while((line = br.readLine())!=null)
			if(!al.containsKey(line.split(",")[0]))
			{
				al.put(line.split(",")[0],0);
				
			}
				
		for(Entry<String, ArrayList<String>>entry:map.entrySet())
			replace.put(entry.getKey(), entry.getValue());
		
		for(Entry<String, ArrayList<String>>entry:map.entrySet())
		{
			
			if(!al.containsKey(entry.getKey()))
				replace.remove(entry.getKey());
			else 
			{
				ArrayList<String> ref = entry.getValue();
				
				//System.out.println(ref.size());
				for(int i = 0; i < ref.size(); i++)
				{
					if(!al.containsKey(ref.get(i)))
					{
						ref.remove(i);
						i--;
					}
						
				}
					
					
				if(ref.size()>0)
				{
					replace.put(entry.getKey(), ref);
				//	bs.write(entry.getKey()+" "+ref+"\n");
				
				}
				
					
				else
					replace.remove(entry.getKey());
				
			}
		}
		
		out.writeObject(replace);
		System.out.print(replace.size());
		
//		for(Entry<String, Integer>entry:count.entrySet())
//			replace_count.put(entry.getKey(), entry.getValue());
//		for(Entry<String, Integer> entry: count.entrySet())
//			if(!replace.containsKey(entry.getKey()))
//				replace_count.remove(entry.getKey());
	//	out2.writeObject(replace_count);

//		bs.close();
		bc.close();
		br.close();
	//	in2.close();
		in.close();
		out.close();
	//	out2.close();
				
		
	}
}
