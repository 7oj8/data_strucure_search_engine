import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;

import javax.crypto.spec.IvParameterSpec;
import javax.swing.text.StyledEditorKit.BoldAction;


public class ProjectMain {
	static String handleFile() {
		File file = new File("C:\\Users\\fmsa2\\Desktop\\دراسة\\عال 212\\Project\\data\\dataset.csv");
		File secondFile = new File("C:\\Users\\fmsa2\\Desktop\\دراسة\\عال 212\\Project\\data\\stop.txt");
		try{
			
			FileReader reader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(reader);
			FileReader reader2 = new FileReader(secondFile);
			BufferedReader stopWordsBuffer = new BufferedReader(reader2);
			//String stopWords = stopWordsBuffer.readLine();
			//int i=0;
			String temp ="";
			buffer.readLine();//to get rid of the header
			int numOfItem=50;
			for(int i=0;i<numOfItem;i++) {
				temp += ((buffer.readLine().toLowerCase()).replaceFirst(""+i, ""));
				temp+="\n";
				//System.out.println("The I: "+i);
				//String splitted[] = temp.split(",");
				//splitted[1] = splitted[1].replaceAll("[^a-zA-Z0-9 ]", "");
				//spiltted[1] = splitted[1].replaceAll(temp, temp)
				//System.out.println(i+++" The File Contents: "+splitted[1]);
				//temp = (buffer.readLine().toLowerCase());
			}
			String stopWord = stopWordsBuffer.readLine();
			
			while(stopWord!=null) {
				//System.out.println(xx+++") The Stop Word is:"+stopWord);
				temp = temp.replaceAll("\\b"+stopWord+"\\b", " ");
				//System.out.println("The Temp After Stop Word:"+temp+"\n\n");
				stopWord = stopWordsBuffer.readLine();
			}
			buffer.close();
			stopWordsBuffer.close();
			//temp = temp+"Fahad Saleh Fahad Saleh";
			temp = temp.replaceAll("-", " ");
			temp = temp.replaceAll("[^a-zA-Z0-9 \n]", "");//only keep alphanumeric
			System.out.println(temp);
			
			//System.out.println(temp+"");
			//System.exit(0);
			//temp = temp.replaceAll("\\s{2,}", " ");//remove any spaces occurence after each other
			
			
			
			return temp;
			
			
			
			
			//splitted[1] = splitted[1].replaceAll("[^a-zA-Z0-9 ]", "");
			//System.out.println(temp);
		}
		catch(Exception e) {	
			System.out.println("ERROR");
			System.out.println(e.getMessage());
			System.out.println("ERROR");
			return "";
		}
		
	}
	public static void main(String[] args) {
		System.out.println("Start....");	
		String fileText = handleFile();
		//System.out.println(fileText);
		String[] sentences = fileText.split("\n");
		String[] words=null;
		indexList[] index=new indexList[sentences.length];//each item is words of one document
		InvertedIndex[] inverted;
		BSTree<InvertedIndex> bs = new BSTree<>();
		
		
		
//		for(int i=0;i<sentences.length;i++) {
//			System.out.println(i+")Entered");
//			sentences[i]=sentences[i].replaceAll("\\s{2,}", " ");//remove spaces after each other
//			sentences[i]=sentences[i].replaceAll("^ ","");//remove spaces from the start
//			words=sentences[i].split(" ");
//			index[i]=new indexList();
//			for(int j=0;j<words.length;j++) {
//				index[i].addWord(words[j]);
//			}
//			index[i].printIndexNode();
//			System.out.println(i+")Leaving");
//			
//			
//			//System.out.println(""+splitted[i]+"");
//		}
		
		//String wordsSpaces = fileText.replace("\n", " ");
		String wordsSpaces = fileText.replace("\n", " ");
		wordsSpaces=wordsSpaces.replaceAll("\\s{2,}", " ");
		
		
//		String ss[]= wordsSpaces.split(" ");
//		int pos=0;
//		Boolean found = false;				          //d
//		for(int i=0;i<wordsSpaces.length();i++) {//  (A B C)=3
//			
//			for(int j=i+1;j<wordsSpaces.length()-1;j++) {//  (B C)=3
//				if(ss[i].equals(ss[j])) {
//					found=true;
//				}
//				else {
//					
//				}
//			}
//		}
		
		
		words=wordsSpaces.split(" ");
		
		//            L
		//i       j
		//A B C D A E F  = 7
	    //0 1 2 3 4 5 6
		//
		int L=words.length;		
		int counter=0;
		for(int i=0;i<L;i++) {
			//System.out.println(words[i]);
			for(int j=i+1;j<L;j++) {
				if(words[i].equals(words[j])) {
//					System.out.println("\n\n");
//					System.out.println(i+")i:"+words[i]);
//					System.out.println(j+")j:"+words[j]);
//					System.out.println(L-1+")L:"+words[L-1]);
					//System.out.println("");
					
					String temp= words[j];
					words[j]=words[L-1];
					words[L-1]=temp;
					//System.out.println("After");
					//System.out.println("");
//					System.out.println(i+")i:"+words[i]);
//					System.out.println(j+")j:"+words[j]);
//					System.out.println(L-1+")L:"+words[L-1]);
//					System.out.println("\n\n");
					L--;
					counter++;
					
				}
				
				//words.length--;
			}
		}
		int newSize = words.length-counter;
		//System.out.println(counter+"");
		for(int i=0;i<newSize;i++) {
			//System.out.println(i+")"+words[i]);
		}
//		String temp[words.length];
//		Boolean found =false; 
//		// A , B , A , M
//		// i                j
//		for(int i=0;i<words.length;i++) {
//			for(int j=i+1;j<temp.length;j++) {
//				if(words[i].equals(words[j])) {
//					found=true;
//				}
//			}
//			if(!found)
//				temp[i]=words[i];
//		}

		
		
		
		
		inverted=new InvertedIndex[words.length];
		
		//System.out.println(words.length);
		//System.out.println(""+wordsSpaces);
		int documentId;
		for(int i=0;i<newSize;i++) {
			//documentId=i;
			//sentences[i]= sentences[i].replaceAll("\\s{2,}", " ");
			//sentences[i]= sentences[i].replaceAll("^ ", "");
			//words=sentences[i].split(" ");
			
			//{A,B,C,A}
			//{0,1,2,3}
			
			inverted[i]= new InvertedIndex(words[i]);
			//System.out.println("The Words:"+words[i]);
			//System.out.println("inverted"+inverted[i]);
			//System.out.println(words[i]);
			
			//System.out.println(sentences[1]);
			
			for(int j=0;j<sentences.length;j++) { //sentences = document
				documentId=j;
				//System.out.println(""+documentId);
				//System.out.println(j+")"+inverted[i].word);
				//System.out.println("Inverted: "+inverted[i].word);
				//System.out.println("Sentence: "+sentences[j]);
				
				if(sentences[j].contains(inverted[i].word)){
					//System.out.println(xx+++")bhbbh");
					//System.out.println(words[i]+" in "+documentId);
					String splitDocumentByWord[] = sentences[j].split(inverted[i].word);
					int NumOfRepeat = splitDocumentByWord.length-1;
					for(int z=0;z<NumOfRepeat;z++) {
						inverted[i].addDocument(documentId);
						bs.insert(words[i], inverted[i]);
					}
					//System.out.println(sentences[j]);
				}
			}
		}
		
		//System.out.println("The Words Of Number 39: \n\n");
		//index[39].printIndexNode();
		
		
		
		for(int i=0;i<newSize;i++) {
			//inverted[i].printList();
			if(bs.findkey(words[i])) {
				//System.out.println(i+":");
				bs.retrieve().printList();
			}
		}
			
				//System.out.println("\n\n\n"+);
		System.out.println("\n\nFinish!");
	}
}
