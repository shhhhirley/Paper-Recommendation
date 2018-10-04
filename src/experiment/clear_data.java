package experiment;
import java.io.*;
public class clear_data {

	public static void main(String[] args) throws IOException{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/paper_unigrams.txt");
		File nf = new File("/Users/apple/desktop/paper_recommendation/data/new_paper_unigrams.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		BufferedWriter bw = new BufferedWriter(new FileWriter(nf));
		String line;
		
		while((line = br.readLine())!=null)
		{
			String sp[] = line.split(",");
			bw.write(sp[0]);
			for(int i = 1; i < sp.length; i++)
				bw.write(","+(Integer.parseInt(line.split(",")[i])-17));
			bw.write("\n");
			
		}
		bw.close();
		br.close();
	}
}
