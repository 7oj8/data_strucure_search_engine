import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
                buffer.readLine().toLowerCase();//to get rid of the header
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
                temp = temp.replaceAll("[^a-zA-Z0-9 \n]", "");//only keep alphanumeric
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

            BSTree<InvertedIndex> bst = new BSTree<>();

            for(int i=0;i<sentences.length;i++) {
                System.out.println(i+")Entered");
                sentences[i]=sentences[i].replaceAll("\\s{2,}", " ");//remove spaces after each other
                sentences[i]=sentences[i].replaceAll("^ ","");//remove spaces from the start
                words=sentences[i].split(" ");
                index[i]=new indexList();
                for(int j=0;j<words.length;j++) {
                    index[i].addWord(words[j]);
                }
                index[i].printIndexNode();
                System.out.println(i+")Leaving");


                //System.out.println(""+splitted[i]+"");
            }

            String wordsSpaces = fileText.replace("\n", " ");
            wordsSpaces=wordsSpaces.replaceAll("\\s{2,}", " ");
            words=wordsSpaces.split(" ");
            inverted=new InvertedIndex[words.length];
            System.out.println(words.length);
            //System.out.println(""+wordsSpaces);
            int documentId;
            int xx=0;
            for(int i=0;i<words.length;i++) {
                //documentId=i;
                //sentences[i]= sentences[i].replaceAll("\\s{2,}", " ");
                //sentences[i]= sentences[i].replaceAll("^ ", "");
                //words=sentences[i].split(" ");
                inverted[i]=new InvertedIndex(words[i]);
                //System.out.println(words[i]);

                //System.out.println(sentences[1]);

                for(int j=0;j<sentences.length;j++) { //sentences = document
                    documentId=j;
                    //System.out.println(j+")"+inverted[i].word);
                    //System.out.println("Inverted: "+inverted[i].word);
                    //System.out.println("Sentence: "+sentences[j]);

                    if(sentences[j].contains(inverted[i].word)){

                        inverted[i].addDocument(documentId);
                        //System.out.println(sentences[j]);
                    }

                }
            }
            //System.out.println("The Words Of Number 39: \n\n");
            //index[39].printIndexNode();
            for(int i=0;i<words.length;i++) {
                inverted[i].printList();
            }
            System.out.println("\n\nFinish!");
        }
    }


