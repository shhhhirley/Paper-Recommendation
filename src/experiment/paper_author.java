package experiment;
import java.io.*;
import java.util.HashMap;
public class paper_author {

	public static void main(String[] args)throws IOException
	{
		File f = new File("/Users/apple/desktop/paper_recommendation/data/dblp.txt");
		File ap = new File("/Users/apple/desktop/paper_recommendation/author_paper.txt");
	//	File na = new File("/Users/apple/desktop/paper_recommendation/new_author_paper.txt");
		BufferedReader bf  = new BufferedReader(new FileReader(f));
		BufferedWriter ba = new BufferedWriter(new FileWriter(ap));
	//	BufferedWriter br = new BufferedWriter(new FileWriter(na));
		String line, index; 
		String sp[] =  null;
		while((line = bf.readLine())!=null)
		{
			
			if(line.length()>1)
			{
				if(line.charAt(1)=='@')
				{
					//System.out.println(line);
					line = line.substring(2);
					 sp = line.split(", ");
					
				}
				else if(line.charAt(1)=='i')
				{
					//System.out.println(line);
					index = line.substring(6);
					if(sp!=null)
					{
						if(sp.length>1)
							for(int i = 0; i < sp.length; i++)
								ba.write(index + "," + sp[i]+"\n");
						else
							ba.write(index + "," + sp[0]+"\n");
					}
						
					sp = null;
				}
			}
			
				
		}
		
		bf.close();
//		br.close();
		ba.close();
	}
}
