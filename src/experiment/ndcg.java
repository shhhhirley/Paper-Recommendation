package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
public class ndcg {
	public static void main(String[] args)throws IOException
	{
		File ran = new File("/Users/apple/desktop/paper_recommendation/results/euc_neg_cbf.txt");
		BufferedReader bran = new BufferedReader(new FileReader(ran));
		File se = new File("/Users/apple/desktop/paper_recommendation/results/euc_cbf.txt");
		BufferedReader bse = new BufferedReader(new FileReader(se));
		String line1, line2; 
		int k = 10;
		double ndcg = 0;
		
		while((line1 = bran.readLine())!=null && (line2 = bse.readLine())!=null)
		{ 
			
			ArrayList<Double> al = new ArrayList<>();
			HashMap<Double, Integer> map = new HashMap<>();
			for(int i = 0; i < line2.split(" ").length; i++)
			{
				al.add(Double.parseDouble(line2.split(" ")[i]));
				map.put(Double.parseDouble(line2.split(" ")[i]), 0);
			}
				
			for(int i = 0; i < line1.split(" ").length; i++)
			{
				al.add(Double.parseDouble(line1.split(" ")[i]));
				map.put(Double.parseDouble(line1.split(" ")[i]), 1);
			}
			if(map.size() >= k)
			{
				Collections.sort(al);
				double idcg = 0;
				double dcg = 0;
				for(int i = 0; i < k; i++)
				{
					if(map.get(al.get(i)) == 0)
					{
						
						dcg += 1/Math.log(1+map.size()-i);
						idcg += 1/Math.log(2);
					}
						
				}
			//	System.out.println(dcg+" "+idcg);
				if(idcg!=0)
					ndcg += dcg/idcg;
			
			}
		
			
			
			
		}
		System.out.println(ndcg/115);
		bse.close();
		bran.close();
	}

}
