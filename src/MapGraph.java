import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MapGraph {
	
	class MapVertex implements Comparable {
		private String id;
		private String name;
		private int x, y;
		private boolean isIntersection;
		
		private String predecessor = null;
		private double cost = Double.MAX_VALUE;
		
		public MapVertex(String id, String name, int x, int y, boolean isIntersection) {
			this.id = id;
			this.name = name;
			this.x = x;
			this.y = y;
			this.isIntersection = isIntersection;
		}
		
		public void updateCost(double newCost, String newPredecessor) {
			this.cost = newCost;
			this.predecessor = newPredecessor;
		}

		public String getId() {
			return this.id;
		}
		
		public String getName() {
			return this.name;
		}
		
		public int getX() {
			return this.x;
		}
		
		public int getY() {
			return this.y;
		}
		
		public double getCost() {
			return this.cost;
		}
		
		public Set<MapVertex> getNeighbors() {
			Set<MapVertex> neighbors = new HashSet<>();
			for(MapEdge e : edges) {
				MapVertex anotherV = e.getAnotherVertex(this);
				if(anotherV != null) {
					neighbors.add(anotherV);
				}
			}
			return neighbors;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof MapVertex) {
				if (((MapVertex) o).id.equals(this.id))
					return true;
				else
					return false;
			}
			else return false;
		}

		@Override
		public int compareTo(Object o) {
			if(o instanceof MapVertex) {
				if(((MapVertex) o).cost < this.cost)
					return 1;
				else if(((MapVertex) o).cost > this.cost)
					return -1;
				else
					return 0;
			}
			return 1;
		}
		
		public void printVertex() {
			System.out.println("Node(id="+id+")");
			System.out.println("\tname : "+name);
			System.out.println("\tx : "+x);
			System.out.println("\ty : "+y);
		}

	}
	
	class MapEdge {
		private String id;
		private String[] adjacentNodes = new String[2];
		private double length;
		
		//Safe-element
		private int cctvNum;
		private double averageWidth;
		private double averageIllum;
		private int convStoreNum;
		private double userScore;
		
		//Dangerous-element
		//add later
		
		public MapEdge(String id, String[] adjacentNodes, double length, int cctvNum, double averageWidth,
				int averageIllum, int convStoreNum, double userScore) {
			this.id = id;
			this.adjacentNodes = adjacentNodes;
			this.length = length;
			this.cctvNum = cctvNum;
			this.averageWidth = averageWidth;
			this.averageIllum = averageIllum;
			this.convStoreNum = convStoreNum;
			this.userScore = userScore;
		}
		
		public String getAdjacentNode(int index) {
			return adjacentNodes[index];
		}
		
		public double getCost() {
			return length;
		}
		
		public MapVertex getAnotherVertex(MapVertex v) {
			if(this.adjacentNodes[0].equals(v.getId())) return findVertexById(adjacentNodes[1]);
			else if(this.adjacentNodes[1].equals(v.getId())) return findVertexById(adjacentNodes[0]);
			else return null;
		}
		
		public void printEdge() {
			System.out.println("Edge(id="+id+")");
			System.out.println("\tnodes : "+adjacentNodes[0]+"-"+adjacentNodes[1]);
		}
	}
	
	private Set<MapVertex> vertices = new HashSet<>();
	private Set<MapEdge> edges = new HashSet<>();
	
	public MapGraph() {
		MapXMLParser xmlParser = new MapXMLParser();
		nodeList2Graph(xmlParser.parseVertices(), xmlParser.parseEdges());
	}
	
	public Set<MapVertex> getVertexSet() {
		return vertices;
	}
	
	public Set<MapEdge> getEdgeSet() {
		return edges;
	}

	public void addVertex(MapVertex vertex) {
		vertices.add(vertex);
	}
	public void addVertex(String id, String name, int x, int y, boolean isIntersection) {
		vertices.add(new MapVertex(id, name, x, y, isIntersection));
	}

	public void addEdge(MapEdge edge) {
		edges.add(edge);
	}
	public void addEdge(String id, String[] adjacentNodes, double length, int cctvNum, double averageWidth,
			int averageIllum, int convStoreNum, double userScore) {
		edges.add(new MapEdge(id, adjacentNodes, length, cctvNum, averageWidth, averageIllum, convStoreNum, userScore));
	}
	
	public void initCosts() {
		for(MapVertex v : vertices) {
			v.predecessor = null;
			v.cost = Double.MAX_VALUE;
		}
	}

	public MapVertex findVertexById(String id) {
		for(MapVertex v : vertices) {
			if(v.id.equals(id)) {
				return v;
			}
		}
		return null;
	}
	
	public double getDistance(MapVertex v, MapVertex u) {
		return Math.sqrt((v.x-u.x)*(v.x-u.x)+(v.y-u.y)*(v.y-u.y));
	}
	
	public double getEdgeCost(MapVertex v, MapVertex u) {
		for(MapEdge e : edges) {
			if(findVertexById(e.getAdjacentNode(0)) == null || findVertexById(e.getAdjacentNode(1)) == null)
				continue;
			if((findVertexById(e.getAdjacentNode(0)).equals(v) && findVertexById(e.getAdjacentNode(1)).equals(u)) ||
					(findVertexById(e.getAdjacentNode(0)).equals(u) && findVertexById(e.getAdjacentNode(1)).equals(v)))
				return e.getCost();
		}
		return Double.MAX_VALUE;
	}
	
	public List<MapVertex> constructPath(String endId) {
		List<MapVertex> path = new ArrayList<>();
		MapVertex v = findVertexById(endId);
		path.add(v);
		while(v.predecessor!=null) {
			v = findVertexById(v.predecessor);
			path.add(v);
		}
		return path;
	}
	
	public void nodeList2Graph(NodeList vertexList, NodeList edgeList) {
		
		for(int i=0; i<vertexList.getLength(); i++) {
			Node node = vertexList.item(i);
			Element element = (Element) node;
			String id = element.getAttribute("id");
			NodeList fields = element.getChildNodes();
			String name = null;
			int x = -1;
			int y = -1;
			boolean isIntersection = false;
			for(int j=0; j<fields.getLength(); j++) {
				Node snode = fields.item(j);
				String nodeName = snode.getNodeName();
				if(nodeName.equals("name")) {
					name = snode.getFirstChild().getTextContent();
				}
				else if(nodeName.equals("x")) {
					x = Integer.parseInt(snode.getFirstChild().getTextContent());
				}
				else if(nodeName.equals("y")) {
					y = Integer.parseInt(snode.getFirstChild().getTextContent());
				}
				else if(nodeName.equals("type")) {
				}
			}
			MapVertex mv = new MapVertex(id, name, x, y, isIntersection);
			addVertex(mv);
			
		}
		
		for(int i=0; i<edgeList.getLength(); i++) {
			Node node = edgeList.item(i);
			Element element = (Element) node;
			NodeList fields = element.getChildNodes();
			String id = null;
			String[] adjacentNodes = new String[2];
			adjacentNodes[0] = null;
			adjacentNodes[1] = null;
			double length = 0;
			int cctvNum = 0;
			double averageWidth = 0;
			int averageIllum = 0;
			int convStoreNum = 0;
			double userScore = 0;
			for(int j=0; j<fields.getLength(); j++) {
				Node snode = fields.item(j);
				String nodeName = snode.getNodeName();
				if(nodeName.equals("node")) {
					if(adjacentNodes[0]==null)
						adjacentNodes[0] = ((Element)snode).getAttribute("id");
					else
						adjacentNodes[1] = ((Element)snode).getAttribute("id");
				}
				else if(nodeName.equals("length")) {
					length = Double.parseDouble(snode.getFirstChild().getTextContent());
				}
				else if(nodeName.equals("width")) {
					averageWidth = Double.parseDouble(snode.getFirstChild().getTextContent());
				}
			}
			MapEdge me = new MapEdge(id, adjacentNodes, length, cctvNum, averageWidth,
					averageIllum, convStoreNum, userScore);
			addEdge(me);
			
		}
	}
	
}
