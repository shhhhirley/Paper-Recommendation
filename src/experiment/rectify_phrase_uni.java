package experiment;
import java.io.*;
public class rectify_phrase_uni {

	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/paper_phrases.txt");
		File u = new File("/Users/apple/desktop/paper_recommendation/data/paper_unigrams.txt");
		File a = new File("/Users/apple/desktop/paper_recommendation/data/rectified_paper_phrases.txt");
		File r = new File("/Users/apple/desktop/paper_recommendation/data/rectified_paper_unigrams.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		BufferedReader bu = new BufferedReader(new FileReader(u));
		BufferedWriter ba = new BufferedWriter(new FileWriter(a));
		BufferedWriter br = new BufferedWriter(new FileWriter(r));
		String line1, line2;
		while((line1 = bf.readLine())!=null)
		{
			line1 = line1.replace("[", "");
			line1 = line1.replace("]", "");
			ba.write(line1+"\n");
		}
		while((line2 = bu.readLine())!=null)
		{
			line2 = line2.replace("[", "");
			line2 = line2.replace("]", "");
			br.write(line2+"\n");
		}
			
		bf.close();
		bu.close();
		br.close();
		ba.close();
	}
}
