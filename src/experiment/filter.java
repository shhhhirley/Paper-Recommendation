package experiment;

import java.io.*;
import java.util.ArrayList;

public class filter {
	public static void main(String[] args)throws IOException
	{
		ArrayList<String> al = new ArrayList<>();
		File f = new File("/Users/apple/desktop/paper_recommendation/data/parsed.txt");
		File n = new File("/Users/apple/desktop/paper_recommendation/data/abnormal.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		BufferedWriter bn = new BufferedWriter(new FileWriter(n));
		String line;
		while((line=bf.readLine())!=null)
		{
			String[] sp = line.split(" ");
			String index = sp[0].split("\\*")[0];
			if(sp.length==1&&!al.contains(index))
			{
				al.add(index);
				bn.write(index+"\n");
				
			}
				
		}
		
		bf.close();
		bn.close();
	}
	
}
