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
	static InvertedIndex[] inverted;
	static indexList[] index;
	
	public static void main(String[] args) {
		System.out.println("Start....");	
		String inputText = handleFile();
		initDataStructure(inputText);
		printArr(getIndexDocs("market"));
		printArr(getIndexDocs("sports"));
		printArr(getIndexDocs("warming"));
		int a[]=handleBool("market OR sports AND warming","index");
		printArr(a);
		System.out.println("------------------------------------");
		printArr(handleBool("market OR sports AND warming","inverse"));//41 Finished for one 
		System.out.println("2");
		int aa[]=handleBool("market OR sports AND warming","bst"); //41 Finished for one 
		printArr(aa);
		System.out.println("break");
		printArr(notMethod(aa,documents.length)); 
		System.out.println("3");
		SimpleGUI s = new SimpleGUI();
		
	}
	
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
	
	
	public static void initDataStructure(String fileText) {
		documents= fileText.split("\n");
		bs = new BSTree<>();
		String[] words;
		System.out.println("documents number: "+documents.length);
		index=new indexList[documents.length];//each item is words of one document
		
		String wordsSpaces = fileText.replace("\n", " ");
		wordsSpaces=wordsSpaces.replaceAll("\\s{2,}", " ");
		wordsSpaces= wordsSpaces.replaceAll("^ ", "");//remove space at the start
		//System.out.println("words Spaced:\n"+wordsSpaces);
		words=wordsSpaces.split(" ");
		int size=words.length;
		
		//init Index
		for(int i=0;i<documents.length;i++) {
			index[i]=new indexList();
			for(int j=0;j<words.length;j++) {
				if(documents[i].contains(words[j])) {
					String documentWords[]=documents[i].split(" ");
					for(int z=0;z<documentWords.length;z++) {
						if(documentWords[z].equals(words[j])) {
							index[i].addWord(words[j]);
						}
					}
				}
			}
		}
		
//		for(int i=0;i<index.length;i++) {
//			System.out.println(i+":");
//			index[i].printIndexNode();
//		}
		
		//Move Repeated words to the end/remove them
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
		//Inverted and BST
		for(int i=0;i<newSize;i++) { //to loop over the words
			inverted[i]= new InvertedIndex(words[i]);
			for(int j=0;j<documents.length;j++) { //to loop over the documents
				documentId=j;
				if(documents[documentId].contains(inverted[i].word)){
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
	public static int[] notMethod(int documents[],int spaceSize) {
		int newSize=spaceSize-documents.length,pos=0;
		int result[]=new int[newSize];
		//initiate with all documents
		boolean found;
		for(int i=0;i<spaceSize;i++) {
			found=false;
			for(int j=0;j<documents.length;j++) {
				if(documents[j]==i) {
					found=true;
					break;
				}
			}
			if(!found) {
				result[pos++]=i;
			}
		}
		return result;
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
	
//	public static String handleInput(String input) {
//		//System.out.println("The Input is"+input);
//		int arr[];
//		String text = "";
//		if(input.contains("AND")||input.contains("OR")) {
//			arr = handleBoolBST(input);
//			//System.out.println("in the AND OR");
//			//printArr(arr);
//			for(int i=0;i<arr.length;i++) {
//				text= text +" " +arr[i];
//			}
//		}
//		else {
//			text = handleRankingInput(input);
//		}
//		return text;
//	}
	
	
	public static String bstHandler(String input) {
		int result[]=new int[0];
		String text="";
		if(input.contains("AND")||input.contains("OR")||input.contains("NOT")) {
			result = handleBool(input, "bst");
			//System.out.println("in the AND OR");
			//printArr(arr);
			for(int i=0;i<result.length;i++) {
				text= text +" " +result[i];
			}
		}
		else {
			text = handleRankingInput(input,"bst");
		}
		return text;
	}
	
	public static String indexHandler(String input) {
		int result[]=new int[0];
		String text="";
		if(input.contains("AND")||input.contains("OR")||input.contains("NOT")) {
			result = handleBool(input, "index");
			//System.out.println("in the AND OR");
			//printArr(arr);
			for(int i=0;i<result.length;i++) {
				text= text +" " +result[i];
			}
		}
		else {
			text =handleRankingInput(input,"index");
		}
		return text;
	}
	
	public static String inverseHandler(String input) {
		int result[]=new int[0];
		String text="";
		if(input.contains("AND")||input.contains("OR")||input.contains("NOT")) {
			result = handleBool(input, "inverse");
			//System.out.println("in the AND OR");
			//printArr(arr);
			for(int i=0;i<result.length;i++) {
				text= text +" " +result[i];
			}
		}
		else {
			text =handleRankingInput(input,"inverse");
		}
		return text;
	}
	public static String handleRankingInput(String input,String caller){
		String words[] = input.toLowerCase().split(" ");
		int n []= new int[0];
		int documentsList[]=new int[0];
		int documentCounterList[];
		for(int i=0;i<words.length;i++) {
			if(caller.equalsIgnoreCase("bst")){
				n = getBSTDocs(words[i]);
			}
			else if(caller.equalsIgnoreCase("index")) {
				n= getIndexDocs(words[i]);
			}
			else if(caller.equalsIgnoreCase("inverse")) {
				n=getInverseDocs(words[i]);
			}
			else {
				//System.out.println("The Words is not Saved!");
				return "The Words is not Saved!";
			}
			documentsList = addArr(documentsList,n);
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
	
	
//	public static int[] handleBoolInput(String input,int pos,int[] oldArr){
//		String arr[] = input.split(" ");
//		int size = arr.length;
//		if(pos+1>=size) {
//			System.out.println("stop Recursion");
//		    	return oldArr;
//		    }
//		boolean empty;
//		if(oldArr.length==0)
//			empty = true;			
//		else 
//			empty = false;
//		
//		pos++;
//		InvertedIndex n1 = null;
//		InvertedIndex n2 = null;
//		int[] result;
//		System.out.println("words list len:"+size+" and index:"+pos);
//		
//
//		
//
//		
//		
//		
//		//System.out.println("our side");
////		System.out.println("n1: "+n1.getWord());
////		System.out.println("n2: "+n2.getWord());
//		//System.exit(1);
//		
//		if(arr[pos].equals("AND")) {
//			System.out.println("in the AND");
//			
//            if(empty) {
//            	result = andMethod(n1.getDocId(), n2.getDocId());
//                //printArr(result);
//            }
//            else {
//            	result = andMethod(oldArr,n2.getDocId());
//            }
//		}
//		else if(arr[pos].equals("OR")) {
//			if(empty) {
//				result = orMethod(n1.getDocId(),n2.getDocId());
//				//printArr(result);
//			}
//			else {
//				result = orMethod(oldArr, n2.getDocId());
//			}
//		}
//		else {
//			System.out.println("error in words order!!");
//			return new int [0];
//		}
//		return handleBoolInput(input, pos+1, result);
//	}
	
	
	
	
	
	//		 head OR sports AND market AND warm OR weather 
	//Stack1: head ->  res -> weather
	//Stack2: OR -> OR
	
	
	
	
	
	
	
	public static int[] handleBool(String input,String caller) {
		String arr[] = input.split(" ");
		//int pos=1;
		int[] result=new int[0];
		Stack<String> opStack=new Stack<String>();
		Stack<int[]> documentsStack=new Stack<int[]>();
		
		for(int i=0;i<arr.length;i++) {
			System.out.println("The Word: "+arr[i]);
			if(arr[i].equals("AND")){
				System.out.println("found AND");
				opStack.push("AND");
			}
			else if(arr[i].equals("OR")) {
				if(opStack.empty() || !opStack.topDataWithoutPop().equals("AND")) {
					opStack.push("OR");
					System.out.println("Added OR");
				}
				else {
					System.out.println("Error! Two Boolean Operation After Each Other");//???
					return new int[0];
				}
			}
			else if(arr[i].equals("NOT")) {
				System.out.println("Found NOT");
				opStack.push("NOT");
			}
			else {//normal word
				int wordArr[];
				if(caller.equalsIgnoreCase("index")) {
					wordArr = getIndexDocs(arr[i]);//!!!!!!!!
				}
				else if(caller.equalsIgnoreCase("inverse")) {
					wordArr =getInverseDocs(arr[i]);
				}
				else if(caller.equalsIgnoreCase("bst")){
					wordArr = getBSTDocs(arr[i]);
				}
				else {
					System.out.println("Error unknown caller!!");
					return new int[0];
				}	
				System.out.println("top method: "+opStack.topDataWithoutPop());
				if(opStack.empty()|| opStack.topDataWithoutPop().equals("OR")) {
					System.out.println("Pushed a word");
					documentsStack.push(wordArr);
				}
				else if(opStack.topDataWithoutPop().equals("AND")) {
					System.out.println("Word With AND");
					opStack.pop();
					if(!documentsStack.empty()) {
						System.out.println("Dealing with 2 words with AND");
						int docs[] = documentsStack.pop();
						result = andMethod(docs, wordArr);
						documentsStack.push(result);
					}
					else {
						System.out.println("Error??");
					}
				}
				else if(opStack.topDataWithoutPop().equals("NOT")) {
					System.out.println("Dealing With NOT");
					opStack.pop();
					result = notMethod(wordArr, documents.length);
					documentsStack.push(result);
				}
				else {
					System.out.println("Error in Order of words and operations!!");
					return new int [0];
				}
			}
			
		}
		while(!documentsStack.empty()) {
			result = orMethod(result, documentsStack.pop());
		}
		return result;
	}
	
	public static int[] getIndexDocs(String word) {
		int documentElement[]=new int[index.length];
		int counter=0;
		for(int i=0;i<index.length;i++) {
			if(index[i].checkExistanceOfWord(word)) {
				documentElement[counter++]=i;
			}
		}
		int result[]=new int[counter];
		for(int i=0;i<counter;i++) {
			result[i]=documentElement[i];
		}
		return result;
	}
	
	public static int[] getInverseDocs(String word) {
		for(int i=0;i<inverted.length;i++) {
			if(inverted[i].word.equalsIgnoreCase(word))
				return inverted[i].getDocId();
		}
		System.out.println("inverse docs not Found!!");
		return new int[0];
	}
	
	public static int[] getBSTDocs(String word) {
		if(bs.findkey(word)) {
			return bs.retrieve().getDocId();
		}
		else {
			System.out.println("BST docs not found!!");
			return new int[0];
		}
	}
	
//	public static int[] handleBoolInverted(String input) {
//		String arr[] = input.split(" ");
//		int size = arr.length;
//		int pos = 1;
//		InvertedIndex n1 = null;
//		InvertedIndex n2 = null;
//		//Inverted
//		for(int i=0;i<inverted.length;i++) {
//			if(arr[pos-1].equals(inverted[i].getWord())) {
//				n1=inverted[i];
//			}
//			
//			if(arr[pos+1].equals(inverted[i].getWord())) {
//				n2=inverted[i];
//			}
//			if(n1!=null && n2!=null) {
//				//System.out.println(i+")found both about to leave");
//				break;
//			}
//		}
//		if(n1==null || n2==null) {
//			System.out.println("the word is not found!");
//			return new int[0];
//		}
//		int result[]=andMethod(n1.getDocId(), n2.getDocId());
//		printArr(result);
//		return result;
//	}
//	
//	public static int[] handleBoolBST(String input) {
//		String arr[] = input.split(" ");
//		//int size = arr.length;
//		int pos = 1;
//		InvertedIndex n1 = null;
//		InvertedIndex n2 = null;
//		//BST
//		if (bs.findkey(arr[pos-1].toLowerCase())) {
//			n1 = bs.retrieve();
//			//System.out.println("n1= "+n1.word);
//		}
//		if (bs.findkey(arr[pos+1].toLowerCase())) {
//			n2 = bs.retrieve();
//			//System.out.println("n2= "+n2.word);
//		}
//		if(n1==null || n2==null) {
//			System.out.println("The used word doesn't exits");
//			return new int[0];
//		}
//		int result[]=andMethod(n1.getDocId(), n2.getDocId());
//		printArr(result);
//		return result;
//	}
	
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
