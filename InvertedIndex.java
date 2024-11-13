
public class InvertedIndex {


    private class Node{
        int documentId,counter; //counter?
        Node next;

        public Node(int id) {
            counter=1;
            documentId=id;
            next=null;
        }

        public int getId() {
            return documentId;
        }
    }

    Node current,head;
    String word="";
    int documentsCounter;







    //	public void printDocumentId() {
//		System.out.println();
//	}
    public InvertedIndex(String word) {
        this.word=word;
        head=current=null;
        documentsCounter=0;
    }

    private Node findDocument(int id) {
        Node temp=head;
        while(temp!=null&&temp.documentId!=id) {
            current=temp;
            temp=temp.next;
        }
        return temp;
    }





    //old add
    int i=0;
    public void addDocument(int id) {
        //System.out.println("PRINT");
        if(head==null) {
            current = head = new Node(id);
            return;
        }

        Node n = findDocument(id);
        if(n==null) {
            //System.out.println(i+++")ADD");
            n=new Node(id);
            current.next=n;
            current=n;
            documentsCounter++;
        }
        else {
            //System.out.println("NOT ADD");
            n.counter=n.counter+1;
        }
    }


    public void printList() {
        Node temp = head;
        System.out.println("The Documents For Word:"+word);
        while(temp!=null) {
            System.out.print(temp.documentId+":"+temp.counter+"\t");
            temp=temp.next;
        }
        System.out.println();
    }

    public int[] getDocId(){
        Node tmp = head;
        int ids[] = new int[documentsCounter+1];
        for(int i=0;i<documentsCounter+1;i++) {
            ids[i]=tmp.documentId;
            tmp=tmp.next;
        }
        return ids;
    }
    public int getCounter(int id) {
        Node n = findDocument(id);
        if(n!=null)
            return n.counter;
        else
            return 0;
    }
}
