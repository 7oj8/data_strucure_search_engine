import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Set;

import javax.crypto.spec.IvParameterSpec;
import javax.swing.text.StyledEditorKit.BoldAction;


public class ProjectMain {
    static BSTree<InvertedIndex> bs = new BSTree<>();
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
            //System.out.println(temp);

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


                }

                //words.length--;
            }
        }
        int newSize = L;
        //System.out.println(counter+"");
        for(int i=0;i<newSize;i++) {
            //System.out.println(i+")"+words[i]);
        }
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
                        //System.out.println(i+":"+words[i]);
                    }
                    //Recent volcanic activity has had a noticeable effect on local weather patterns, including temperature drops and ash-induced rain, affecting air quality and public health.
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
                //bs.retrieve().printList();
            }
        }


        int t1[]= inverted[49].getDocId();
        int t2[]= inverted[48].getDocId();
        int t3[]= andMethod(t1, t2);
        t3 = orMethod(t1, t2);
        System.out.println("1:\n");
        for(int i=0;i<t1.length;i++) {
            //System.out.println(""+t1[i]);
        }
        System.out.println("\n2:\n");
        for(int i=0;i<t2.length;i++) {
            //System.out.println(""+t2[i]);
        }
        System.out.println("\n3:\n");
        for(int i=0;i<t3.length;i++) {
            //System.out.println(""+t3[i]);
        }
        //System.out.println("\n\n\n"+);
        bs.findkey("head");
        System.out.println(bs.retrieve().getDocId()[0]);
        bs.findkey("air");
        System.out.println(bs.retrieve().getDocId()[0]);

        handleInput("air AND head");
        System.out.println("\n\nFinish!");
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

        //1 2 3
        //2 5 8
        //
        for(int i=0;i<n1.length;i++) {
            for(int j=0;j<n2.length;j++) {
                if(n1[i]==n2[j]) {
                    tmp[count]=n1[i];
                    count++;
                }
            }
        }
        //System.out.println("in AND");
        int tmp2[]=new int[tmp.length-count];
        boolean isAllZero=false;
        //System.out.println(count);
        for(int i=0;i<tmp2.length;i++) {
            tmp2[i]=tmp[i];
            if(tmp2[i]!=)
            //System.out.println(i+") "+tmp2[i]);
        }
        //System.out.println();
        return tmp2;
    }



    public static int[] orMethod(int n1[],int n2[]) {
        int tmp[] = new int[n1.length+n2.length];
        int count=0;

        for(int i=0;i<n1.length;i++) {
            tmp[count++]=n1[i];
        }

        //1 2 3
        //2 3 4

        for(int i=0;i<n2.length;i++) {
            tmp[count++]=n2[i];
        }
        int L=tmp.length;

        for(int i=0;i<L;i++) {

            for(int j=i+1;j<L;j++) {
                if(tmp[i]==tmp[j]){
                    int temp= tmp[j];
                    tmp[j]=tmp[L-1];
                    tmp[L-1]=temp;
                    L--;


                }


            }
        }
        int result[]=new int[L];
        for(int i=0;i<result.length;i++) {
            result[i]=tmp[i];
        }
        return result;
    }

    public static void handleInput(String input){
        // Split input into individual tokens (words and operators)
        String arr[] = input.split(" ");

        InvertedIndex n1 = null;    // To hold the Inverted Index for the first word
        InvertedIndex n2 = null;    // To hold the Inverted Index for the second word
        int[] result = new int[50]; // To store the resulting document IDs after the operations
        int resultCount = 0;        // Count of document IDs in the result

        // Loop through each word in the input
        // head , AND , air
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("AND")) {   // Handle AND operation
                if (bs.findkey(arr[i-1])) {    // If the word before 'AND' exists in the BST
                    n1 = bs.retrieve();
                    //System.out.println("\n\n\n\n"+n1.word+"\n\n\n");
                }
                if (bs.findkey(arr[i+1])) {    // If the word after 'AND' exists in the BST
                    n2 = bs.retrieve();
                    //System.out.println("\n\n\n\n"+n2.word+"\n\n\n");
                }


                result = andMethod(n1.getDocId(), n2.getDocId());
                int zzzzz[] = andMethod(n1.getDocId(), n2.getDocId());
                System.out.println("Before");
                System.out.println(zzzzz.length);
                for(int zz=0;zz<zzzzz.length;zz++) {
                    System.out.println(zz+")"+zzzzz[zz]);
                }
                System.out.println("After");
                //printArr(result);
                //System.out.println("AF");
                resultCount = result.length;  // Set the count to the new size of the result array
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
                resultCount = result.length;  // Set the count to the new size of the result array
            }
            else {
                // Handle the case when the word is a direct search term (not an operator)
                if (bs.findkey(arr[i])) { // Check if the word exists in the BST
                    //System.out.println("arr[i]: "+arr[i]);
                    n1 = bs.retrieve();  // Retrieve the Inverted Index for the word
                    //System.out.println("retrive: "+n1.word);
                    // Set the result to the document IDs associated with that word
                    result = n1.getDocId();
                    resultCount = result.length;  // Set the count to the length of the result array
                }
            }
        }

        // Output the final result (document IDs after all operations)
        System.out.println("Resulting document IDs:");
        for (int i = 0; i < resultCount; i++) {
            System.out.println(result[i]);
        }
    }
    public static void printArr(int n1[]) {
        System.out.println("in the printArr method:");
        for(int i=0;i<n1.length;i++)
            System.out.println(i+")i: "+n1[i]);
    }
}
