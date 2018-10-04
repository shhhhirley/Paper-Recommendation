package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.math3.linear.ArrayRealVector;
public class cbf {

	public static void main(String[] args)throws IOException, ClassNotFoundException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/idf.txt");
		File u = new File("/Users/apple/desktop/paper_recommendation/data/unigrams_id.txt");
		File p = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords.txt");
		File w = new File("/Users/apple/desktop/paper_recommendation/results/cbf.txt");
		File c = new File("/Users/apple/desktop/paper_recommendation/results/check.txt");
		File g = new File("/Users/apple/desktop/paper_recommendation/results/neg_cbf.txt");
		File n = new File("/Users/apple/desktop/paper_recommendation/new_results/neg_sample.txt");
		File ew = new File("/Users/apple/desktop/paper_recommendation/results/euc_cbf.txt");
		
		File eg = new File("/Users/apple/desktop/paper_recommendation/results/euc_neg_cbf.txt");
		BufferedReader bn = new BufferedReader(new FileReader(n));
	//	File ph = new File("/Users/apple/desktop/paper_recommendation/data/paper_phrases.txt");
	//	File ph = new File("/Users/apple/desktop/paper_recommendation/data/paper_phrases.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		BufferedReader bu = new BufferedReader(new FileReader(u));
		BufferedReader bp = new BufferedReader(new FileReader(p));
		BufferedWriter bw = new BufferedWriter(new FileWriter(w));
		BufferedReader bk = new BufferedReader(new FileReader(p));
		BufferedWriter bc = new BufferedWriter(new FileWriter(c));
		BufferedWriter bg = new BufferedWriter(new FileWriter(g));
		BufferedWriter bew = new BufferedWriter(new FileWriter(ew));
		BufferedWriter beg = new BufferedWriter(new FileWriter(eg));
	//	BufferedReader bph = new BufferedReader(new FileReader(ph));
	//	BufferedReader bph = new BufferedReader(new FileReader(ph));
		FileInputStream fin = new FileInputStream("/Users/apple/desktop/paper_recommendation/new_data/selected_reference_paper_after_2014.txt");
		ObjectInputStream in = new ObjectInputStream(fin);
		HashMap<String, ArrayList<String>> reference = (HashMap<String, ArrayList<String>>) in.readObject();
		String line, key; 
		Random rd = new Random();
		ArrayList<String> al = new ArrayList<>();
		HashMap<Integer, Double> idf = new HashMap<>();
		//HashMap<String, ArrayList<Double>> tfidf = new HashMap<>();
		HashMap<String, ArrayList<Integer>> pu = new HashMap<>();
		ArrayList<Integer> ns = new ArrayList<>();
		ArrayList<String> allpaper = new ArrayList<>();
		while((line = bn.readLine())!=null)
		{
			ns.add(Integer.parseInt(line));
		}
		while((key = bf.readLine())!=null)
		{
			idf.put(Integer.parseInt(key.split(" ")[0]), Double.parseDouble(key.split(" ")[1]));
		}
		
		while((line = bp.readLine())!=null)
		{
			String sp[] = line.split(",");
			ArrayList<Integer> uni = new ArrayList<>();
			for(int i = 1; i < sp.length; i++)
				uni.add(Integer.parseInt(sp[i]));
			pu.put(sp[0], uni);
			allpaper.add(sp[0]);
		}
		
		while((line = bk.readLine())!=null)
		{
			String sp[] = line.split(",");
			
		//	String sp2[] = line2.split(",");
			if(reference.containsKey(sp[0])&&!al.contains(sp[0]))
			{
				al.add(sp[0]);
				ArrayList<Double> ar = new ArrayList<>();
				HashMap<Integer,Integer> dup = new HashMap<>();
				for(int i = 1; i < sp.length; i ++)
				{
					if(!dup.containsKey(Integer.parseInt(sp[i])))
						dup.put(Integer.parseInt(sp[i]),1);
					else
						dup.put(Integer.parseInt(sp[i]), dup.get(Integer.parseInt(sp[i]))+1);		
					
				}
				for(int i = 1; i < sp.length; i ++)
					ar.add(i-1, (double)dup.get(Integer.parseInt(sp[i]))/sp.length*idf.get(Integer.parseInt(sp[i])));
			//	tfidf.put(sp[0], ar);
				ArrayList<String> paper = reference.get(sp[0]);
				for(int i = 0; i < paper.size(); i++)
				{
					ArrayList<Double> ap = new ArrayList<>();
					ArrayList<Integer> paper_unigrams = pu.get(paper.get(i));
					HashMap<Integer,Integer> dupl = new HashMap<>();
					for(int j = 0; j < paper_unigrams.size(); j++)
					{	
						if(!dupl.containsKey(paper_unigrams.get(j)))
								dupl.put(paper_unigrams.get(j),1);
							else
								dupl.put(paper_unigrams.get(j), dupl.get(paper_unigrams.get(j))+1);	
						
					}
					for(int j = 0; j < paper_unigrams.size(); j++)
						ap.add(j, (double)dupl.get(paper_unigrams.get(j))/paper_unigrams.size()*idf.get(paper_unigrams.get(j)));
					double similarity = 0;
					double squarer = 0, squarep = 0;
					double euc = 0;
					if(ar.size()!=0&&ap.size()!=0)
					{
						for(int k = 0; k < ar.size() && k < ap.size(); k++)
						{
							euc += Math.pow(ar.get(k)-ap.get(k),2);
							similarity += ar.get(k)*ap.get(k);
							squarer += Math.pow(ar.get(k), 2);
							squarep += Math.pow(ap.get(k), 2);
						}
						if(squarer != 0 && squarep != 0)
						{
							bew.write(euc+" ");
							bw.write(similarity/(Math.pow(squarer, 0.5)*Math.pow(squarep, 0.5))+" ");
						}
						if(similarity/(Math.pow(squarer, 0.5)*Math.pow(squarep, 0.5))== 1)
							bc.write(similarity+" "+Math.pow(squarer, 0.5)+" "+Math.pow(squarep, 0.5)+"\n");
					
							
					}
					
					
					
				}
				for(int i = 0; i < 20 - paper.size(); i++)
				{
					ArrayList<Double> ap = new ArrayList<>();
					HashMap<Integer,Integer> dupl = new HashMap<>();
					int next = rd.nextInt(allpaper.size());
					ArrayList<Integer> neg = pu.get(allpaper.get(next));
					for(int j = 0; j < neg.size(); j++)
					{	
						if(!dupl.containsKey(neg.get(j)))
								dupl.put(neg.get(j),1);
							else
								dupl.put(neg.get(j), dupl.get(neg.get(j))+1);	
					}
					for(int j = 0; j < neg.size(); j++)
						ap.add(j, (double)dupl.get(neg.get(j))/neg.size()*idf.get(neg.get(j)));
					double similarity = 0;
					double squarer = 0, squarep = 0;
					double euc = 0;
					if(ar.size()!=0&&ap.size()!=0)
					{
						for(int k = 0; k < ar.size() && k < ap.size(); k++)
						{
							euc += Math.pow(ar.get(k)-ap.get(k), 2);
							similarity += ar.get(k)*ap.get(k);
							squarer += Math.pow(ar.get(k), 2);
							squarep += Math.pow(ap.get(k), 2);
						}
						if(squarer != 0 && squarep != 0)
						{
						beg.write(euc+" ");
							bg.write(similarity/(Math.pow(squarer, 0.5)*Math.pow(squarep, 0.5))+" ");
						}
						
					
							
					}
					
				}
			bw.write("\n");
			bg.write("\n");
			bew.write("\n");
			beg.write("\n");
			}
			
			
			
			
		}
	//	System.out.println(tfidf);
	//	System.out.println(reference.size());
		
		bf.close();
		bu.close();
		bp.close();
		in.close();
		bw.close();
		bk.close();
		bc.close();
		bg.close();
		bn.close();
		bew.close();
		beg.close();
		
		
	}
}
