package experiment;
import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.analysis.function.Sigmoid;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVectorChangingVisitor;

import java.math.*;
import java.util.Vector;
import java.util.Random;
import java.io.*;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sgd_2 {
	public static HashMap<Integer, ArrayRealVector> keywords = new HashMap<>();
	public static HashMap<Integer, ArrayRealVector> venues = new HashMap<>();
	public static HashMap<Integer, ArrayRealVector> authors = new HashMap<>();
	public static ArrayRealVector p = new ArrayRealVector(50);
	public static ArrayRealVector q = new ArrayRealVector(50);
	

	public static ArrayList<Map.Entry<String, Integer>> sortMap(Map map) {
		List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
				return obj2.getValue() - obj1.getValue();
			}
		});
		return (ArrayList<Entry<String, Integer>>) entries;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		FileInputStream fin = new FileInputStream("/Users/apple/desktop/paper_recommendation/data/selected_reference_paper.txt");
		ObjectInputStream in = new ObjectInputStream(fin);
		FileInputStream fin2 = new FileInputStream("/Users/apple/desktop/paper_recommendation/data/selected_reference_count.txt");
		ObjectInputStream in2 = new ObjectInputStream(fin2);
		File la = new File("/Users/apple/desktop/paper_recommendation/data/keywords_labels.txt");
		File f = new File("/Users/apple/desktop/paper_recommendation/data/author_paper_id.txt");
		File k = new File("/Users/apple/desktop/paper_recommendation/data/keywords_vectors.txt");
		File v = new File("/Users/apple/desktop/paper_recommendation/data/venues_vectors.txt");
		File a = new File("/Users/apple/desktop/paper_recommendation/data/authors_vectors.txt");
		File l = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywords_before_2014.txt");
		File d = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		File n = new File("/Users/apple/desktop/paper_recommendation/venue_id.txt");
		File la2 = new File("/Users/apple/desktop/paper_recommendation/data/venue_labels.txt");
		File la3 = new File("/Users/apple/desktop/paper_recommendation/data/author_labels.txt");
		File ob = new File("/Users/apple/desktop/paper_recommendation/data/objective.txt");
		BufferedWriter bob = new BufferedWriter(new FileWriter(ob));
		HashMap<String, Integer> count = (HashMap<String, Integer>) in2.readObject();
		HashMap<String, ArrayList<String>> map = (HashMap<String, ArrayList<String>>) in.readObject();
		HashMap<Integer, String> sample = new HashMap<>();
		int sum = 0;
		int m = 5;
		int t = 10000000;
		in.close();
		in2.close();
		Random rd = new Random();
		Log lg = new Log();
		double c = rd.nextDouble();
		
		ArrayList<Map.Entry<String, Integer>> entries = sortMap(count);
		for (int i = 0; i < entries.size(); i++)
			for (int j = 0; j < entries.get(i).getValue(); j++) {
				sample.put(sum, entries.get(i).getKey());
				sum += 1;
			}
		System.out.println("start training");
// ------------------------------------------------------------------------正采样
		String[] refs = new String[t], paps = new String[t];
		
		for(int i = 0; i < t; i++)
		{
			refs[i] = sample.get(rd.nextInt(sum));//采样概率与文章引用数目成正比
			//System.out.println(reference);
			ArrayList<String> paperlist = map.get(refs[i]);
			paps[i] = paperlist.get(rd.nextInt(paperlist.size()));
			
			
			//System.out.println(paper);
		}
		
// ------------------------------------------------------------------------get_author

		int auths[] = new int[t];
		BufferedReader br = new BufferedReader(new FileReader(f));
		HashMap<String, ArrayList<Integer>> authormap = new HashMap<>();
		String line;
		ArrayList<Integer> authorlist;
		
		while ((line = br.readLine()) != null) {
			
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
		br.close();
		for(int i = 0; i < t; i++)
			auths[i] = authormap.get(paps[i]).get(rd.nextInt(authormap.get(paps[i]).size()));
			
		
			
		
		System.out.println("author over");

// ------------------------------------------------------------------------get_keywords

		BufferedReader bl = new BufferedReader(new FileReader(l));
		
		String[] sp;
		HashMap<String, ArrayList<Integer>> paper_key = new HashMap<>();
		while ((line = bl.readLine()) != null) {
			sp = line.split(",");
			ArrayList<Integer> keylist = new ArrayList<>();
			for (int i = 1; i < sp.length; i++)
				keylist.add(Integer.parseInt(sp[i]));
			paper_key.put(sp[0], keylist);
			

		}
		bl.close();
		System.out.println("keywords over");
// ---------------------------------------------------------------------------get_venue

		BufferedReader bn = new BufferedReader(new FileReader(n));
		BufferedReader bd = new BufferedReader(new FileReader(d));
		HashMap<String, Integer> map1 = new HashMap<>();
		HashMap<String, Integer> venue = new HashMap<>();
		String conf;
		String index = "";
		int venue_id = 0;
		while ((line = bn.readLine()) != null) {
			String ven = "";
			sp = line.split(",,,");
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

			} else
				venue.put(index, venue_id);

		}
		//System.out.println(venue.size());
		bn.close();
		bd.close();
		System.out.println("venue over");
// ----------------------------------------------------------------------------------init

		BufferedWriter bk = new BufferedWriter(new FileWriter(k));
		BufferedWriter bv = new BufferedWriter(new FileWriter(v));
		BufferedWriter ba = new BufferedWriter(new FileWriter(a));
		BufferedWriter bla = new BufferedWriter(new FileWriter(la));
		BufferedWriter bla2 = new BufferedWriter(new FileWriter(la2));
		BufferedWriter bla3 = new BufferedWriter(new FileWriter(la3));
	
		for(int i = 0; i < 50; i++)
			p.addToEntry(i, 2 * rd.nextDouble() - 1);
		for(int i = 0; i < 50; i++)
			q.addToEntry(i, 2 * rd.nextDouble() - 1);

		System.out.println("init over");
//------------------------------------------------------------------------------迭代		
		ArrayList<Integer> re_key = new ArrayList<>();
		ArrayList<Integer> pa_key = new ArrayList<>();
		for(int x = 0; x < 1; x++)
		{	
			double obj = 0;
			double[]  neg_obj= new double[m];
			for(int w = 0; w < 5000; w++)
			{
			re_key = paper_key.get(refs[w]);
			pa_key = paper_key.get(paps[w]);
			
			
			
			//System.out.println(pa_key);
			Sigmoid s = new Sigmoid();
			double step = 0.25;
			
			 
			double alpha = 0.5;
			ArrayRealVector pa_auth = new ArrayRealVector(50);
			ArrayRealVector pa_ven = new ArrayRealVector(50);
			ArrayRealVector re_ven = new ArrayRealVector(50);
			ArrayRealVector xkp = new ArrayRealVector(50);
			ArrayRealVector xp;
			ArrayRealVector xkr = new ArrayRealVector(50);
			ArrayRealVector xr;
//			
			ArrayRealVector delta_xk, delta_xa ,delta_xk1 ,delta_xk2, delta_xv, delta_xv1, delta_xv2, delta_p, delta_q;
		//	ArrayRealVector neg_delta_xk, neg_delta_xa ,neg_delta_xk1 ,neg_delta_xk2, neg_delta_xv, neg_delta_xv1, neg_delta_xv2, neg_delta_p, neg_delta_q;
			if (authors.containsKey(auths[w]))
				pa_auth = authors.get(auths[w]);
			else {
				for (int i = 0; i < 50; i++)
					pa_auth.addToEntry(i, 2 * rd.nextDouble() - 1);
				authors.put(auths[w], pa_auth);
			}
//
//			
			if (venues.containsKey(venue.get(paps[w])))
				pa_ven = venues.get(venue.get(paps[w]));
			else {
				for (int i = 0; i < 50; i++)
					pa_ven.addToEntry(i, 2 * rd.nextDouble() - 1);
				venues.put(venue.get(paps[w]), pa_ven);
			}
//			
			if (venues.containsKey(venue.get(refs[w])))
			{
				re_ven = venues.get(venue.get(refs[w]));
				//System.out.println(re_ven);
			}
				
			else {
				for (int i = 0; i < 50; i++)
					re_ven.addToEntry(i, 2 * rd.nextDouble() - 1);
				venues.put(venue.get(refs[w]), re_ven);
				
			}
	
				for (int i = 0; i < re_key.size(); i++) {
					if (keywords.containsKey(re_key.get(i)))
						xkr = xkr.add(keywords.get(re_key.get(i)));
					else {
						ArrayRealVector re_keywords = new ArrayRealVector(50);
						for (int j = 0; j < 50; j++)
							re_keywords.addToEntry(j, 2 * rd.nextDouble() - 1);
						keywords.put(re_key.get(i), re_keywords);
						xkr = xkr.add(re_keywords);
					}
				}
				xkr = (ArrayRealVector) xkr.mapDivide(re_key.size());
				for (int i = 0; i < pa_key.size(); i++) {
					if (keywords.containsKey(pa_key.get(i)))
						xkp = xkp.add(keywords.get(pa_key.get(i)));
					else {
						ArrayRealVector pa_keywords = new ArrayRealVector(50);
						for (int j = 0; j < 50; j++)
							pa_keywords.addToEntry(j, 2 * rd.nextDouble() - 1);
						keywords.put(pa_key.get(i), pa_keywords);
						xkp = xkp.add(pa_keywords);
					}

				}
				xkp = (ArrayRealVector) xkp.mapDivide(pa_key.size());
				xp = new ArrayRealVector(xkp.mapMultiply(alpha).add(pa_ven.mapMultiply(1 - alpha)));
				xr = new ArrayRealVector(xkr.mapMultiply(alpha).add(re_ven.mapMultiply(1 - alpha)));
				double grp = xp.dotProduct(xr)+p.dotProduct(xp)+q.dotProduct(xr)+c;
				double fpa = xp.dotProduct(pa_auth)+c;
				double prefix = 1 - s.value(grp + fpa);
			
				ArrayRealVector postfix = (ArrayRealVector) pa_auth.mapMultiply(alpha / pa_key.size())
						.add(xp.mapMultiply(alpha / re_key.size())).add(xr.mapMultiply(alpha / pa_key.size()))
						.add(p.mapMultiply(alpha / pa_key.size())).add(q.mapMultiply(alpha / re_key.size()));
				ArrayRealVector postfix1 = (ArrayRealVector) xp.mapMultiply(alpha / re_key.size()).add(q.mapMultiply(alpha / re_key.size()));
				ArrayRealVector postfix2 = (ArrayRealVector) pa_auth.mapMultiply(alpha / pa_key.size())
						.add(xr.mapMultiply(alpha / pa_key.size())).add(p.mapMultiply(alpha / pa_key.size()));
				ArrayRealVector postfix3 = (ArrayRealVector) pa_auth.mapMultiply(1-alpha)
						.add(xp.mapMultiply(1-alpha)).add(xr.mapMultiply(1-alpha))
						.add(p.mapMultiply(1-alpha)).add(q.mapMultiply(1-alpha));
				ArrayRealVector postfix4 = (ArrayRealVector) xp.mapMultiply(1-alpha).add(q.mapMultiply(1-alpha));
				ArrayRealVector postfix5 = (ArrayRealVector) pa_auth.mapMultiply(1-alpha).add(xr.mapMultiply(1-alpha)).add(p.mapMultiply(1-alpha));
				delta_xk = new ArrayRealVector(postfix.mapMultiply(prefix));
				delta_xk1 = new ArrayRealVector(postfix1.mapMultiply(prefix));
				delta_xk2 = new ArrayRealVector(postfix2.mapMultiply(prefix));
				delta_xv = new ArrayRealVector(postfix3.mapMultiply(prefix));
				delta_xv1 = new ArrayRealVector(postfix4.mapMultiply(prefix));
				delta_xv2 = new ArrayRealVector(postfix5.mapMultiply(prefix));
				delta_xa = new ArrayRealVector(xp.mapMultiply(prefix));
				delta_p = new ArrayRealVector(xp.mapMultiply(prefix));
				delta_q = new ArrayRealVector(xr.mapMultiply(prefix));
				for (int i = 0; i < re_key.size(); i++)
				{
					if(pa_key.contains(re_key.get(i)))
						keywords.put(re_key.get(i), keywords.get(re_key.get(i)).add(delta_xk.mapMultiply(step)));
					else
						keywords.put(re_key.get(i), keywords.get(re_key.get(i)).add(delta_xk1.mapMultiply(step)));
				}
				for (int i = 0; i < pa_key.size(); i++)
				{
					if(re_key.contains(pa_key.get(i)))
						keywords.put(pa_key.get(i), keywords.get(pa_key.get(i)).add(delta_xk.mapMultiply(step)));
					else
						keywords.put(pa_key.get(i), keywords.get(pa_key.get(i)).add(delta_xk2.mapMultiply(step)));
				}
				
					if(re_ven.equals(pa_ven))
						venues.put(venue.get(paps[w]), venues.get(venue.get(paps[w])).add(delta_xv.mapMultiply(step)));
					else
					{
						venues.put(venue.get(refs[w]),  venues.get(venue.get(refs[w])).add(delta_xv1.mapMultiply(step)));
						venues.put(venue.get(paps[w]),  venues.get(venue.get(paps[w])).add(delta_xv2.mapMultiply(step)));
					}
						
				
					

				pa_auth = pa_auth.add(delta_xa.mapMultiply(step));
				authors.put(auths[w], pa_auth);
				p = p.add(delta_p.mapMultiply(step));
				q = q.add(delta_q.mapMultiply(step));
				c = 2*prefix;
				obj += lg.value(s.value(fpa+grp))/1000;

//-------------------------------------------------------------------------------负采样的迭代
				String neg_pap = "";
				int neg_auth = 0;
				for(int j = 0; j < m; j++)
				{
					neg_pap = paps[rd.nextInt(t)];
					neg_auth = auths[rd.nextInt(t)];
					//pa_neg_ven = venue.get(neg_pap);		
					ArrayList<Integer> neg_pa_key = paper_key.get(neg_pap);
					ArrayRealVector neg_pa_auth = new ArrayRealVector(50);
					ArrayRealVector neg_pa_ven = new ArrayRealVector(50);
					ArrayRealVector neg_xkp = new ArrayRealVector(50);
					ArrayRealVector neg_xp;
					ArrayRealVector neg_delta_xk, neg_delta_xa ,neg_delta_xk1 ,neg_delta_xk2, neg_delta_xv, neg_delta_xv1, neg_delta_xv2, neg_delta_p, neg_delta_q;
					if (authors.containsKey(neg_auth))
						neg_pa_auth = authors.get(neg_auth);
					else {
						for (int i = 0; i < 50; i++)
							neg_pa_auth.addToEntry(i, 2 * rd.nextDouble() - 1);
						authors.put(neg_auth, neg_pa_auth);
					}
					if (venues.containsKey(venue.get(neg_pap)))
						neg_pa_ven = venues.get(venue.get(neg_pap));
					else {
						for (int i = 0; i < 50; i++)
							neg_pa_ven.addToEntry(i, 2 * rd.nextDouble() - 1);
						venues.put(venue.get(neg_pap), neg_pa_ven);
					}
					for (int i = 0; i < neg_pa_key.size(); i++) {
						if (keywords.containsKey(neg_pa_key.get(i)))
							neg_xkp = neg_xkp.add(keywords.get(neg_pa_key.get(i)));
						else {
							ArrayRealVector neg_pa_keywords = new ArrayRealVector(50);
							for (int u = 0; u < 50; u++)
								neg_pa_keywords.addToEntry(u, 2 * rd.nextDouble() - 1);
							keywords.put(neg_pa_key.get(i), neg_pa_keywords);
							neg_xkp = neg_xkp.add(neg_pa_keywords);
						}

					}
					neg_xkp = (ArrayRealVector) neg_xkp.mapDivide(neg_pa_key.size());
					neg_xp = new ArrayRealVector(neg_xkp.mapMultiply(alpha).add(neg_pa_ven.mapMultiply(1 - alpha)));
					double neg_grp = neg_xp.dotProduct(xr)+p.dotProduct(neg_xp)+q.dotProduct(xr)+c;
					double neg_fpa = neg_xp.dotProduct(neg_pa_auth)+c;
					double neg_prefix = -s.value(neg_grp + neg_fpa);
					ArrayRealVector neg_postfix = (ArrayRealVector) neg_pa_auth.mapMultiply(alpha / pa_key.size())
							.add(neg_xp.mapMultiply(alpha / re_key.size())).add(xr.mapMultiply(alpha / pa_key.size()))
							.add(p.mapMultiply(alpha / pa_key.size())).add(q.mapMultiply(alpha / re_key.size()));
					ArrayRealVector neg_postfix1 = (ArrayRealVector) neg_xp.mapMultiply(alpha / re_key.size()).add(q.mapMultiply(alpha / re_key.size()));
					ArrayRealVector neg_postfix2 = (ArrayRealVector) neg_pa_auth.mapMultiply(alpha / pa_key.size())
							.add(xr.mapMultiply(alpha / pa_key.size())).add(p.mapMultiply(alpha / pa_key.size()));
					ArrayRealVector neg_postfix3 = (ArrayRealVector) neg_pa_auth.mapMultiply(1-alpha)
							.add(neg_xp.mapMultiply(1-alpha)).add(xr.mapMultiply(1-alpha))
							.add(p.mapMultiply(1-alpha)).add(q.mapMultiply(1-alpha));
					ArrayRealVector neg_postfix4 = (ArrayRealVector) neg_xp.mapMultiply(1-alpha).add(q.mapMultiply(1-alpha));
					ArrayRealVector neg_postfix5 = (ArrayRealVector) neg_pa_auth.mapMultiply(1-alpha).add(xr.mapMultiply(1-alpha)).add(p.mapMultiply(1-alpha));
					neg_delta_xk = new ArrayRealVector(neg_postfix.mapMultiply(neg_prefix));
					neg_delta_xk1 = new ArrayRealVector(neg_postfix1.mapMultiply(neg_prefix));
					neg_delta_xk2 = new ArrayRealVector(neg_postfix2.mapMultiply(neg_prefix));
					neg_delta_xv = new ArrayRealVector(neg_postfix3.mapMultiply(neg_prefix));
					neg_delta_xv1 = new ArrayRealVector(neg_postfix4.mapMultiply(neg_prefix));
					neg_delta_xv2 = new ArrayRealVector(neg_postfix5.mapMultiply(neg_prefix));
					neg_delta_xa = new ArrayRealVector(neg_xp.mapMultiply(neg_prefix));
					neg_delta_p = new ArrayRealVector(neg_xp.mapMultiply(neg_prefix));
					neg_delta_q = new ArrayRealVector(xr.mapMultiply(neg_prefix));
					for (int i = 0; i < re_key.size(); i++)
					{
						if(neg_pa_key.contains(re_key.get(i)))
							keywords.put(re_key.get(i), keywords.get(re_key.get(i)).add(neg_delta_xk.mapMultiply(step)));
						else
							keywords.put(re_key.get(i), keywords.get(re_key.get(i)).add(neg_delta_xk1.mapMultiply(step)));
					}
					for (int i = 0; i < neg_pa_key.size(); i++)
					{
						if(re_key.contains(neg_pa_key.get(i)))
							keywords.put(neg_pa_key.get(i), keywords.get(neg_pa_key.get(i)).add(neg_delta_xk.mapMultiply(step)));
						else
							keywords.put(neg_pa_key.get(i), keywords.get(neg_pa_key.get(i)).add(neg_delta_xk2.mapMultiply(step)));
					}
					
						if(re_ven.equals(neg_pa_ven))
							venues.put(venue.get(neg_pap), venues.get(venue.get(neg_pap)).add(neg_delta_xv.mapMultiply(step)));
						else
						{
							venues.put(venue.get(refs[w]),  venues.get(venue.get(refs[w])).add(neg_delta_xv1.mapMultiply(step)));
							venues.put(venue.get(neg_pap),  venues.get(venue.get(neg_pap)).add(neg_delta_xv2.mapMultiply(step)));
						}
						neg_pa_auth = neg_pa_auth.add(neg_delta_xa.mapMultiply(step));
						authors.put(neg_auth, neg_pa_auth);
						p = p.add(neg_delta_p.mapMultiply(step));
						q = q.add(neg_delta_q.mapMultiply(step));
						c = 2*neg_prefix;
						neg_obj[j] += lg.value(1-s.value(neg_fpa+neg_grp))/1000;
				}
				
				
				
		
		}
			for(int i = 0; i < m; i++)
				obj += neg_obj[i];
			System.out.println(obj);
			
		//bob.write(obj1+"\n");
		
		
		
		}
		for(Entry<Integer,ArrayRealVector>entry:keywords.entrySet())
		{
			for(int j = 0; j < 50; j++)
				bk.write(entry.getValue().getEntry(j) + " ");
			bk.write("\n");
			bla.write(entry.getKey()+"\n");
		}
		for(Entry<Integer,ArrayRealVector>entry:venues.entrySet())
		{
			for(int j = 0; j < 50; j++)
				bv.write(entry.getValue().getEntry(j) + " ");
			bv.write("\n");
			bla2.write(entry.getKey()+"\n");
		}
		
		for(Entry<Integer,ArrayRealVector>entry:authors.entrySet())
		{
			for(int j = 0; j < 50; j++)
				ba.write(entry.getValue().getEntry(j) + " ");
			ba.write("\n");
			bla3.write(entry.getKey()+"\n");
		}
		bk.close();
		ba.close();
		bv.close();
		bla.close();
		bla2.close();
		bla3.close();
		bob.close();
	}
	

}
