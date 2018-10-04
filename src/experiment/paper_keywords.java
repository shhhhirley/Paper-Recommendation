package experiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
public class paper_keywords {
	public static void main(String[] args)throws IOException
	{//hashmap的containskey用的是hash table,速度比arraylist contains快很多
		File f = new File("/Users/apple/desktop/paper_recommendation/data/paper_keywords.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		File a = new File("/Users/apple/desktop/paper_recommendation/data/abnormal.txt");
		BufferedReader ba = new BufferedReader(new FileReader(a));
		File k = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords.txt");
		BufferedWriter bk = new BufferedWriter(new FileWriter(k));
		String line1,line2;
		//ArrayList<String> map = new ArrayList<>();
		HashMap<String,Integer>map = new HashMap<>();
		while((line1 = ba.readLine())!=null)
		{
			map.put(line1,1);
			System.out.println(line1);
		}
		while((line2 = bf.readLine())!=null)
		{
			if(!map.containsKey(line2.split(",")[0]))//map.containsKey();
				bk.write(line2+"\n");
		}
		bk.close();
		bf.close();
		ba.close();
	}
}
