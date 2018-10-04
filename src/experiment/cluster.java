package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.math3.linear.ArrayRealVector;
public class cluster {
	public static void main(String [] args)throws IOException
	{
		File d = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		BufferedReader bd = new BufferedReader(new FileReader(d));
		File n = new File("/Users/apple/desktop/paper_recommendation/venue_id.txt");
		BufferedReader bn = new BufferedReader(new FileReader(n));
		File f = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords_after_2014.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));	
		File key = new File("/Users/apple/desktop/paper_recommendation/results/keywords_vectors.txt");
		BufferedReader bk = new BufferedReader(new FileReader(key));
		File kl = new File("/Users/apple/desktop/paper_recommendation/results/keywords_labels.txt");
		BufferedReader bl = new BufferedReader(new FileReader(kl));
		File venu = new File("/Users/apple/desktop/paper_recommendation/results/venues_vectors.txt");
		BufferedReader bv = new BufferedReader(new FileReader(venu));
		File ven_la = new File("/Users/apple/desktop/paper_recommendation/results/venue_labels.txt");
		BufferedReader ba = new BufferedReader(new FileReader(ven_la));
		
		File l = new File("/Users/apple/desktop/paper_recommendation/data/kdd_labels.txt");
		BufferedWriter bol = new BufferedWriter(new FileWriter(l));
		File c = new File("/Users/apple/desktop/paper_recommendation/data/kdd.txt");
		BufferedReader bc = new BufferedReader(new FileReader(c));
		File k = new File("/Users/apple/desktop/paper_recommendation/data/kdd_keywords.txt");
		BufferedWriter bkc = new BufferedWriter(new FileWriter(k));
		File ok = new File("/Users/apple/desktop/paper_recommendation/data/kdd_papers_only_keywords_vector.txt");
		BufferedWriter bok = new BufferedWriter(new FileWriter(ok));
		String line, vec; 
		HashMap<Integer,ArrayRealVector> vectors = new HashMap<>();
		HashMap<String, Integer> map1 = new HashMap<>();
		HashMap<String, Integer> venue = new HashMap<>();
		HashMap<String, ArrayRealVector> map = new HashMap<>();
		HashMap<String, ArrayRealVector> map2 = new HashMap<>();
		HashMap<Integer, ArrayRealVector> ven_vecs = new HashMap<>();
		HashMap<Integer, Integer> mp = new HashMap<>();
		HashMap<Integer, Integer> keywords = new HashMap<>();
		
	
		String conf = "";
		String venlabel;
		String index = "";
		double alpha = 0.5;
		int no = 0;
		ArrayList<String> ccf = new ArrayList<>();
		while((line = bc.readLine()) != null)
		{
			ccf.add(line);
		}
		while ((line = bn.readLine()) != null) {
			String ven = "";
			String[] sp = line.split(",,,");
			ven = sp[1];
			map1.put(ven, Integer.parseInt(sp[0]));
		}
		while((line = bd.readLine())!=null)
		{
			if(line.length()!=0)
			{
				if(line.charAt(1)=='i')
					index = line.substring(6);
				else if(line.charAt(1)=='c')
					conf = line.substring(2);		
			}
			else if(ccf.contains(conf))
			{
				if(!mp.containsKey(map1.get(conf)))
					mp.put(map1.get(conf), no++);
				venue.put(index, map1.get(conf));
			}
				
			
		}
		System.out.println(venue.size());
//--------------------------------------------------------------------------------------get keywords& venue	
		while((line = bl.readLine())!=null&&(vec = bk.readLine())!=null)
		{
			ArrayRealVector arv = new ArrayRealVector(50);
			String[] sp = vec.split(" ");
			for(int i = 0; i < 50; i++)
				arv.addToEntry(i, Double.parseDouble(sp[i]));
			vectors.put(Integer.parseInt(line), arv);//keywords' vectors
		}
		while((line = bv.readLine())!=null&&(venlabel = ba.readLine())!=null)
		{
			if(mp.containsKey(Integer.parseInt(venlabel)))
			{
				ArrayRealVector ven_vec = new ArrayRealVector(50);
				String sp[] = line.split(" ");
				for(int i = 0; i < 50; i++)
					ven_vec.addToEntry(i, Double.parseDouble(sp[i]));
				ven_vecs.put(Integer.parseInt(venlabel), ven_vec);
			//	System.out.println(venlabel);
			}
			
		}
//--------------------------------------------------------------------------------------- get representation
		
		while((line = bf.readLine())!=null)
		{
			
			String[] sp = line.split(",");
			ArrayRealVector temp = new ArrayRealVector(50);
			int count = 0;
			if(venue.containsKey(sp[0]))
			{
			//	System.out.println("contains");
				for(int i = 1; i < sp.length; i++)
				{
					if(vectors.containsKey(Integer.parseInt(sp[i])))
					{
						keywords.put(Integer.parseInt(sp[i]),0);
						temp = temp.add(vectors.get(Integer.parseInt(sp[i])));
						count++;
					}
						
				}
			
				if(count > 0)
				{
					temp = (ArrayRealVector) temp.mapDivide(count);
					map.put(sp[0], temp);
				}
				
			}
	//		System.out.println(no++);
		}
		for(Entry<String, ArrayRealVector> entries: map.entrySet())
		{
			for(int i = 0; i < 50; i++)
				bok.write(entries.getValue().getEntry(i)+" ");
			bok.write("\n");
			if(venue.get(entries.getKey()).equals(map1.get("CVPR")))
				bol.write(2+"\n");
			else if(venue.get(entries.getKey()).equals(map1.get("SODA")))
				bol.write(1+"\n");
			else bol.write(0+"\n");
		}
		System.out.println(map.size());
		for(Entry<String, Integer> entries:venue.entrySet())
		{
			if(ven_vecs.containsKey(entries.getValue()))
				map2.put(entries.getKey(), ven_vecs.get(entries.getValue()));
			
		}
		for(Entry<String, ArrayRealVector> entries: map.entrySet())
		{
			if(map2.get(entries.getKey())!=null)
				map.put(entries.getKey(), (ArrayRealVector) entries.getValue().mapMultiply(alpha).add(map2.get(entries.getKey()).mapMultiply(1-alpha)));
			
		}
	//	System.out.println(map.size());
		
		for(Entry<Integer, Integer> entries: keywords.entrySet())
		{
			bkc.write(entries.getKey()+"\n");
		}
		bd.close();
		bn.close();
		bv.close();
		bk.close();
		bf.close();
		ba.close();
		bl.close();
		bol.close();
		bc.close();
		bkc.close();
		bok.close();
	}

}
