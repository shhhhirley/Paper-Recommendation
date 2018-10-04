package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.math3.linear.ArrayRealVector;
public class query {
//cbf 和 porposed methods
	public static void main(String[] args)throws IOException
	{
		File d = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		BufferedReader bd = new BufferedReader(new FileReader(d));
		File n = new File("/Users/apple/desktop/paper_recommendation/venue_id.txt");
		BufferedReader bn = new BufferedReader(new FileReader(n));
		File cf = new File("/Users/apple/desktop/paper_recommendation/data/ccf1.txt");
		BufferedReader bcf = new BufferedReader(new FileReader(cf));
		File check = new File("/Users/apple/desktop/check");
		BufferedWriter bw = new BufferedWriter(new FileWriter(check));
		ArrayList<String> ch = new ArrayList<>();
		File pi = new File("/Users/apple/desktop/paper_recommendation/data/paper_id.txt");
		File ai = new File("/Users/apple/desktop/paper_recommendation/data/author_id.txt");
		File a = new File("/Users/apple/desktop/paper_recommendation/data/authors_vectors.txt");
		File ala = new File("/Users/apple/desktop/paper_recommendation/data/author_labels.txt");
		File p = new File("/Users/apple/desktop/paper_recommendation/data/phrases_id.txt");
		File u = new File("/Users/apple/desktop/paper_recommendation/data/unigrams_id.txt");
		File k = new File("/Users/apple/desktop/paper_recommendation/data/keywords_vectors.txt");
		File l = new File("/Users/apple/desktop/paper_recommendation/data/keywords_labels.txt");
		File c = new File("/Users/apple/desktop/paper_recommendation/data/ccf_keywords.txt");
		File pl = new File("/Users/apple/desktop/paper_recommendation/new_results/outcome_labels.txt");
		File pk = new File("/Users/apple/desktop/paper_recommendation/data/ccf_papers_only_keywords_vector.txt");
		File f = new File("/Users/apple/desktop/paper_recommendation/data/idf.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		File fp = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords_in_2015.txt");
		BufferedReader bpi = new BufferedReader(new FileReader(pi));
		BufferedReader ba = new BufferedReader(new FileReader(a));
		BufferedReader bala = new BufferedReader(new FileReader(ala));
		BufferedReader bfp = new BufferedReader(new FileReader(fp));
		BufferedReader bp = new BufferedReader(new FileReader(p));
		BufferedReader bu = new BufferedReader(new FileReader(u));
		BufferedReader bk = new BufferedReader(new FileReader(k));
		BufferedReader bl = new BufferedReader(new FileReader(l));
		BufferedReader bc = new BufferedReader(new FileReader(c));
		BufferedReader bpl = new BufferedReader(new FileReader(pl));
		BufferedReader bpk = new BufferedReader(new FileReader(pk));
		BufferedReader bai = new BufferedReader(new FileReader(ai));
		String linep, lineu, line;
		HashMap<String,Integer> keywords = new HashMap<>();
		HashMap<Integer,ArrayRealVector>labels = new HashMap<>();
		HashMap<Integer,Integer> ccf = new HashMap<>();
		ArrayList<String> ccf1 = new ArrayList<>();
		
		HashMap<String, ArrayRealVector> paper_vector = new HashMap<>();
		HashMap<String, Double> cosine_value = new HashMap<>();
		HashMap<String, Double> tfidf_cos = new HashMap<>();
		HashMap<Integer, Double> idf = new HashMap<>();
		HashMap<String, ArrayList<Integer>> pu = new HashMap<>();
		HashMap<String, ArrayList<Double>> paper_tfidf = new HashMap<>();
		String index = "", conf = "";
		HashMap<String, String> venue = new HashMap<>();
		ArrayList<String>keyword = new ArrayList<>();
		HashMap<String, Integer> authorid = new HashMap<>();
		HashMap<Integer, ArrayRealVector> authorvec = new HashMap<>();
		HashMap<String,String>paperid = new HashMap<>();
		keyword.add("Computer Architecture and High Performance Computing");
		keyword.add("Parallel");
		
		while((line = bpi.readLine())!=null)
		{
			paperid.put(line.split("\\*")[0], line.split("\\*")[1]);
		}
	while((line = bai.readLine())!=null)
	{
		authorid.put(line.split(",")[0], Integer.parseInt(line.split(",")[1]));
		
	}
	while((line = bala.readLine())!=null&&(linep = ba.readLine())!=null)
	{
		ArrayRealVector ar = new ArrayRealVector(50);
		for(int i = 0; i < 50; i++)
			ar.addToEntry(i, Double.parseDouble(linep.split(" ")[i]));
		authorvec.put(Integer.parseInt(line), ar);
	}
		
		int num = 0;
		while((line = bcf.readLine()) != null)
		{
			ccf1.add(line);
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
			else if(ccf1.contains(conf))
			{
				venue.put(index, conf);
			}
				
			
		}
		ccf1.clear();
		for(Entry<String, String> entry: venue.entrySet())
		{
			if(!ch.contains(entry.getValue()))
			{
				bw.write(entry.getValue()+"\n");
				ch.add(entry.getValue());
			}
				
		}
		
		while((line = bf.readLine())!=null)
		{
			idf.put(Integer.parseInt(line.split(" ")[0]), Double.parseDouble(line.split(" ")[1]));
		}
		while((line = bfp.readLine())!=null)
		{
			String sp[] = line.split(",");
			ArrayList<Integer> uni = new ArrayList<>();
			for(int i = 1; i < sp.length; i++)
				uni.add(Integer.parseInt(sp[i]));
			pu.put(sp[0], uni);//paper-keywords
		}
		
		while((line = bpk.readLine())!=null&&(lineu = bpl.readLine())!=null)
		{
			if(pu.containsKey(lineu))
			{
				ArrayRealVector ar = new ArrayRealVector(50);
				for(int i = 0; i < 50; i++)
					ar.addToEntry(i, Double.parseDouble(line.split(" ")[i]));
				paper_vector.put(lineu, ar);
			}
			
		}
		for(Entry<String, ArrayList<Integer>> entries: pu.entrySet())
		{
			if(paper_vector.containsKey(entries.getKey()))
			{
				ArrayList<Integer> al = new ArrayList<>();
				ArrayList<Double> ar = new ArrayList<>();
				HashMap<Integer, Integer>dup = new HashMap<>();
				al = entries.getValue();
				for(int i = 0; i < al.size(); i++)
				{
					if(!dup.containsKey(al.get(i)))
						dup.put(al.get(i), 1);
					else dup.put(al.get(i), dup.get(al.get(i))+1);
						
				}
				for(int i = 0; i < al.size(); i++)
					ar.add(i, (double)dup.get(al.get(i))/al.size()*idf.get(al.get(i)));	
				paper_tfidf.put(entries.getKey(), ar);
		//		System.out.println(ar.size());
			}
			
		}
		//System.out.println(paper_tfidf.size());
		while((line = bc.readLine())!=null)
		{
			ccf.put(num++,Integer.parseInt(line));
		}
		while((linep = bp.readLine())!=null)
			keywords.put(linep.split(",")[0],Integer.parseInt(linep.split(",")[1]));
	
		while((lineu = bu.readLine())!=null)
			keywords.put(lineu.split(",")[0],Integer.parseInt(lineu.split(",")[1]));
		
		while((linep = bk.readLine())!=null&&(lineu = bl.readLine())!=null)
		{
			ArrayRealVector ar = new ArrayRealVector(50);
			for(int i = 0; i < 50; i++)
				ar.addToEntry(i, Double.parseDouble(linep.split(" ")[i]));
			labels.put(Integer.parseInt(lineu), ar);
		}
	//	System.out.println(labels.size());
		ArrayRealVector ar = new ArrayRealVector(50);
		ArrayList<Double> tfidf = new ArrayList<>();
		
		for(int i = 0; i < keyword.size(); i++)
		{
			String keyw = keyword.get(i);
			if(keywords.containsKey(keyw))
			{
				int key = keywords.get(keyw);
				
				if(labels.containsKey(key))
				{
					ar = ar.add(labels.get(key));//我们计算出的关键词向量
					//计算tf-idf
					double ti = 0.2*idf.get(key);
					tfidf.add(ti);
				}	
			}
		
			
		}
	//	System.out.println(tfidf);
		ar = (ArrayRealVector) ar.mapDivide(5);//mean value of query
		ar = (ArrayRealVector) ar.mapMultiply(0.5).add(authorvec.get(authorid.get("David Patterson")).mapMultiply(0.5));
		System.out.println(ar);
		for(Entry<String, ArrayRealVector> entries: paper_vector.entrySet())
			cosine_value.put(entries.getKey(), ar.cosine(entries.getValue()));
		
		for(Entry<String, ArrayList<Double>>entries: paper_tfidf.entrySet())
		{
			double similarity = 0;
			double squarer = 0, squarep = 0;
			ArrayList<Double> al = entries.getValue();
			
			if(al.size()>5)
			{
				
				for(int i = 0; i < al.size() && i < tfidf.size(); i++)
				{
					similarity += tfidf.get(i)*al.get(i);
					squarer += Math.pow(tfidf.get(i), 2);
					squarep += Math.pow(al.get(i), 2);
				}
			//	System.out.println(squarer+" "+squarep);
				if(squarer != 0 && squarep != 0)
				{
					tfidf_cos.put(entries.getKey(), similarity/(Math.pow(squarer, 0.5)*Math.pow(squarep, 0.5)));
				}
				
			}
		
		}
		System.out.println(tfidf_cos.size());
		ArrayList<Entry<String, Double>>list = new ArrayList<>(cosine_value.entrySet());
		ArrayList<Entry<String, Double>>list2 = new ArrayList<>(tfidf_cos.entrySet());
		Collections.sort(list,new Comparator<Entry<String, Double>>()
		{
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2)
			{
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		Collections.sort(list2,new Comparator<Entry<String, Double>>()
		{
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2)
			{
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		for(int i = 0; i < 20; i++)
		{
			if(venue.containsKey(list.get(i).getKey()))
				System.out.println(paperid.get(list.get(i).getKey()));
		}
			
		System.out.println("\n");
		for(int i = 0; i < 20; i++)
		{
			if(venue.containsKey(list2.get(i).getKey()))
				System.out.println(paperid.get(list2.get(i).getKey()));
		}
			
		
	//	System.out.println(keywords.size());
		bp.close();
		bu.close();
		bk.close();
		bl.close();
		bc.close();
		bpl.close();
		bpk.close();
		bf.close();
		bfp.close();
		bd.close();
		bn.close();
		bcf.close();
		bw.close();
		ba.close();
		bala.close();
		bai.close(); 
		bpi.close();
	}
}
