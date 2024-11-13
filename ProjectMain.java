import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;

import javax.crypto.spec.IvParameterSpec;
import javax.swing.SwingUtilities;
import javax.swing.text.StyledEditorKit.BoldAction;


public class ProjectMain {
	static BSTree<InvertedIndex> bs;
	static String[] documents;
	static String handleFile() {
		File file = new File("C:\\Users\\fmsa2\\Desktop\\دراسة\\عال 212\\Project\\data\\dataset.csv");
		File secondFile = new File("C:\\Users\\fmsa2\\Desktop\\دراسة\\عال 212\\Project\\data\\stop.txt");
		try{
			FileReader reader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(reader);
			FileReader reader2 = new FileReader(secondFile);
			BufferedReader stopWordsBuffer = new BufferedReader(reader2);
			String temp ="";
			buffer.readLine();//to get rid of the header
			int numOfItem=50;
			for(int i=0;i<numOfItem;i++) {
				temp += ((buffer.readLine().toLowerCase()).replaceFirst(""+i, ""));
				temp+="\n";
			}
			String stopWord = stopWordsBuffer.readLine();
			
			while(stopWord!=null) {
				temp = temp.replaceAll("\\b"+stopWord+"\\b", " ");
				stopWord = stopWordsBuffer.readLine();
			}
			buffer.close();
			stopWordsBuffer.close();
			temp = temp.replaceAll("-", " ");
			temp = temp.replaceAll("[^a-zA-Z0-9 \n]", " ");//only keep alphanumeric
			return temp;
		}
		catch(Exception e) {	
			System.out.println("Error in handling file...");
			System.out.println(e.getMessage());
			return "";
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println("Start....");	
		String inputText = handleFile();
		initDataStructure(inputText);
		SimpleGUI s = new SimpleGUI();
		
	}
	
	public static void initDataStructure(String fileText) {
		documents= fileText.split("\n");
		bs = new BSTree<>();
		String[] words;
		//indexList[] index=new indexList[documents.length];//each item is words of one document
		InvertedIndex[] inverted;
		String wordsSpaces = fileText.replace("\n", " ");
		wordsSpaces=wordsSpaces.replaceAll("\\s{2,}", " ");
		words=wordsSpaces.split(" ");
		int size=words.length;
		
		//remove repeated words
		for(int i=0;i<size;i++) {
			for(int j=i+1;j<size;j++) {
				if(words[i].equals(words[j])) {	
					String temp= words[j];
					words[j]=words[size-1];
					words[size-1]=temp;
					size--;	
				}
			}
		}
		int newSize = size;
		inverted=new InvertedIndex[newSize]; //words.length???
		int documentId;
		for(int i=0;i<newSize;i++) { //to loop over the words
			inverted[i]= new InvertedIndex(words[i]);
			for(int j=0;j<documents.length;j++) { //to loop over the documents
				documentId=j;
				if(documents[j].contains(inverted[i].word)){
					String splitDocumentByWord[] = documents[j].split(" ");
					for(int z=0;z<splitDocumentByWord.length;z++) {//to add each repetation
						if(splitDocumentByWord[z].equals(inverted[i].word)) {
							inverted[i].addDocument(documentId);
							bs.insert(words[i], inverted[i]);
							
						}
					}
				}
			}
		}

		
//		for(int i=0;i<avl.counter;i++) {
//			if(avl.findkey("head")) {
//				System.out.println(i+":");
//				avl.retrieve().printList();
//			}
//			else {
//				System.out.println("Not Found");
//			}
//		}
	}
	
	public static int[] andMethod(int n1[],int n2[]) {
//		System.out.println("n1:");
//		printArr(n1);
//		System.out.println("\nn2:");
//		printArr(n2);
		
		int tmp[];
		int count=0;
		if(n1.length>n2.length) {
			tmp= new int[n1.length];	
		}
		else {
			tmp= new int[n2.length];
		}
		tmp[0]=-1; //as a flag for using this array
		
		//you can compare values using new int as the index instead of O(n^2)???
		for(int i=0;i<n1.length;i++) {
			for(int j=0;j<n2.length;j++) {
				if(n1[i]==n2[j]) {
					tmp[count]=n1[i];
					count++;
				}
			}
		}
		
		boolean arrIsInit=false;
		if(tmp[0]!=-1)
			arrIsInit=true;
		if(!arrIsInit) {
			System.out.println("There is no common elements");
			return new int[0];
		}
		
		int tmp2[]=new int[count];
		
		for(int i=0;i<count;i++) {
			tmp2[i]=tmp[i];
		}
		//System.out.println("Last thing in AND with size "+count);
		//printArr(tmp2);
		return tmp2;
	}
	
	public static int[] addArr(int[] n1,int[] n2) {
		int tmp[] = new int[n1.length+n2.length];
		int count=0;
		for(int i=0;i<n1.length;i++) {
			tmp[count++]=n1[i];
		}
		for(int i=0;i<n2.length;i++) {
			tmp[count++]=n2[i];
		}
		return checkRepetation(tmp,count);
	}
	
	public static int[] checkRepetation(int[] arr,int size) {
		for(int i=0;i<size;i++) {
			for(int j=i+1;j<size;j++) {
				if(arr[i]==arr[j]){
					int temp= arr[j];
					arr[j]=arr[size-1];
					arr[size-1]=temp;
					size--;	
				}
			}
		}
		int result[]=new int[size];
		for(int i=0;i<size;i++) {
			result[i]=arr[i];
		}
		return result;
	}
	
	public static int[] orMethod(int n1[],int n2[]) {
//		System.out.println("in OR method");
//		System.out.println("n1:");
//		printArr(n1);
//		System.out.println("n2:");
//		printArr(n2);
		return addArr(n1, n2);
		
		
	}
	
	public static String handleInput(String input) {
		//System.out.println("The Input is"+input);
		int arr[];
		String text = "";
		if(input.contains("AND")||input.contains("OR")) {
			arr = handleBoolInput(input,0,new int[0]);
			//System.out.println("in the AND OR");
			//printArr(arr);
			for(int i=0;i<arr.length;i++) {
				text= text +" " +arr[i];
			}
		}
		else {
			text = handleRankingInput(input);
		}
		return text;
	}
	
	public static String handleRankingInput(String input){
		String words[] = input.toLowerCase().split(" ");
		InvertedIndex n = null;
		int documentsList[]=new int[0];
		int documentCounterList[];
		for(int i=0;i<words.length;i++) {
			if(bs.findkey(words[i])){
				n = bs.retrieve();
				//System.out.println("len: "+n.getDocId().length);
				documentsList = addArr(documentsList,n.getDocId());
				//printArr(documentsList);
			}
			else {
				//System.out.println("The Words is not Saved!");
				return "The Words is not Saved!";
			}
		}
		documentCounterList = new int[documentsList.length];
		int documentNum;
		
		
		for(int i=0;i<documentCounterList.length;i++) {
			documentNum=documentsList[i];
			for(int j=0;j<words.length;j++) {
				if(documents[documentNum].contains(words[j])) {
					documentCounterList[i]+=findDocumentCounter(words[j],documentNum);					
				}
			}
		}
		System.out.println("Final Result:");
		//printArr(documentCounterList);
		//printArrWithCount(documentsList, documentCounterList);
		System.out.println("\n\n\n");
		return sortArr(documentsList,documentCounterList);
	}
	
	public static String sortArr(int documents[],int counters[]) {
		int n = documents.length;
		for (int i = 0; i < n-1; i++){
			for (int j = 0; j < n-1-i; j++){
				if (counters[j] < counters[j+1]){
					//Swap A[j] with A[j+1]
					int tmp1 = counters[j];
					int tmp2 = documents[j];
					counters[j] = counters[j+1]; 
					documents[j] = documents[j+1]; 
					counters[j+1] = tmp1 ;
					documents[j+1] = tmp2 ;
			      } 
				} 
			}
		return printArrWithCount(documents,counters);
	}
	
	public static int findDocumentCounter(String word,int id){
		int counter=0;
		if(bs.findkey(word)) {
			counter = bs.retrieve().getCounter(id);
		}
		return counter;
	}
	
	
	public static int[] handleBoolInput(String input,int index,int[] oldArr){
		String arr[] = input.split(" ");
		int size = arr.length;
		if(index+1>=size) {
			System.out.println("stop Recursion");
		    	return oldArr;
		    }
		boolean empty;
		if(oldArr.length==0)
			empty = true;			
		else 
			empty = false;
		
		index++;
		InvertedIndex n1 = null;
		InvertedIndex n2 = null;
		int[] result;
		System.out.println("words list len:"+size+" and index:"+index);
		
		if (bs.findkey(arr[index-1].toLowerCase())) {
			n1 = bs.retrieve();
			System.out.println("n1= "+n1.word);
		}
		if (bs.findkey(arr[index+1].toLowerCase())) {
			n2 = bs.retrieve();
			System.out.println("n2= "+n2.word);
		}
		if(n1==null || n2==null) {
			System.out.println("The used word doesn't exits");
			return new int[0];
		}
		
		if(arr[index].equals("AND")) {
			System.out.println("in the AND");
			
            if(empty) {
            	result = andMethod(n1.getDocId(), n2.getDocId());
                //printArr(result);
            }
            else {
            	result = andMethod(oldArr,n2.getDocId());
            }
		}
		else if(arr[index].equals("OR")) {
			if(empty) {
				result = orMethod(n1.getDocId(),n2.getDocId());
				//printArr(result);
			}
			else {
				result = orMethod(oldArr, n2.getDocId());
			}
		}
		else {
			System.out.println("error in words order!!");
			return new int [0];
		}
		return handleBoolInput(input, index+1, result);
	}
	
	public static void printArr(int n1[]) {
		System.out.println("in the printArr method:");
		for(int i=0;i<n1.length;i++)
			System.out.println(i+")i: "+n1[i]);
	}
	public static String printArrWithCount(int docs[] , int counts[]) {
		String text="";
		System.out.println("in the print Arr With Counter method:");
		for(int i=0;i<docs.length;i++) {
			System.out.println(i+") Document Id: "+docs[i]+", With Counter: "+counts[i]);
			text +=(i+") Document Id: "+docs[i]+", With Counter: "+counts[i]);
			text+="\n";
		}
		return text;
	}
	

	public static String Printindex(int id) {
		String text = documents[id].replaceAll("\\s{2,}", " ");
		System.out.println(text);
		return text;
		//return documents[id];
	}
	public static String printInvertedIndex(String words) {
		String text = "";
		int arr[];
		if(bs.findkey(words.toLowerCase())) {
			 arr=bs.retrieve().getDocId();
		}
		
		else {
			return "There is no Documents with This word....";
		}
		for(int i=0;i<arr.length;i++) {
			text= text +" " +arr[i];
		}
		return text;
	}
}
