package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
public class mf_accuracy {

	public static void main(String[] args)throws IOException, ClassNotFoundException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/results/R.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		File al = new File("/Users/apple/desktop/paper_recommendation/results/author_label.txt");
		BufferedReader bal = new BufferedReader(new FileReader(al));
		File pl = new File("/Users/apple/desktop/paper_recommendation/results/matrix_label.txt");
		BufferedReader bpl = new BufferedReader(new FileReader(pl));
		FileInputStream fin2 = new FileInputStream("/Users/apple/desktop/paper_recommendation/new_data/selected_reference_paper_after_2014.txt");
		ObjectInputStream in2 = new ObjectInputStream(fin2);	
		HashMap<String, ArrayList<String>> reference2 = (HashMap<String, ArrayList<String>>) in2.readObject();
		File o = new File("/Users/apple/desktop/paper_recommendation/results/matrix_results.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(o));
		String line;
		HashMap<String,Integer>map = new HashMap<>();
		int num = 0,count = 0;
		int linenum[] = new int[115];
		double matrix[][] = new double[597][115];
		System.out.println(reference2.keySet().size());
		while((line = bpl.readLine())!=null)
		{
			
			if(reference2.containsKey(line))
				linenum[num++] = count;
			count++;
			
		}
		int read = 0;
		while((line = br.readLine())!=null)
		{
			
			String[] sp = line.split(" ");
			for(int j = 0; j < linenum.length; j++)
				matrix[read][j] = Double.parseDouble(sp[linenum[j]]);
			read++;
			
		}
		for(int i = 0; i < 597; i++)
		{
			for(int j = 0; j < 115; j++)
			{
				bw.write(matrix[i][j]+" ");
				
			}
			bw.write("\n");
				
				
		}
		bw.close();
		br.close();
		bpl.close();
		bal.close();
		in2.close();
		
	}
}
