import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ProjectMain {
	static BSTree<InvertedIndex> bs;
	static String[] documents;
	static InvertedIndex[] inverted;
	static indexList[] index;

	public static void main(String[] args) {
		System.out.println("Start....");
		String inputText = handleFile();
		initDataStructure(inputText);
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
//			buffer.readLine();//to get rid of the header
//			int numOfItem=50;
//			for(int i=0;i<numOfItem;i++) {
//				temp += ((buffer.readLine().toLowerCase()).replaceFirst(""+i, ""));
//				temp+="\n";
//			}
			buffer.readLine(); // Skip the header
			String line;
			while ((line = buffer.readLine()) != null) {
				temp += (line.toLowerCase()).replaceFirst("" + temp.split("\n").length, "");
				temp += "\n"; // Add a newline for the next document
			}
			String stopWord = stopWordsBuffer.readLine();

			while(stopWord!=null) {
				temp = temp.replaceAll("\\b"+stopWord+"\\b", " ");
				stopWord = stopWordsBuffer.readLine();
			}
			buffer.close();
			stopWordsBuffer.close();
			temp = temp.replaceAll("-", " ");
			temp = temp.replaceAll("[^a-z0-9 \n]", " ");//only keep alphanumeric
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
		index=new indexList[documents.length]; //each item is words of one document
		String wordsSpaces = fileText.replace("\n", " ");
		wordsSpaces=wordsSpaces.replaceAll("\\s{2,}", " ");
		wordsSpaces= wordsSpaces.replaceAll("^ ", ""); //remove space at the start
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
		inverted=new InvertedIndex[newSize];
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
	}

	public static int[] notMethod(int documents[],int documentCount) {
		int newSize=documentCount-documents.length;
		int pos=0;
		int result[]=new int[newSize];
		//initiate with all documents
		boolean found;
		for(int i=0;i<documentCount;i++) {
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
		int tmp[];
		int count=0;
		if(n1.length>n2.length) {
			tmp= new int[n1.length];
		}
		else {
			tmp= new int[n2.length];
		}
		tmp[0]=-1; //as a flag for using this array

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
		return addArr(n1, n2);
	}

	public static String handleInput(String input, String caller) {
		int result[]=new int[0];
		String text="";
		if(input.contains("AND")||input.contains("OR")||input.contains("NOT")) {
			result = handleBool(input, caller);
			for(int i=0;i<result.length;i++) {
				text= text +" " +result[i];
			}
		}
		else {
			text =handleRankingInput(input,caller);
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
		return sortArr(documentsList,documentCounterList);
	}

	//	public static String sortArr(int documents[],int counters[]) {
//		int n = documents.length;
//		for (int i = 0; i < n-1; i++){
//			for (int j = 0; j < n-1-i; j++){
//				if (counters[j] < counters[j+1]){
//					//Swap A[j] with A[j+1]
//					int tmp1 = counters[j];
//					int tmp2 = documents[j];
//					counters[j] = counters[j+1];
//					documents[j] = documents[j+1];
//					counters[j+1] = tmp1 ;
//					documents[j+1] = tmp2 ;
//				}
//			}
//		}
//		return printArrWithCount(documents,counters);
//	}
	public static String sortArr(int documents[], int counters[]) {
		mergeSort(documents, counters, 0, counters.length - 1);
		return printArrWithCount(documents, counters);
	}

	public static void mergeSort(int[] documents, int[] counters, int l, int r) {
		if (l >= r)
			return;
		int m = (l + r) / 2;
		mergeSort(documents, counters, l, m); // Sort first half
		mergeSort(documents, counters, m + 1, r); // Sort second half
		merge(documents, counters, l, m, r); // Merge
	}

	private static void merge(int[] documents, int[] counters, int l, int m, int r) {
		int[] tempDocs = new int[r - l + 1];
		int[] tempCounters = new int[r - l + 1];
		int i = l, j = m + 1, k = 0;

		// Merge based on counters array
		while (i <= m && j <= r) {
			if (counters[i] >= counters[j]) { // Descending order
				tempCounters[k] = counters[i];
				tempDocs[k++] = documents[i++];
			} else {
				tempCounters[k] = counters[j];
				tempDocs[k++] = documents[j++];
			}
		}

		// Copy remaining elements
		while (i <= m) {
			tempCounters[k] = counters[i];
			tempDocs[k++] = documents[i++];
		}
		while (j <= r) {
			tempCounters[k] = counters[j];
			tempDocs[k++] = documents[j++];
		}

		// Copy back to original arrays
		for (k = 0; k < tempCounters.length; k++) {
			counters[l + k] = tempCounters[k];
			documents[l + k] = tempDocs[k];
		}
	}



	public static int findDocumentCounter(String word,int id){
		int counter=0;
		if(bs.findkey(word)) {
			counter = bs.retrieve().getCounter(id);
		}
		return counter;
	}

	public static int[] handleBool(String input,String caller) {
		String arr[] = input.split(" ");
		int[] result=new int[0];
		Stack<String> opStack=new Stack<String>();
		Stack<int[]> documentsStack=new Stack<int[]>();
		String boolOp = "AND OR NOT";
		//int opCount=0;
		boolean didNot=false,didOp=false;


		//Market AND sports NOT head OR weather
		for(int i=1;i<arr.length-1;i++) {
			if((arr[i].equals("AND")||arr[i].equals("OR")) && (arr[i-1].equals("OR")||arr[i+1].equals("AND"))) {
				System.out.println("Error: Two AND/OR next To Each Other ");
				return new int[0];
			}
			if(arr[i].equals("NOT") && (arr[i+1].equals("NOT")|| arr[i-1].equals("NOT"))) {
				System.out.println("Error: Two NOT next To Each Other ");
				return new int[0];
			}
		}



		for(int i=0;i<arr.length;i++) {
			if((arr[i].equals("AND")||arr[i].equals("OR"))&&didOp) {
				System.out.println("Error in Order of words and operations!!\ntwo Boolean after each other are not Allowed!!");
				return new int[0];
			}
			if(arr[i].equals("NOT")&&didNot) {
				System.out.println("Error in Order of words and operations!!\ntwo NOT after each other are not Allowed!!");
				return new int[0];
			}
			if(arr[i].equals("AND")){
				opStack.push("AND");
				//opCount++;
				didOp=true;
				didNot=false;
			}
			else if(arr[i].equals("OR")) {
				if( opStack.empty() || !opStack.topDataWithoutPop().equals("AND")) {
					opStack.push("OR");
					//opCount++;
					didOp=true;
					didNot=false;
				}
				else {
					System.out.println("Error");
					return new int[0];
				}
			}
			else if(arr[i].equals("NOT")) {
				opStack.push("NOT");
				//opCount++;
				didNot=true;
				didOp=false;
			}
			else {//normal word
				int wordArr[];
				//opCount--;
				didOp=false;
				if(caller.equalsIgnoreCase("index")) {
					wordArr = getIndexDocs(arr[i]);
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
				if(opStack.empty()|| opStack.topDataWithoutPop().equals("OR")) {
					documentsStack.push(wordArr);
				}
				else if(opStack.topDataWithoutPop().equals("AND")) {
					opStack.pop();
					if(!documentsStack.empty()) {
						int docs[] = documentsStack.pop();
						result = andMethod(docs, wordArr);
						documentsStack.push(result);
					}
					else {
						System.out.println("Error");
					}
				}
				else if(opStack.topDataWithoutPop().equals("NOT")) {
					opStack.pop();
					result = notMethod(wordArr, documents.length);
					documentsStack.push(result);
				}
				else {
					System.out.println("Error in Order of words and operations!!");
					return new int [0];
				}
			}
			//-1 0 1
//			if(opCount>=1 || opCount<=1) {
//				System.out.println("Error in boolean operation syntax!!");
//				return new int [0];
//			}

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
			if(index[i].checkExistanceOfWord(word.toLowerCase())) {
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
		if(bs.findkey(word.toLowerCase())) {
			return bs.retrieve().getDocId();
		}
		else {
			System.out.println("BST docs not found!!");
			return new int[0];
		}
	}

	public static void printArr(int n1[]) {
		System.out.println("in the printArr method:");
		for(int i=0;i<n1.length;i++)
			System.out.println(i+")i: "+n1[i]);
	}

	public static String printArrWithCount(int docs[] , int counts[]) {
		String text="";
		for(int i=0;i<docs.length;i++) {
			//System.out.println(i+") Document Id: "+docs[i]+", With Counter: "+counts[i]);
			text +=((i+1)+") Document Id: "+docs[i]+", With Counter: "+counts[i]);
			text+="\n";
		}
		return text;
	}

	public static String Printindex(int id) {
		String text = documents[id].replaceAll("\\s{2,}", " ");
		//System.out.println(text);
		return text;
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