package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
public class matrix_process {
	
	public static void main(String[] args)throws IOException
	{
		File ac = new File("/Users/apple/desktop/paper_recommendation/results/author_citation.txt");
		BufferedReader bac = new BufferedReader(new FileReader(ac));
		File ca = new File("/Users/apple/desktop/paper_recommendation/results/citation_author.txt");
		BufferedWriter bca = new BufferedWriter(new FileWriter(ca));
		String line;
		HashMap<Integer, ArrayList<Integer>>map = new HashMap<>();
		while((line = bac.readLine())!=null)
		{
			int citation = Integer.parseInt(line.split(" ")[1]);
			ArrayList<Integer> al;
			if(!map.containsKey(citation))
			{
				al = new ArrayList<>();
				al.add(Integer.parseInt(line.split(" ")[0]));
				map.put(citation,al);
			}
			else
				{
				al = map.get(citation);
				al.add(Integer.parseInt(line.split(" ")[0]));
				map.put(citation, al);
				}
			
				
				
		}
		for(int i = 0; i < map.size(); i++)
		{
			for(int j = 0; j < map.get(i).size(); j++)
				bca.write(map.get(i).get(j)+" ");
			bca.write("\n");
		}
		bca.close();
		bac.close();
	}
}
