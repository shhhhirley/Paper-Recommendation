package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.math3.linear.ArrayRealVector;
public class test_accuracy {
//这个不是推荐的！！！是作图的时候找的paper_author distance!!!!
	public static void main(String[] args)throws IOException, ClassNotFoundException
	{
		File d = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		BufferedReader bd = new BufferedReader(new FileReader(d));
		File n = new File("/Users/apple/desktop/paper_recommendation/venue_id.txt");
		BufferedReader bn = new BufferedReader(new FileReader(n));
		File f = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords_after_2014.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		File l = new File("/Users/apple/desktop/paper_recommendation/results/author_labels.txt");
		BufferedReader bl = new BufferedReader(new FileReader(l));
		File v = new File("/Users/apple/desktop/paper_recommendation/results/authors_vectors.txt");
		BufferedReader bv = new BufferedReader(new FileReader(v));
		File key = new File("/Users/apple/desktop/paper_recommendation/results/keywords_vectors.txt");
		BufferedReader bkey = new BufferedReader(new FileReader(key));
		File kl = new File("/Users/apple/desktop/paper_recommendation/results/keywords_labels.txt");
		BufferedReader bkl = new BufferedReader(new FileReader(kl));
		File venu = new File("/Users/apple/desktop/paper_recommendation/results/venues_vectors.txt");
		BufferedReader bven = new BufferedReader(new FileReader(venu));
		File ven_la = new File("/Users/apple/desktop/paper_recommendation/results/venue_labels.txt");
		BufferedReader bven_la = new BufferedReader(new FileReader(ven_la));
		File ap = new File("/Users/apple/desktop/paper_recommendation/data/author_paper_id.txt");
		BufferedReader bap = new BufferedReader(new FileReader(ap));
		FileInputStream fin = new FileInputStream("/Users/apple/desktop/paper_recommendation/new_data/selected_reference_paper_after_2014.txt");
		ObjectInputStream in = new ObjectInputStream(fin);
		HashMap<String, ArrayList<String>> reference = (HashMap<String, ArrayList<String>>) in.readObject();
		File k_vec = new File("/Users/apple/desktop/paper_recommendation/results/vectors_of_selected_paper.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(k_vec));
		File co = new File("/Users/apple/desktop/paper_recommendation/results/without_author_euc.txt");
		BufferedWriter bco = new BufferedWriter(new FileWriter(co));
		File ran = new File("/Users/apple/desktop/paper_recommendation/results/random_euc.txt");
		BufferedWriter bran = new BufferedWriter(new FileWriter(ran));
		String line, vec, venlabel;
		HashMap<Integer,ArrayRealVector> ven_vecs = new HashMap<>();
		HashMap<Integer,ArrayRealVector> vectors = new HashMap<>();
		HashMap<Integer,ArrayRealVector> auth_vectors = new HashMap<>();
		HashMap<String, ArrayRealVector>map = new HashMap<>();
		HashMap<String, ArrayRealVector>map2 = new HashMap<>();
		HashMap<String, ArrayList<Integer>> authormap = new HashMap<>();
		ArrayList<Integer> authorlist;
		Random rd = new Random();
		double alpha = 0.5;
		HashMap<String, Integer> map1 = new HashMap<>();
		HashMap<String, Integer> venue = new HashMap<>();
		String conf;
		String index = "";
		int venue_id = 0;

		
//--------------------------------------------------------------------------get_author		
		while ((line = bap.readLine()) != null) {//一篇文章的所有author
			
			if (!authormap.containsKey(line.split(",")[0])) {
				authorlist = new ArrayList<>();
				authorlist.add(Integer.parseInt(line.split(",")[1]));
				authormap.put(line.split(",")[0], authorlist);
			} else {
				authorlist = authormap.get(line.split(",")[0]);
				authorlist.add(Integer.parseInt(line.split(",")[1]));
				authormap.put(line.split(",")[0], authorlist);
			}

		}
		System.out.println("author over");
//-------------------------------------------------------------------------get_keywords_vector		
		while((line = bl.readLine())!=null&&(vec = bv.readLine())!=null)
		{
			ArrayRealVector arv = new ArrayRealVector(50);
			String[] sp = vec.split(" ");
			for(int i = 0; i < 50; i++)
				arv.addToEntry(i, Double.parseDouble(sp[i]));
			auth_vectors.put(Integer.parseInt(line), arv);
		}
		while((line = bkl.readLine())!=null&&(vec = bkey.readLine())!=null)
		{
			ArrayRealVector arv = new ArrayRealVector(50);
			String[] sp = vec.split(" ");
			for(int i = 0; i < 50; i++)
				arv.addToEntry(i, Double.parseDouble(sp[i]));
			vectors.put(Integer.parseInt(line), arv);
		}
		System.out.println(vectors.size());

//--------------------------------------------------------------------------get_representation	
		int no_key = 0;
		while((line = bf.readLine())!=null)//一篇文章包含的keywords向量
				{
					
					String[] sp = line.split(",");
					ArrayRealVector temp  = new ArrayRealVector(50);
					int count = 0;
					for(int i = 1; i < sp.length; i++)
					{
						if(vectors.containsKey(Integer.parseInt(sp[i])))
						{
							temp = temp.add(vectors.get(Integer.parseInt(sp[i])));
							count++;
						}
							
					}
				
					if(count > 0)
					{
						temp = (ArrayRealVector) temp.mapDivide(count);
						map.put(sp[0], temp);
					}
					else no_key++;
					
				//	System.out.println(linenum++);
					//bw.write(temp+"\n");
				}
				System.out.println(map.size()+ " " + no_key);		
//----------------------------------------------------------------------get_venue	
				while ((line = bn.readLine()) != null) {
					String ven = "";
					String[] sp = line.split(",,,");
					ven = sp[1];

					map1.put(ven, Integer.parseInt(sp[0]));
				}

				while ((line = bd.readLine()) != null) {
					if (line.length() > 0) {
						if (line.charAt(1) == 'i')
							index = line.substring(6);// 获得index
						else if (line.charAt(1) == 'c') {
							conf = line.substring(2);
							// System.out.print(conf);
							venue_id = map1.get(conf);
							// System.out.print(venue_id);
						}

					} else if(map.containsKey(index))
						venue.put(index, venue_id);

				}
				System.out.println(venue.size());
//------------------------------------------------------------get_venue_representaiton		
		while((line = bven.readLine())!=null&&(venlabel = bven_la.readLine())!=null)
		{
			ArrayRealVector ven_vec = new ArrayRealVector(50);
			String sp[] = line.split(" ");
			for(int i = 0; i < 50; i++)
				ven_vec.addToEntry(i, Double.parseDouble(sp[i]));
			ven_vecs.put(Integer.parseInt(venlabel), ven_vec);
			
		}
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
		
		System.out.println("venue_vector over");//map里面是一篇文章的向量
//------------------------------------------------------------find similarity		
		for(Entry<String, ArrayList<String>>entries:reference.entrySet())
		{
			ArrayList<String> al = new ArrayList<>();
			al = entries.getValue();
			for(int i = 0; i < al.size(); i++)
			{

				if(map.containsKey(al.get(i)))
				{
					bco.write(map.get(entries.getKey()).getDistance(map.get(al.get(i)))+" ");
				}
			}
			for(int i = al.size()-1; i < 20; i++)
				rd.nextInt();
			bco.write("\n");
			bran.write("\n");
				
		}
		
			
		bf.close();
		bv.close();
		bl.close();
		bkl.close();
		bkey.close();
		bw.close();
		in.close();
		bco.close();
		bap.close();
		bran.close();
		bven.close();
		bven_la.close();
		bd.close();
		bn.close();
	
		
	}
}
