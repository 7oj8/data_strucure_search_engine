
public class Stack <T>{
	private class Node<T> {
		T data;
		Node<T> next;
		public Node(T newData) {
			this.data=newData;
			next=null;
		}
	}
	
	Node<T> top;
	public Stack() {
		top=null;
	}
	public T pop() {
		T data= top.data;
		top = top.next;
		return data;
	}
	public void push(T data) {
		Node<T> tmp = new Node<T>(data);
		tmp.next=top;
		top = tmp;
	}
	public T topDataWithoutPop() {
		if(top==null)
			return null;
		else
			return top.data;
	}
	public boolean empty() {
		return top==null;
	}
}
