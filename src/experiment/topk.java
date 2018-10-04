package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
public class topk {
	public static void main(String[] args)throws IOException
	{
		//top 10
		File ran = new File("/Users/apple/desktop/paper_recommendation/new_results/neg_euc.txt");
		BufferedReader bran = new BufferedReader(new FileReader(ran));
		File se = new File("/Users/apple/desktop/paper_recommendation/new_results/without_auth_euc.txt");
		BufferedReader bse = new BufferedReader(new FileReader(se));
		String line1, line2; 
		double precision = 0, prec = 0;
		double recall = 0;
		int c = 0;
		int k = 3;
		while((line1 = bran.readLine())!=null && (line2 = bse.readLine())!=null)
		{ 
			
			if(line1.length()!=0&&line2.length()!=0)
			{
				c++;
				int count = 0;
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
					for(int i = 0, j = 1 ; i < k; i++, j++)
					{
						if(map.get(al.get(i)) == 0)
							count++;
						prec = (double)count/j;
						precision += prec/k;
					}
			//	System.out.println(count);
					
					recall += (double)count/line2.split(" ").length;	
				}
			
			}
			
			
			
			
		}
		System.out.println(precision/c+" "+recall/c);
		bse.close();
		bran.close();
	}

}
