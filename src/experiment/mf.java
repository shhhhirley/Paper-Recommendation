package experiment;
import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.math3.linear.ArrayRealVector;
public class mf {
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
		File f = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));	
		File l = new File("/Users/apple/desktop/paper_recommendation/results/author_labels.txt");
		BufferedReader bl = new BufferedReader(new FileReader(l));
		File ap = new File("/Users/apple/desktop/paper_recommendation/data/author_paper_id.txt");
		BufferedReader bap = new BufferedReader(new FileReader(ap));
		FileInputStream fin = new FileInputStream("/Users/apple/desktop/paper_recommendation/new_data/selected_paper_reference.txt");
		ObjectInputStream in = new ObjectInputStream(fin);	
		HashMap<String, ArrayList<String>> reference = (HashMap<String, ArrayList<String>>) in.readObject();
		FileInputStream fin2 = new FileInputStream("/Users/apple/desktop/paper_recommendation/new_data/selected_reference_paper_after_2014.txt");
		ObjectInputStream in2 = new ObjectInputStream(fin2);	
		HashMap<String, ArrayList<String>> reference2 = (HashMap<String, ArrayList<String>>) in2.readObject();
	
		String line;
		
		
		HashMap<Integer, ArrayList<String>> authorpaper = new HashMap<>();
		ArrayList<String> paperlist;
//		File m = new File("/Users/apple/desktop/paper_recommendation/results/author_label.txt");
//		BufferedWriter bw = new BufferedWriter(new FileWriter(m));
		File z = new File("/Users/apple/desktop/paper_recommendation/results/matrix_label.txt");
		BufferedWriter bz = new BufferedWriter(new FileWriter(z));
//		File ac = new File("/Users/apple/desktop/paper_recommendation/results/author_citation.txt");
//		BufferedWriter bac = new BufferedWriter(new FileWriter(ac));
	
	
	
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
		System.out.println("author over");
//-------------------------------------------------------------------------find candidate author set
				
				
				ArrayList<String> written = new ArrayList<>();
				ArrayList<String> cited = new ArrayList<>();
				HashMap<Integer, Integer> candidate = new HashMap<>();
				HashMap<String, Integer> all_papers = new HashMap<>();
				
				int auth = 0;
				for(Entry<String, ArrayList<String>> entry: reference2.entrySet())
				{
					
					ArrayList<String> al = entry.getValue();
				
					
					for(int i = 0; i < al.size(); i++)
					{
							candidate.put(get_author(authorpaper,al.get(i)),auth++);									
					}
				}
				//	bco.write(candidate+"\n");
					
//					int author = 0;
//					for(Entry<Integer, Integer>cand: candidate.entrySet())
//					{
//						
//						ArrayList<String> al = authorpaper.get(cand.getKey());
//						int num = 0;
//						for(Entry<String, ArrayList<String>> entry: reference2.entrySet())
//						{
//							ArrayList<String> al2 = entry.getValue();
//							for(int i = 0; i < al2.size(); i++)
//							{
//								if(al.contains(al2.get(i)))
//								{
//									bac.write(author+" "+num+"\n");
//									break;
//								}
//										
//							}
//								
//							num++;
//						}
//						author++;
//							
//					
//					}
//----------------------------------------------------------------------------------给文章编号
					int n = 0;
					for(Entry<Integer, Integer>cand: candidate.entrySet())
					{
						 written = authorpaper.get(cand.getKey());
				//		 System.out.println(written);
				//		bco.write(written+"\n");	 
						for(int j = 0; j < written.size(); j++)
							{
							
								cited = reference.get(written.get(j));
								if(cited != null)
								{
									for(int k = 0; k < cited.size(); k++)
									{
										String c = cited.get(k);
										if(!all_papers.containsKey(c))
											all_papers.put(c,n++);						
										}			
						//			System.out.println("not empty");
								
										
								}
							}	
						
							
					}
					for(Entry<String, Integer> entry: all_papers.entrySet())
					{
						bz.write(entry.getKey()+"\n");
					}
//------------------------------------------------------------------------------------------计算矩阵					
					
//					HashMap<String, Integer> mp = new HashMap<>();
//					for(Entry<Integer, Integer>cand: candidate.entrySet())
//					{
//						written = authorpaper.get(cand.getKey());
//						for(int i = 0; i < written.size(); i++)
//						{
//							cited = reference.get(written.get(i));
//							if(cited != null)
//							{
//								for(int k = 0; k < cited.size(); k++)
//								{
//									String c = cited.get(k);
//									if(!mp.containsKey(c)||reference2.containsKey(c))
//										mp.put(c, 0);	
//									else mp.put(c, mp.get(c)+1);
//									}			
//					//			System.out.println("not empty");
//							
//									
//							}
//						}
//						bw.write("[");
//						for(Entry<String, Integer> entry:all_papers.entrySet())
//						{
//							bz.write(entry.getKey()+"\n");
//							if(!mp.containsKey(entry.getKey()))
//								bw.write(0+",");
//							else
//								bw.write(mp.get(entry.getKey())+",");
//						}
//						bw.write("],"+"\n");
//						mp.clear();
						
//					}
					
					

				
		
			
	System.out.println("number of papers"+" "+all_papers.size());

		
 



		
			
		bf.close();	
		bl.close();		
		in.close();
		in2.close();
	//	bw.close();
		bz.close();
	//	bac.close();
		bap.close();
		bd.close();
	
		
	}
}
