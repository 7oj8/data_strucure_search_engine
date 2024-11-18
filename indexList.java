
public class indexList {
	
	private class Node{
		String word;
		int counter;
		Node next;
		
		public Node(String newWord) {
			counter=1;
			word=newWord;
			next=null;
		}
	}
	
	Node current,head; //every node is a word with counter
	int wordCounter; //The First empty index and the number of current document
	
	
	public indexList() {
		head=current=null;
		wordCounter=0;
	}

	private Node findNode(String word) {
		Node temp=head;
		while(temp!=null&&!temp.word.equals(word)) {
			current=temp;
			temp=temp.next;
		}
		return temp;
//		for(int i=0;i<wordCounter;i++) {
//			temp = nodeArr[i];
//			if(temp.word.equals(word)) {
//				return i;
//			}
//		}
//		return -1;
	}

	public void addWord(String word) {
		if(word.equals(" "))
			return;
		if(head==null) {
			current=head=new Node(word);
			wordCounter++;
			return;
		}
		Node n = findNode(word);
		if(n==null) {
			//if new Word
			n = new Node(word);
			current.next=n;
			current = n;
			wordCounter++;
		}
		else {
			n.counter=n.counter+1;
		}
	}
	
	public boolean checkExistanceOfWord(String word){
		if(findNode(word)!=null)
			return true;
		else
			return false;
	}
	
	public void printIndexNode() {
		Node temp=head;
		while(temp!=null) {
			System.out.println("The Word is "+temp.word+" And its counter is:"+temp.counter);
			temp=temp.next;
		}
	}
}
