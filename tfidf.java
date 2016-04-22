//Finding out the tf-idf score of a term in a corpus.
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class tfidf
{

	public static void main(String[] args) throws FileNotFoundException,IOException
	{

			HashMap<String, Integer> filenames = new HashMap<>();// Storing the names of the file in a hashmap
			HashMap<String, ArrayList<Integer> > dictionary = new HashMap<>();// Input the file containing names of file to be index.
			ArrayList<String> file =new ArrayList<String>();
			ArrayList<String> stopwords =new ArrayList<String>();
			File folder = new File("/home/sysadmin/Documents/Lab/ass1/hindi_original");
			File[] listOfFiles = folder.listFiles();
			FileWriter fw = new FileWriter(new File("doc_name_hindi.txt"));

			for (int i = 0; i < listOfFiles.length; i++)
			{
				if (listOfFiles[i].isFile())
				{
										fw.flush();
										//System.out.println(listOfFiles[i].getName());
										fw.write("hindi_original/"+listOfFiles[i].getName());
										fw.write("\n");
				}

			}

			FileReader fr = new FileReader(new File("doc_name_hindi.txt"));
			Scanner sc = new Scanner(fr);
			Integer counter=0;
			// To add all the stopwords into the database.
			FileReader f1= new FileReader(new File("hindistop"));
			Scanner scan= new Scanner(f1);
			while(scan.hasNext())
			{
				String abc= scan.next();
				stopwords.add(abc);
			}

			while (sc.hasNext())
			{
				String s = sc.next();// Input the name of the each file.
				file.add(s);
				filenames.put(s,++counter);//  Setting the doc_no for each of the files.
				FileReader fr1= new FileReader(new File(s));// Opening file one by one.
				Scanner sc1=new Scanner(fr1); // Scanning new file.

				while (sc1.hasNext())
				{
					String s1=sc1.next();
					int flag1=1;

					// Normalizer
					for(int i=0;i<s1.length();i++)
					{
							if(s1.contains("0") || s1.contains("1")|| s1.contains("2") || s1.contains("3")|| s1.contains("4") || s1.contains("5")|| s1.contains("6") || s1.contains("7") ||s1.contains("8") || s1.contains("9"))
							{
									flag1=0;
							}
							if(s1.startsWith(".") || s1.startsWith(",") || s1.startsWith("<") || s1.startsWith("-")|| s1.startsWith("-")|| s1.startsWith("-") || s1.startsWith("'")|| s1.startsWith("|") || s1.startsWith("||"))
							{
								s1=s1.substring(1,s1.length());
							}
							if(s1.endsWith(".") || s1.endsWith(",") || s1.endsWith(">") || s1.endsWith("-")|| s1.endsWith("}")|| s1.endsWith(")") || s1.endsWith("'") || s1.endsWith("|") || s1.endsWith("||") || s1.endsWith("="))
							{
								s1=s1.substring(s1.length()-1);
							}

					}


					//stopwords removal
					if(!stopwords.contains(s1) && flag1==1)			// Checking whether a word is a stop word or not.
					{
						// Stemming the words.
						HindiStemmerLight stemmer = new HindiStemmerLight();
						//s1.toLowerCase();
						//stemmer.add( s1.toCharArray(), s1.length());
						stemmer.stem(s1);ArrayList<Integer> doc_no= new ArrayList<Integer>();

						if(dictionary.containsKey(s1))
						{
							ArrayList<Integer> flag = dictionary.get(s1);	//dummy arraylist.
							if(!flag.contains(counter))
							{
								flag.add(counter);
								dictionary.put(s1,flag);
							}
						}
						else
						{
							ArrayList<Integer> flag = new ArrayList<Integer> ();	//dummy arraylist.
							flag.add(counter);
							dictionary.put(s1,flag);
						}

				}

			}
		}
			System.out.println(dictionary);
			System.out.println(filenames.size());
			System.out.print("Enter the query term \n");
			Scanner hututu=new Scanner(System.in);
			String term = hututu.next();

			//Finding the term frequency of the document
			System.out.println(dictionary.get(term));// this shows the posting list of the term.
			ArrayList<Integer> posting_list= new ArrayList<Integer>(dictionary.get(term));
			ArrayList<Integer> frequency = new ArrayList<Integer>();
			ArrayList<Integer> word_count = new ArrayList<Integer>();

			for(int i=0;i<posting_list.size();i++)
			{

					FileReader fr2= new FileReader(new File(file.get(posting_list.get(i)-1)));// Opening file one by one.
					Scanner sc2=new Scanner(fr2); // Scanning new file.
					int count=0;
					int count1=0;
					while (sc2.hasNext())
					{

							String s2=sc2.next();
							count++;
							if(s2.equals(term))
							count1++;
					}
					frequency.add(count1);
					word_count.add(count);
			}

			Integer total_no_of_documents=filenames.size();
			Integer where_it_occur=frequency.size();
			double inverse_document_frequency=Math.log(total_no_of_documents/(double)where_it_occur);
			double tf;
			ArrayList<Double> tf_idf =new ArrayList<Double>();
			for(int i=0;i<frequency.size();i++)
			{
					tf = (double)frequency.get(i)/word_count.get(i);
					System.out.println("Term Frequency is " + tf);
					//System.out.println(tf);
					//tf_idf.add(tf/inverse_document_frequency);
					tf_idf.add(tf * inverse_document_frequency);
					System.out.println(frequency.get(i)+"/"+ word_count.get(i)+"/"+tf_idf.get(i)+"\n");
					//System.out.println(dictionary);
				//	System.out.println(frequency.get(i)/ word_count.get(i)/inverse_document_frequency+"\n");
			}
			//System.out.println(tf_idf);
			//System.out.println(inverse_document_frequency);
			//System.out.println(word_count);
	}
}
