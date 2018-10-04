package experiment;

import java.io.*;

import org.apache.commons.math3.linear.ArrayRealVector;

public class accuracy {
	public static void main(String[] args)throws IOException
	{
		File w = new File("/Users/apple/desktop/paper_recommendation/new_results/temp_vec.txt");
		BufferedReader bw = new BufferedReader(new FileReader(w));
		File t = new File("/Users/apple/desktop/paper_recommendation/new_results/auth_vec.txt");
		BufferedReader bt = new BufferedReader(new FileReader(t));
		File k = new File("/Users/apple/desktop/paper_recommendation/new_results/key_vec.txt");
		BufferedReader bk = new BufferedReader(new FileReader(k));
		File r = new File("/Users/apple/desktop/paper_recommendation/new_results/accuracy_euc.txt");
		BufferedWriter br = new BufferedWriter(new FileWriter(r));

		String line;
		String li;
		String key;
		
		while((line = bw.readLine())!=null&&(key = bk.readLine())!=null)
		{
			ArrayRealVector temp = new ArrayRealVector(50);
			ArrayRealVector auth = new ArrayRealVector(50);
			ArrayRealVector keyword = new ArrayRealVector(50);
			if(!line.equals("\\"))
			{
				li = bt.readLine();
				String sp[] = line.split("\\{");
				String spl[] = sp[1].split("}");
				String spli[] = spl[0].split(";");
				
				String q[] =  key.split("\\{");
				String ql[] = q[1].split("}");
				String qli[] = ql[0].split(";");
				
				for(int i = 0; i < 50; i++)
					keyword.addToEntry(i, Double.parseDouble(qli[i]));
				for(int i = 0; i < 50; i++)
					temp.addToEntry(i, Double.parseDouble(spli[i]));
				if(li.length()!=0)
				{
					String p[] =  li.split("\\{");
					String pl[] = p[1].split("}");
					String pli[] = pl[0].split(";");
					for(int i = 0; i < 50; i++)
						auth.addToEntry(i, Double.parseDouble(pli[i]));
					br.write(keyword.getDistance(temp.mapMultiply(0.5).add(auth.mapMultiply(0.5)))+" ");
				}
				else
					br.write(keyword.getDistance(temp)+" ");
					
			}
			else 
				br.write("\n");
		
		}
		bt.close();
		bw.close();
		br.close();
		bk.close();
		
		br.close();
		
	}

}
