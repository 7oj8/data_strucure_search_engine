
public class InvertedIndex {
	
	
	private class Node{
		int documentId,counter;
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
	
	public String getWord() {
		return word;
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
		
		if(head==null) {//first Node
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
