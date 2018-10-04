package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.math3.linear.ArrayRealVector;
public class all_co_authors {
	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		File a = new File("/Users/apple/desktop/paper_recommendation/data/author_id.txt");
		File l  = new File("/Users/apple/desktop/paper_recommendation/results/author_labels.txt");
		File v = new File("/Users/apple/desktop/paper_recommendation/results/authors_vectors.txt");
		File d = new File("/Users/apple/desktop/paper_recommendation/results_1000/random_distance.txt");
		File rl = new File("/Users/apple/desktop/paper_recommendation/results_1000/random_labels.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		BufferedReader ba = new BufferedReader(new FileReader(a));
		BufferedReader bl = new BufferedReader(new FileReader(l));
		BufferedReader bv = new BufferedReader(new FileReader(v));
		BufferedWriter bd = new BufferedWriter(new FileWriter(d));
		BufferedWriter brl = new BufferedWriter(new FileWriter(rl));
		HashMap<Integer, ArrayList<Integer>>map = new HashMap<>();
		HashMap<String, Integer>map1 = new HashMap<>();
		String line,vec_line; 
		HashMap<Integer,Integer> label = new HashMap<>();
		ArrayList<ArrayRealVector> vecs = new ArrayList<>();
		int pair_num = 0;
		Random rd = new Random();
		while((line = ba.readLine()) != null)
			map1.put(line.split(",")[0], Integer.parseInt(line.split(",")[1]));
		while((line = bf.readLine())!=null)
		{
			if(line.length()!=0)
			{
				if(line.charAt(1)=='@')
				{
					ArrayList<Integer> co_author = new ArrayList<>();
					line = line.substring(2);
					String sp[] = line.split(", ");
					for(int i = 0; i < sp.length; i++)
						co_author.add(map1.get(sp[i]));
					if(co_author.size()>1)
						map.put(pair_num++, co_author);
					
				}
			}
			
		}
		int ln = 0;
		while((line = bl.readLine())!=null && (vec_line = bv.readLine())!=null)
		{
			ArrayRealVector vec = new ArrayRealVector(50);
			label.put(ln,Integer.parseInt(line));
			for(int i = 0; i < 50; i++)
				vec.addToEntry(i,Double.parseDouble(vec_line.split(" ")[i]));
			vecs.add(vec);
			ln++;
				
		}
		System.out.println(vecs.size()+" "+ln);
		for(int i = 0; i < 10000; i++)
		{
			boolean co = false;
			int next1 = rd.nextInt(vecs.size());
			int next2 = rd.nextInt(vecs.size());
			for(Entry<Integer, ArrayList<Integer>>entries: map.entrySet())
			{
				ArrayList<Integer> al = entries.getValue();
				if(al.contains(label.get(next1)))
				{
					if(al.contains(label.get(next2)))
					{
						co = true;
						break;
					}
						
				}
				
				
			}
			if(co)
				i--;
			else
				{
				bd.write(vecs.get(next1).getDistance(vecs.get(next2))+"\n");
				brl.write(label.get(next1)+","+label.get(next2)+"\n");
				}
			System.out.println(i);
		}
		
		
		bf.close();
		ba.close();
		bl.close();
		bv.close();
		bd.close();
		brl.close();
		
	}

}
