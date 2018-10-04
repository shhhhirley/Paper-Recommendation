package experiment;
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
public class matrix_topk {
	public static void main(String[] args)throws IOException
	{
		File ac = new File("/Users/apple/desktop/paper_recommendation/results/author_citation.txt");
		BufferedReader bac = new BufferedReader(new FileReader(ac));
		File ca = new File("/Users/apple/desktop/paper_recommendation/results/top_10.txt");
		BufferedWriter bca = new BufferedWriter(new FileWriter(ca));
		File re = new File("/Users/apple/desktop/paper_recommendation/results/matrix_results.txt");
		BufferedReader bre = new BufferedReader(new FileReader(re));
		String line;
		double a[][] = new double[115][597];
		int row = 0;
		Random rd = new Random();
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
		//System.out.println(map.size());
		while((line = bre.readLine())!=null)
		{
			String sp[] = line.split(" ");
			for(int i = 0; i < sp.length; i++)
				a[i][row] = Double.parseDouble(sp[i]);
			row++;
			
		}
		//System.out.println(row);
		double precision = 0;
		double recall = 0;
		double ndcg = 0;
		for(int z = 0; z < 5; z++)
		{
			double mean = 0;
			double rec = 0;
			for(int i = 0; i < 115; i++)
			{
				double count = 0;
				ArrayList<Integer> al = map.get(i);
		//		System.out.println(al);
				ArrayList<Double> se = new ArrayList<>();
				HashMap<Double, Integer>hm = new HashMap<>();
				for(int j = 0; j < al.size(); j++)
				{
					se.add(a[i][al.get(j)]);
					hm.put(a[i][al.get(j)],al.get(j));
				}
		//		System.out.println(se.size());
				
				for(int j = se.size(); j < 20; j++)
				{
					 int k  = rd.nextInt(597);
					 if(!se.contains(a[i][k]))
					 {
						 hm.put(a[i][k], k);
						 se.add(a[i][k]);
					 }
					 else
						 j--;
						
				}
			//	System.out.println(se.size());	
				Collections.sort(se);
				double idcg = 0, dcg = 0;
				for(int k = se.size()-1; k > se.size()-4; k--)
				{
					if(map.get(i).contains(hm.get(se.get(k))))
					{
						count++;
						dcg += 1/Math.log(1+se.size()-k);
						idcg += 1/Math.log(2);
					}
						
					
				}
			//	System.out.println(dcg+" "+idcg);
				if(idcg!=0)
					ndcg += dcg/idcg;
				mean += count/5;
				rec += count/al.size();
		//		System.out.println(count+" "+al.size());
				//要挑出一些candidate，现在有579个candidate
			}
			precision += mean/115;
			recall += rec/115;
		}
		
		System.out.println(precision/5+" "+recall/5+" "+ndcg/575);
		bca.close();
		bac.close();
		bre.close();
	}

}
