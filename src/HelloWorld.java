import java.util.PriorityQueue;
import java.util.Queue;

public class HelloWorld {

	class Node implements Comparable {
		public String name;
		public int key;

		public Node(String name, int key) {
			this.name = name;
			this.key = key;
		}
		
		public String toString() {
			return name;
		}

		@Override
		public int compareTo(Object o) {
			if(o instanceof Node) {
				if(((Node) o).key > this.key)
					return 1;
				else if(((Node) o).key < this.key)
					return -1;
				else
					return 0;
			}
			return 0;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HelloWorld hw = new HelloWorld();
		Node currNode;
		System.out.println("Hello World");
		PriorityQueue<Node> pq = new PriorityQueue<>();
		Node x = hw.new Node("A",1000);
		pq.add(x);
		pq.add(hw.new Node("B",500));
		pq.add(hw.new Node("C",900));
		pq.add(hw.new Node("D",50));
		pq.add(hw.new Node("E",700));
		pq.add(hw.new Node("F",300));
		while(!pq.isEmpty()) {
			x.key = 5;
			currNode = pq.remove();
			System.out.println(currNode);
			System.out.println(currNode.key);
		}
	}

}
