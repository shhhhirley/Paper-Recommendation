package experiment;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class auth_paper {

	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/author_paper.txt");
		File id = new File("/Users/apple/desktop/paper_recommendation/data/author_id.txt");
		File v = new File("/Users/apple/desktop/paper_recommendation/author_paper_id.txt");
//		File nv = new File("/Users/apple/desktop/paper_recommendation/wrong.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		BufferedReader bi = new BufferedReader(new FileReader(id));
		BufferedWriter bw = new BufferedWriter(new FileWriter(v));
	//	BufferedWriter bn = new BufferedWriter(new FileWriter(nv));
		HashMap<String, String>map = new HashMap<String, String>();
	//	HashMap<String, Integer>mapv = new HashMap<String, Integer>();
		
	
	String line;
	
	
	while((line = bi.readLine()) != null)
		map.put(line.split(",")[0], line.split(",")[1]);
	while((line = br.readLine()) !=null)
	{
		
			bw.write(line.split(",")[0]+","+map.get(line.split(",")[1])+"\n");
		
	}
		
			
				
		
	
	

	bi.close();
	br.close();
	bw.close();
	
	
}
	
}