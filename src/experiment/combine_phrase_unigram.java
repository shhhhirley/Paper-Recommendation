package experiment;
import java.io.*;
public class combine_phrase_unigram {
public static void main(String[] args)throws IOException
{
	File f = new File("/Users/apple/desktop/paper_recommendation/data/paper_phrases.txt");
	BufferedReader bf = new BufferedReader(new FileReader(f));
	File u = new File("/Users/apple/desktop/paper_recommendation/data/paper_unigrams.txt");
	BufferedReader bu = new BufferedReader(new FileReader(u));
	File k = new File("/Users/apple/desktop/paper_recommendation/data/paper_keywords.txt");
	BufferedWriter bw = new BufferedWriter(new FileWriter(k));
	String line1, line2;
	while((line1 = bf.readLine())!=null&& (line2 = bu.readLine())!=null)
	{
		String line = line1+line2.substring(24);
		if(line.split(",").length>1)
			bw.write(line+"\n");

}
	bw.close();
	bf.close();
	bu.close();
}
}
