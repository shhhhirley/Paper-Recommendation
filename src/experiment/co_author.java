package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.math3.linear.ArrayRealVector;
public class co_author {
	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		File a = new File("/Users/apple/desktop/paper_recommendation/data/author_id.txt");
		File l  = new File("/Users/apple/desktop/paper_recommendation/results/author_labels.txt");
		File v = new File("/Users/apple/desktop/paper_recommendation/results/authors_vectors.txt");
		File w = new File("/Users/apple/desktop/paper_recommendation/results_1000/filtered_author.txt");
		File c = new File("/Users/apple/desktop/paper_recommendation/results_1000/co_authors_distance.txt");
		File cl = new File("/Users/apple/desktop/paper_recommendation/results_1000/co_authors_labels.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		BufferedReader ba = new BufferedReader(new FileReader(a));
		BufferedReader bl = new BufferedReader(new FileReader(l));
		BufferedReader bv = new BufferedReader(new FileReader(v));
		BufferedReader bw = new BufferedReader(new FileReader(w));
		BufferedWriter bc = new BufferedWriter(new FileWriter(c));
		BufferedWriter bcl = new BufferedWriter(new FileWriter(cl));
		HashMap<Integer, ArrayList<Integer>>map = new HashMap<>();
		HashMap<String, Integer>map1 = new HashMap<>();
		String line,vec_line; 
		HashMap<Integer,Integer> label = new HashMap<>();
		ArrayList<Integer> authorlist = new ArrayList<>();
		ArrayList<ArrayRealVector> vecs = new ArrayList<>();
		
		
		int pair_num = 0;
		int num = 0, kn = 0;
		while((line = bw.readLine()) != null)
			authorlist.add(Integer.parseInt(line));
		while((line = ba.readLine()) != null)
			map1.put(line.split(",")[0], Integer.parseInt(line.split(",")[1]));
	//	System.out.println(map1.size());
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
		System.out.println(pair_num);
		
//		for(int i = 0; i < 1000; i++)
//		{
//			int la = rd.nextInt(map.size());
//			sel = map.get(la);
//			if(!selected.containsKey(sel.get(0)))
//				selected.put(sel.get(0), 5);
//				
//			
//		}
		int ln = 0;
		while((line = bl.readLine())!=null && (vec_line = bv.readLine())!=null)
		{
			ArrayRealVector vec = new ArrayRealVector(50);
			label.put(Integer.parseInt(line),ln);
			for(int i = 0; i < 50; i++)
				vec.addToEntry(i,Double.parseDouble(vec_line.split(" ")[i]));
			vecs.add(vec);
			ln++;
				
		}
		System.out.println(ln);
		while(kn < pair_num)
		{
			if(num > 10000)
				break;
			ArrayList<Integer> find_pairs = map.get(kn);
			System.out.println(find_pairs);
			int s[] = new int[2];
			int vl[] = new int[2];
			int k = 0;
			for(int j = 0; j < find_pairs.size(); j++)
			{
				if(label.containsKey(find_pairs.get(j))&&authorlist.contains(find_pairs.get(j))&&k<=1)
				{
					vl[k] = label.get(find_pairs.get(j));
					s[k] = find_pairs.get(j);
					k++;
				}
				else if(k>1)
					break;
				
			}
			System.out.println(s[0]+" "+s[1]);
			System.out.println(vl[0]+" "+vl[1]);
			if(k > 1 && s[0] != s[1])
			{
				ArrayRealVector vec1 = vecs.get(vl[0]);
				ArrayRealVector vec2 = vecs.get(vl[1]);
				double distance = vec1.getDistance(vec2);
				bc.write(distance+"\n");
				bcl.write(s[0]+","+s[1]+"\n");
				num++;
				
				
			}
			System.out.println(num);
			kn++;
			
			
		}

	bf.close();
	ba.close();
	bl.close();
	bv.close();
	bw.close();
	bc.close();
	bcl.close();
			
	}

}
