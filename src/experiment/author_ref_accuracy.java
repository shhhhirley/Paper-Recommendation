package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.math3.linear.ArrayRealVector;
public class author_ref_accuracy {
	//根据用户兴趣的推荐！！！
	public static int get_author(HashMap<Integer, ArrayList<String>> authorpaper, String paper)
	{
		
		Set<Integer> set = authorpaper.keySet();
		Iterator it = set.iterator();
		while(it.hasNext())
		{
			int author = (int) it.next();
			if(authorpaper.get(author).contains(paper))
				return author;
		}
		return 0;
		
		
	}

	public static void main(String[] args)throws IOException, ClassNotFoundException
	{
		File d = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		BufferedReader bd = new BufferedReader(new FileReader(d));
		File n = new File("/Users/apple/desktop/paper_recommendation/venue_id.txt");
		BufferedReader bn = new BufferedReader(new FileReader(n));
		File f = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords.txt");
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
		FileInputStream fin = new FileInputStream("/Users/apple/desktop/paper_recommendation/new_data/selected_paper_reference.txt");
		ObjectInputStream in = new ObjectInputStream(fin);	
		HashMap<String, ArrayList<String>> reference = (HashMap<String, ArrayList<String>>) in.readObject();
		FileInputStream fin2 = new FileInputStream("/Users/apple/desktop/paper_recommendation/new_data/selected_reference_paper_after_2014.txt");
		ObjectInputStream in2 = new ObjectInputStream(fin2);	
		HashMap<String, ArrayList<String>> reference2 = (HashMap<String, ArrayList<String>>) in2.readObject();
		File w = new File("/Users/apple/desktop/paper_recommendation/new_results/neg_vec.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(w));
		File t = new File("/Users/apple/desktop/paper_recommendation/new_results/neg_sample.txt");
		BufferedWriter bt = new BufferedWriter(new FileWriter(t));
		String line, vec, venlabel;
		HashMap<Integer,ArrayRealVector> ven_vecs = new HashMap<>();
	
		HashMap<Integer,ArrayRealVector> vectors = new HashMap<>();
		HashMap<String, ArrayRealVector>map = new HashMap<>();
		HashMap<String, ArrayRealVector>map2 = new HashMap<>();
		HashMap<Integer, ArrayList<String>> authorpaper = new HashMap<>();
		ArrayList<String> paperlist;
		double alpha = 0.5;
		HashMap<Integer, ArrayRealVector> authorvector  = new HashMap<>();
		HashMap<String, Integer> map1 = new HashMap<>();
		HashMap<String, Integer> venue = new HashMap<>();
		String conf;
		String index = "";
		int venue_id = 0;
		Random rd = new Random();

		
	
	
//--------------------------------------------------------------------------get_author		
		while ((line = bap.readLine()) != null) {
			if (!authorpaper.containsKey(Integer.parseInt(line.split(",")[1]))) {
				paperlist = new ArrayList<>();
				paperlist.add(line.split(",")[0]);
				authorpaper.put(Integer.parseInt(line.split(",")[1]), paperlist);
			} else {
				paperlist = authorpaper.get(Integer.parseInt(line.split(",")[1]));
				paperlist.add(line.split(",")[0]);
				authorpaper.put(Integer.parseInt(line.split(",")[1]),paperlist);
			}
			
		
		//	System.out.println(linenum++);

		}
		

//-------------------------------------------------------------------------find candidate author set
				HashMap<Integer, ArrayList<String>> sample = new HashMap<>();
			//	HashMap<Integer, ArrayList<String>> neg_sample = new HashMap<>();
				HashMap<String,Integer> sumup = new HashMap<>();
				
				ArrayList<String> written = new ArrayList<>();
				ArrayList<String> cited = new ArrayList<>();
				
				
				
		
				HashMap<String, ArrayList<Integer>> pos = new HashMap<>();
			//	HashMap<String, ArrayList<Integer>> neg = new HashMap<>();
				for(Entry<String, ArrayList<String>> entry: reference2.entrySet())
				{
					ArrayList<Integer> candidate = new ArrayList<>();
					
				
					sumup.put(entry.getKey(),0);
					ArrayList<String> al = entry.getValue();
		
					
				//	ArrayList<Integer> neg_candidate = new ArrayList<>();
					
					for(int i = 0; i < 50; i++)
					{
						int au = 0;
						if(i >= al.size() && i < 40)
							{
							au = rd.nextInt(1750000);
							candidate.add(au);
							
							};
//						else 
//							{
//							au = rd.nextInt(1750000);
//							neg_candidate.add(au);
//							}					
							
					}
					pos.put(entry.getKey(), candidate);
				//	neg.put(entry.getKey(),neg_candidate);
				//	System.out.println(pos.size()+" "+neg.size());
				//	bco.write(candidate+"\n");
					Iterator it1 = candidate.iterator();
					
					while(it1.hasNext())
					{
						ArrayList<String> all_cited = new ArrayList<>();
						 int author = (int) it1.next();
						 written = authorpaper.get(author);
				//		 System.out.println(written);
				//		bco.write(written+"\n");	 
						for(int j = 0; j < written.size(); j++)
							{
							//if(!sumup.contains(written.get(j)))
								sumup.put(written.get(j),0);
								cited = reference.get(written.get(j));
								if(cited != null)
								{
									for(int k = 0; k < cited.size(); k++)
									{
										all_cited.add(cited.get(k));
										sumup.put(cited.get(k),0);
										}			
						//			System.out.println("not empty");
								
										
								}
								
							
							
							}
						if(all_cited.size()!=0)
							sample.put(author, all_cited);	
						
							
					}
					System.out.println(sample.size());
//					Iterator it2 = neg_candidate.iterator();
//					
//					while(it2.hasNext())
//					{
//						 ArrayList<String> all_cited = new ArrayList<>();
//						 int author = (int) it2.next();
//						 written = authorpaper.get(author);
//						 for(int j = 0; j < written.size(); j++)
//							{
//							//if(!sumup.contains(written.get(j)))
//								sumup.put(written.get(j),0);
//								cited = reference.get(written.get(j));
//								if(cited != null)
//								{
//									for(int k = 0; k < cited.size(); k++)
//									{
//										all_cited.add(cited.get(k));
//										sumup.put(cited.get(k),0);
//										}			
//								}
//								
//							
//							
//							}
//						if(all_cited.size()!=0)
//						{
//							neg_sample.put(author, all_cited);
//							bt.write(all_cited+"\n");
//						}
//							
//						else System.out.println("empty");
//						
//							
//					}
					
			//all_cited.clear();
				//	candidate.clear();
			
				}
		
//-------------------------------------------------------------------------get_author_vector
				while((line = bl.readLine())!=null&& (vec = bv.readLine())!=null)
				{
					if(sample.containsKey(Integer.parseInt(line)))
					{
						ArrayRealVector value = new ArrayRealVector(50);
						String sp[] = vec.split(" ");
						
						for(int i = 0; i < sp.length; i++)
							value.addToEntry(i, Double.parseDouble(sp[i]));
						authorvector.put(Integer.parseInt(line), value);
					}
				
				}
		
//-------------------------------------------------------------------------get_keywords_vector		
		
		while((line = bkl.readLine())!=null&&(vec = bkey.readLine())!=null)
		{
			ArrayRealVector arv = new ArrayRealVector(50);
			String[] sp = vec.split(" ");
			for(int i = 0; i < 50; i++)
				arv.addToEntry(i, Double.parseDouble(sp[i]));
			vectors.put(Integer.parseInt(line), arv);
		}
	//	System.out.println(vectors.size());

//--------------------------------------------------------------------------get_representation	
		
		while((line = bf.readLine())!=null)
				{
					
					String[] sp = line.split(",");
					ArrayRealVector temp = new ArrayRealVector(50);
					int count = 0;
					if(sumup.containsKey(sp[0]))
					{
						
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
						
					}
			//		System.out.println(no++);
				}
		
		
		//			System.out.println("map"+":"+map.size());
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
			
		//		System.out.println(venue.size());
			
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
		

 //System.out.println(map.size());
		
 for(Entry<String, ArrayList<Integer>>entry: pos.entrySet())
 {
	 
	
	// System.out.println(entry.getValue().size());
	 if(entry.getValue().size()!=0)
	 {
		 Iterator it = entry.getValue().iterator();
		
		 while(it.hasNext())
		 {
			 ArrayRealVector temp = new ArrayRealVector(50);
			 int author = (int)it.next();
			 int count = 0;
			ArrayList<String> sam = sample.get(author);
			if(sam!=null)
			{
				for(int i = 0; i < sam.size(); i ++)
				{
					if(map.containsKey(sam.get(i)))
					{
						temp = temp.add(map.get(sam.get(i)));
						count++;
					}
					
				}
				temp.mapDivideToSelf(count);
				bw.write(temp+"\n");
			}
			
//			bt.write(temp+"\n");
//			if(authorvector.containsKey(author))
//			{
////			//	temp.mapMultiplyToSelf(0.9);
//				temp = (ArrayRealVector) temp.mapMultiply(0.8).add(authorvector.get(author).mapMultiply(0.2));
////				//temp.mapDivideToSelf(2);
////				
//			}
			
			
	//		bco.write(temp.cosine()+" ");
			
		//	bran.write(temp+"\n");
			// temp = temp.add(author_vectors.get(get_author(authorpaper,entry.getKey())));
		
			
		 }
		 bw.write("\\"+"\n");

	 }
 }
 for(Entry<Integer, ArrayList<String>>entry: sample.entrySet())
 {
	 bt.write(entry.getKey()+"\n");
 }
// for(Entry<String, ArrayList<Integer>>entry: neg.entrySet())
// {
//	 
//	
//	 //System.out.println(entry.getValue().size());
//	 if(entry.getValue().size()!=0)
//	 {
//		 Iterator it = entry.getValue().iterator();
//		 
//		 while(it.hasNext())
//		 {
//			 
//			 ArrayRealVector temp = new ArrayRealVector(50);
//			 int count = 0;
//			 int aut = (int) it.next();
//			
//			ArrayList<String> sam = neg_sample.get(aut);
//			if(sam != null)
//			{
//				for(int i = 0; i < sam.size(); i++)
//				{
//					if(map.containsKey(sam.get(i)))
//					{
//						temp = temp.add(map.get(sam.get(i)));
//						count++;
//					}
//					
//				}
//				temp.mapDivide(count);
//			//	 bt.write(temp+" ");
//		//		bt.write(sam+"\n");
//			//	bran.write(temp+"\n");
//				// temp = temp.add(author_vectors.get(get_author(authorpaper,entry.getKey())));
//				bran.write(temp.cosine(map.get(entry.getKey()))+" ");
//			}
//			
//			
//		 }
//	//	 bt.write("\n");
//		 bran.write("\n");
//
//	 }
// }
 


		
			
		bf.close();
	
		bkl.close();
		bkey.close();
	//	bran.close();
		in.close();
		in2.close();
		//bco.close();
		bap.close();
		bven.close();
		bven_la.close();
		bd.close();
		bn.close();
		bl.close();
		bv.close();
		bw.close();
		bt.close();
	
		
	}
}
