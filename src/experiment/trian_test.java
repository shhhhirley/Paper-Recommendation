package experiment;
import java.io.*;
public class trian_test {

	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		File before = new File("/Users/apple/desktop/paper_recommendation/data/published_before_2014.txt");
		File after = new File("/Users/apple/desktop/paper_recommendation/data/published_after_2014.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		BufferedWriter bw = new BufferedWriter(new FileWriter(before));
		BufferedWriter ba = new BufferedWriter(new FileWriter(after));
		String line;
		Boolean b = false;
		while((line = br.readLine())!=null)
		{
			if(line.length()>1)
			{
				
				if(line.charAt(1)=='t')
				{
					line = line.substring(2);
					int year = Integer.parseInt(line);
					//System.out.println(year);
					if(year >= 2014)
						b = true;
					else
						b = false;
						
				}
				
				else if(line.charAt(1)=='i')
				{
					line = line.substring(6);
					if(b)
					{
						ba.write(line+"\n");
						b = false;
					}
						
					else 
						bw.write(line+"\n");
						
				}
			}
			
		}
		br.close();
		ba.close();
		bw.close();
	}
}
