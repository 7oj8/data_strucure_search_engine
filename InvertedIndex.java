public class InvertedIndex {


    private class Node{
        int documentId,counter; //counter?
        Node next;

        public Node(int id) {
            counter=1;
            documentId=id;
            next=null;
        }
    }

    Node current,head;
    String word="";
    int documentsCounter;

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

    public void addDocument(int id) {
        if(head==null) {
            current = head = new Node(id);
            return;
        }
        Node n = findDocument(id);
        if(n==null) {
            n=new Node(id);
            current.next=n;
            current=n;
            documentsCounter++;
        }
        else {
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
}
