package experiment;
import java.io.*;
import java.util.HashMap;
public class find_unigram {
	public static String  to_lower(String word)
	{
		if(word.charAt(0)>='A'&&word.charAt(0)<'a')
		{
			char lower = (char) (word.charAt(0)-'A'+'a');
			String replace = lower+word.substring(1);
			return replace;
		}
		else
			return word;
		
	}

	public static void main(String[] args)throws IOException
	{
		 
		File f = new File("/Users/apple/desktop/paper_recommendation/data/wordIDF.txt");
		File p = new File("/Users/apple/desktop/paper_recommendation/data/parsed.txt");
		File n = new File("/Users/apple/desktop/paper_recommendation/data/paper_unigrams.txt");
		File t = new File("/Users/apple/desktop/paper_recommendation/data/unigrams_id.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		BufferedReader bp = new BufferedReader(new FileReader(p));
		BufferedWriter bw = new BufferedWriter(new FileWriter(n));
		BufferedWriter bt = new BufferedWriter(new FileWriter(t));
		HashMap<String, Integer> map = new HashMap<>();
		String word,line;
		int max = 200000;
		String wordlist[] = new String[max];
		int num = 0;
		int count = 239348;
		while((word=br.readLine())!=null&&num<max)
		{
			
			wordlist[num] = word.split(",")[0];//取前20000个idf最大的词
			//bw.write(wordlist[num]+"\n");
			num++;
		}
		
		while((line = bp.readLine())!=null)
		{
			
			String fra[] = line.split("\\*");
			String index = fra[0];
			bw.write(index);
			line = fra[1];
			String[] sp = line.split("\\[");	
			String pre[] = sp[0].split(" ");
			//System.out.println(pre.length);
			for(int i = 0 ; i < pre.length; i++)
			{
				
				if(pre[i].length()>1)
				{
					pre[i] = to_lower(pre[i]);
				//	bw.write(pre[i]+",");
				
				}
				//bw.write("\n");
					
				for(int k = 0; k < max; k++)
				{
					
					if(wordlist[k].equals(pre[i]))
					{
						//bw.write(pre[i]+" "+wordlist[k]+"\n");
					//	System.out.println("pre_matched");
						if(!map.containsKey(pre[i]))
						{
							map.put(pre[i], count);
							bt.write(pre[i]+","+count+"\n");
							bw.write(","+count);
							count++;
						}
						else
							bw.write(","+map.get(pre[i]));
						
					}
						
				}
				
				
			}
			
			for(int i = 1; i <sp.length; i++)
			{
				String temp = null;
				if(sp[i].split("]").length>1)
				{
					temp = sp[i].split("]")[1];
				String unigrams[] = temp.split(" ");
				for(int j = 0; j < unigrams.length; j++)
				{
					for(int k = 0; k < max; k++)
					{
						if(unigrams[j].equals(wordlist[k]))
						{
							//System.out.println("matched");
							if(!map.containsKey(unigrams[j]))
							{
								map.put(unigrams[j],count);
								bt.write(unigrams[j]+","+count+"\n");
								bw.write(","+count);
								count++;
							}
							else
								bw.write(","+map.get(unigrams[j]));
								
						}
					}
						
				}
				}	
			
			}
		System.out.println(index);
		bw.write("\n");
		}
		bt.close();
		br.close();
		bw.close();
		bp.close();
	}
}
