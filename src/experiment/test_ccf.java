package experiment;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.linear.ArrayRealVector;
public class test_ccf {
	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/ccf.txt");
		File fl = new File("/Users/apple/desktop/paper_recommendation/data/ccf_labels.txt");
		File vi = new File("/Users/apple/desktop/paper_recommendation/venue_id.txt");
		File v = new File("/Users/apple/desktop/paper_recommendation/results/venues_vectors.txt");
		File vl = new File("/Users/apple/desktop/paper_recommendation/results/venue_labels.txt");
		File w = new File("/Users/apple/desktop/paper_recommendation/results/ccf_vectors.txt");
		File c = new File("/Users/apple/desktop/paper_recommendation/data/new_ccf.txt");
		File cl = new File("/Users/apple/desktop/paper_recommendation/data/new_ccf_label.txt");
		BufferedReader bf = new BufferedReader(new FileReader(f));
		BufferedReader bfl = new BufferedReader(new FileReader(fl));
		BufferedReader bvi = new BufferedReader(new FileReader(vi));
		BufferedReader bv = new BufferedReader(new FileReader(v));
		BufferedReader bvl = new BufferedReader(new FileReader(vl));
		BufferedWriter bw = new BufferedWriter(new FileWriter(w));
		BufferedWriter bc = new BufferedWriter(new FileWriter(c));
		BufferedWriter bcl = new BufferedWriter(new FileWriter(cl));
		String line,line2;
		HashMap<String, Integer> map = new HashMap<>();
		HashMap<String, Integer> ccf_label = new HashMap<>();
		ArrayList<String> ccf = new ArrayList<>();
		HashMap<Integer, ArrayRealVector> venue_vector = new HashMap<>();
		while((line = bf.readLine())!=null&&(line2 = bfl.readLine())!=null)
		{
			ccf.add(line);
			ccf_label.put(line, Integer.parseInt(line2));
		}
		while((line = bvi.readLine())!=null)
		{
			String sp[] = line.split(",,,");
			
			if(ccf.contains(sp[1]))
				map.put(sp[1], Integer.parseInt(sp[0]));
			
		}
		while((line = bv.readLine())!=null&&(line2 = bvl.readLine())!=null)
		{
			ArrayRealVector ar = new ArrayRealVector(50);
			for(int i = 0; i < 50; i++)
				ar.addToEntry(i, Double.parseDouble(line.split(" ")[i]));
			venue_vector.put(Integer.parseInt(line2), ar);
		}
		for(int i = 0; i < ccf.size(); i++)
		{
			ArrayRealVector ar = new ArrayRealVector(50);
			if(venue_vector.containsKey(map.get(ccf.get(i))))
			{
					bc.write(ccf.get(i)+"\n");
					bcl.write(ccf_label.get(ccf.get(i))+"\n");
				 	ar = venue_vector.get(map.get(ccf.get(i)));
					for(int j = 0; j < 50; j++)
					{
					//	System.out.println(j+" "+ar.getEntry(j));
						bw.write(ar.getEntry(j)+" ");
					}
						
					bw.write("\n");
			}
				
		}
		System.out.println(map.size());
	
		bf.close();
		bv.close();
		bvl.close();
		bvi.close();
		bw.close();
		bc.close();
		bfl.close();
		bcl.close();
	
	}

}
