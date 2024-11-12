import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;

import javax.crypto.spec.IvParameterSpec;
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
			System.out.println("ERROR");
			System.out.println(e.getMessage());
			return "";
		}

	}
	public static void main(String[] args) {
		System.out.println("Start....");
		String fileText = handleFile();
		//System.out.println(fileText);
		documents= fileText.split("\n");
		bs= new BSTree<>();
		String[] words=null;
		indexList[] index=new indexList[documents.length];//each item is words of one document
		InvertedIndex[] inverted;
		String wordsSpaces = fileText.replace("\n", " ");
		wordsSpaces=wordsSpaces.replaceAll("\\s{2,}", " ");
		words=wordsSpaces.split(" ");

		int size=words.length;
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
		inverted=new InvertedIndex[words.length];//newSize???
		int documentId;
		for(int i=0;i<newSize;i++) {
			inverted[i]= new InvertedIndex(words[i]);
			for(int j=0;j<documents.length;j++) {
				documentId=j;
				if(documents[j].contains(inverted[i].word)){
					String splitDocumentByWord[] = documents[j].split(" ");
					for(int z=0;z<splitDocumentByWord.length;z++) {
						if(splitDocumentByWord[z].equals(inverted[i].word)) {
							inverted[i].addDocument(documentId);
							bs.insert(words[i], inverted[i]);
						}
					}


//					String splitDocumentByWord[] = documents[j].split(inverted[i].word);
//					int NumOfRepeat = splitDocumentByWord.length-1;
//					for(int z=0;z<NumOfRepeat;z++) {
//						inverted[i].addDocument(documentId);
//						bs.insert(words[i], inverted[i]);
//					}
				}
			}
		}
		for(int i=0;i<newSize;i++) {
			//inverted[i].printList();
			if(bs.findkey(words[i])) {
				//System.out.println(i+":");
				//bs.retrieve().printList();
			}
		}
		//test more than one boolean
		handleInput("market AND sports");
		SimpleGUI s = new SimpleGUI();
		System.out.println("\n\nFinish!");
	}

	public static int[] andMethod(int n1[],int n2[]) {
		System.out.println("n1:");
		printArr(n1);
		System.out.println("\nn2:");
		printArr(n2);

		int tmp[];
		int count=0;
		if(n1.length>n2.length) {
			tmp= new int[n1.length];
		}
		else {
			tmp= new int[n2.length];
		}
		tmp[0]=-1;//as a flag for using this array

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
		System.out.println("Last thing in AND with size "+count);
		printArr(tmp2);
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
		for(int i=0;i<result.length;i++) {
			result[i]=arr[i];
		}
		return result;
	}

	public static int[] orMethod(int n1[],int n2[]) {
		System.out.println("in OR method");
		System.out.println("n1:");
		printArr(n1);
		System.out.println("n2:");
		printArr(n2);
		return addArr(n1, n2);


	}

	public static void handleInput(String input) {
		if(input.contains("AND")||input.contains("OR"))
			printArr(handleBoolInput(input,0,new int[0]));
		else {
			handleRankingInput(input);
		}
	}

	public static void handleRankingInput(String input){
		String words[] = input.toLowerCase().split(" ");
		InvertedIndex n = null;
		int documentsList[]=new int[0];
		int documentCounterList[];
		for(int i=0;i<words.length;i++) {
			if(bs.findkey(words[i])){
				n = bs.retrieve();
				//System.out.println("len: "+n.getDocId().length);
				//S M
				documentsList = addArr(documentsList,n.getDocId());
				printArr(documentsList);
			}
			else {
				System.out.println("The Words is not Saved!");
				return;
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
		sortArr(documentsList,documentCounterList);
	}

	public static void sortArr(int documents[],int counters[]) {
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
		printArrWithCount(documents,counters);
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
		}
		if (bs.findkey(arr[index+1].toLowerCase())) {
			n2 = bs.retrieve();
		}
		if(n1==null || n2==null) {
			System.out.println("The used word doesn't exits");
			return new int[0];
		}

		if(arr[index].equals("AND")) {
			System.out.println("in the AND");

			if(empty) {
				result = andMethod(n1.getDocId(), n2.getDocId());
				printArr(result);
			}
			else {
				result = andMethod(oldArr,n2.getDocId());
			}
		}
		else if(arr[index].equals("OR")) {
			if(empty) {
				result = orMethod(n1.getDocId(),n2.getDocId());
				printArr(result);
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

	//old method
	/*
	public static void handleInput(String input) {
		String arr[] = input.split(" ");
		InvertedIndex n1 = null;
		InvertedIndex n2 = null;
		int[] result = new int[50];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals("AND")) {
				System.out.println("in the AND");
				if (bs.findkey(arr[i-1])) {    // If the word before 'AND' exists in the BST
					n1 = bs.retrieve();
					//System.out.println("\n\n\n\n"+n1.word+"\n\n\n");
				}
				if (bs.findkey(arr[i+1])) {    // If the word after 'AND' exists in the BST
					n2 = bs.retrieve();
					//System.out.println("\n\n\n\n"+n2.word+"\n\n\n");
				}

				if(n1==null || n2==null) {
					System.out.println("The used word doesn't exits");
					return new int[0];
				}

	            //int ex1[] = {1,2,3};
	            //printArr(ex1);
	            //int ex2[] = {4,5,7};
	            //printArr(ex2);
	            System.out.println("before and");
	            result = andMethod(n1.getDocId(), n2.getDocId());

	            //result = andMethod(ex1,ex2);
	            System.out.println("after and");
	            printArr(result);
	            //int zzzzz[] = andMethod(n1.getDocId(), n2.getDocId());
	            //System.out.println("Before");
	            //System.out.println(zzzzz.length);
	            //printArr(zzzzz);
	            //System.out.println("After");
	            //printArr(result);
	            //System.out.println("AF");
	            //resultCount = result.length;  // Set the count to the new size of the result array
			}
			else if (arr[i].equals("OR")) {  // Handle OR operation
				if (bs.findkey(arr[i-1])) {    // If the word before 'OR' exists in the BST
					n1 = bs.retrieve();  // Retrieve the Inverted Index for that word
				}
				if (bs.findkey(arr[i+1])) {    // If the word after 'OR' exists in the BST
					n2 = bs.retrieve();  // Retrieve the Inverted Index for the other word
				}

				// Perform OR operation on the document IDs from both Inverted Indexes
				result = orMethod(n1.getDocId(), n2.getDocId());
				printArr(result);
				//resultCount = result.length;  // Set the count to the new size of the result array
			}
//		        else {
//		            // Handle the case when the word is a direct search term (not an operator)
//		            if (bs.findkey(arr[i])) { // Check if the word exists in the BST
//		            	//System.out.println("arr[i]: "+arr[i]);
//		                n1 = bs.retrieve();  // Retrieve the Inverted Index for the word
//		                //System.out.println("retrive: "+n1.word);
//		                // Set the result to the document IDs associated with that word
//		                result = n1.getDocId();
//		                resultCount = result.length;  // Set the count to the length of the result array
//		            }
//		        }
		    }

		    // Output the final result (document IDs after all operations)
		    //System.out.println("Resulting document IDs:");
		    //printArr(result);

//		    for (int i = 0; i < resultCount; i++) {
//		        System.out.println(result[i]);
//		    }
	}*/

	public static void printArr(int n1[]) {
		System.out.println("in the printArr method:");
		for(int i=0;i<n1.length;i++)
			System.out.println(i+")i: "+n1[i]);
	}
	public static void printArrWithCount(int docs[] , int counts[]) {
		System.out.println("in the print Arr With Counter method:");
		for(int i=0;i<docs.length;i++) {
			System.out.println(i+") Document Id: "+docs[i]+", With Counter: "+counts[i]);

		}
	}
}
