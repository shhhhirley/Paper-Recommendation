package experiment;
import java.io.*;
public class parse_problem {
	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/filtered_paper_keywordstxt");
		File a = new File("/Users/apple/desktop/paper_recommendation/data/abnorm.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		BufferedWriter bw = new BufferedWriter(new FileWriter(a));
		String line;
		while((line = br.readLine())!=null)
		{
			String sp[] = line.split(",");	
			if(sp[0].split("]").length>1)
			{
				bw.write(line+"\n");
			}
			
		}
		br.close();
		bw.close();
	}

}
