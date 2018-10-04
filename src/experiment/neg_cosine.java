package experiment;

import java.io.*;
import java.util.HashMap;

import org.apache.commons.math3.linear.ArrayRealVector;

public class neg_cosine {
public static void main(String[] args)throws IOException{
	File n = new File("/Users/apple/desktop/paper_recommendation/new_results/neg_vec.txt");
	BufferedReader bn = new BufferedReader(new FileReader(n));
	File nc = new File("/Users/apple/desktop/paper_recommendation/new_results/neg_euc.txt");
	BufferedWriter bnc = new BufferedWriter(new FileWriter(nc));
	File k = new File("/Users/apple/desktop/paper_recommendation/new_results/key_vec.txt");
	BufferedReader bk = new BufferedReader(new FileReader(k));
	String line;
	HashMap<Integer, ArrayRealVector> map = new HashMap<>();
	int count = 0;
	
	
	
	
	while((line = bk.readLine())!=null)
	{		
		if(!line.equals("\\"))
		{	
			ArrayRealVector ar = new ArrayRealVector(50);
			String sp[] = line.split("\\{");
			System.out.println(sp.length);
			String spl[] = sp[1].split("}");
			String spli[] = spl[0].split(";");
			for(int i = 0; i < 50; i++)
			{
				ar.addToEntry(i, Double.parseDouble(spli[i]));
			}
			map.put(count, ar);
			
		}
		else 
			count++;
	}
	int num = 0;
	while((line = bn.readLine())!=null)
	{
		if(!line.equals("\\"))
		{
			ArrayRealVector ar = new ArrayRealVector(50);
			String sp[] = line.split("\\{");
			String spl[] = sp[1].split("}");
			String spli[] = spl[0].split(";");
			
			for(int i = 0; i < 50; i++)
				ar.addToEntry(i, Double.parseDouble(spli[i]));
			bnc.write(map.get(num).getDistance(ar)+" ");
			System.out.println(map.get(num));
			System.out.println(ar);
		}
		else
		{
			num++;
			bnc.write("\n");
		}
	}
	bk.close();
	bnc.close();
	bn.close();
}
}
