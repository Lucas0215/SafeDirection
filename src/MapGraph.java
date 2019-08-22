import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MapGraph {
	
	class MapVertex {
		private String id;
		private String name;
		private int x, y;
		private boolean isIntersection;
		
		private String predecessor = null;
		private double cost = Double.MAX_VALUE;
		private double heuristic = Double.MAX_VALUE;
		
		public MapVertex(String id, String name, int x, int y, boolean isIntersection) {
			this.id = id;
			this.name = name;
			this.x = x;
			this.y = y;
			this.isIntersection = isIntersection;
		}
		
		public void updateCost(double newCost, double newHeuristic, String newPredecessor) {
			this.cost = newCost;
			this.heuristic = newHeuristic;
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
		
		public double getHeuristic() {
			return this.heuristic;
		}
		
		public Set<MapVertex> getNeighbors() {
			Set<MapVertex> neighbors = new HashSet<>();
			for(MapEdge e : getEdgeSet()) {
				MapVertex anotherV = e.getAnotherVertex(this);
				if(anotherV != null) {
					neighbors.add(anotherV);
				}
			}
			return neighbors;
		}
		
		@Override
		public String toString() {
			return "Node(id="+id+")"+"\n"+"\tname : "+name+"\n"+"\tx : "+x+"\n"+"\ty : "+y;
		}

	}
	
	class MapEdge {
		private String[] adjacentNodes = new String[2];
		private double length;
		
		//Safe-element
		private int cctvNum;
		private int shelterNum;
		private int convenienceNum;
		private double averageWidth;
		private double averageBrightness;
		
		//Dangerous-element
		private int adultEntNum;
		private int constructionNum;
		
		public MapEdge(String[] adjacentNodes, double length, int cctvNum, int shelterNum,
				int convenienceNum, double averageWidth, double averageBrightness, int adultEntNum,
				int constructionNum) {
			this.adjacentNodes = adjacentNodes;
			this.length = length;
			this.cctvNum = cctvNum;
			this.shelterNum = shelterNum;
			this.convenienceNum = convenienceNum;
			this.averageWidth = averageWidth;
			this.averageBrightness = averageBrightness;
			this.adultEntNum = adultEntNum;
			this.constructionNum = constructionNum;
		}
		
		public void update(double length, int cctvNum, int shelterNum,
				int convenienceNum, double averageWidth, double averageBrightness, int adultEntNum,
				int constructionNum) {
			this.length = length;
			this.cctvNum = cctvNum;
			this.shelterNum = shelterNum;
			this.convenienceNum = convenienceNum;
			this.averageWidth = averageWidth;
			this.averageBrightness = averageBrightness;
			this.adultEntNum = adultEntNum;
			this.constructionNum = constructionNum;
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
		
		@Override
		public String toString() {
			return "Edge"+"\n"+"\tNodes : "+adjacentNodes[0]+"-"+adjacentNodes[1]+"\n"+"\tLength : "
					+length+"\n"+"\tWidth : "+averageWidth+"\n"+"\tCCTV : "+cctvNum+"\n"+"\tShelter : "+shelterNum+"\n"+"\tConvenience Store : "
					+convenienceNum+"\n"+"\tBrightness : "+averageBrightness+"\n"+"\tAdult Entertainment : "+adultEntNum
					+"\n"+"\tUnder Construction : "+constructionNum;
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
	public void addEdge(String[] adjacentNodes, double length, int cctvNum, int shelterNum,
			int convenienceNum, double averageWidth, double averageBrightness, int adultEntNum,
			int constructionNum) {
		edges.add(new MapEdge(adjacentNodes, length, cctvNum, shelterNum, convenienceNum, averageWidth, averageBrightness, adultEntNum, constructionNum));
	}
	
	public void initCosts() {
		for(MapVertex v : vertices) {
			v.predecessor = null;
			v.cost = Double.MAX_VALUE;
			v.heuristic = Double.MAX_VALUE;
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
	
	public MapVertex findVertexByName(String name) {
		for(MapVertex v : vertices) {
			if(v.name.equals(name)) {
				return v;
			}
		}
		return null;
	}
	
	public double getDistance(MapVertex v, MapVertex u) {
		return Math.sqrt((v.x-u.x)*(v.x-u.x)+(v.y-u.y)*(v.y-u.y)) / 3;
	}
	
	public MapEdge getEdge(MapVertex v, MapVertex u) {
		for(MapEdge e : edges) {
			MapVertex adj1 = findVertexById(e.getAdjacentNode(0));
			MapVertex adj2 = findVertexById(e.getAdjacentNode(1));
			if(adj1 != null && adj2 != null) {
				if(adj1.equals(v) && adj2.equals(u) ||
						adj1.equals(u) && adj2.equals(v))
					return e;
			}
		}
		return null;
	}
	
	public double getEdgeCost(MapVertex v, MapVertex u) {
		MapEdge e = getEdge(v,u);
		if(e!=null)
			return e.getCost();
		return Double.MAX_VALUE;
	}
	
	public double getSafetyCost(MapVertex v, MapVertex u) {
		MapEdge e = getEdge(v,u);
		if(e!=null) {
			double reputation;
			if(v.getId().compareTo(u.getId())>0)
				reputation = Utils.getAverageReputation(u.getId(), v.getId());
			else
				reputation = Utils.getAverageReputation(v.getId(), u.getId());
			return Settings.getReputationImp() * (5-reputation)
			+ Settings.getCctvImp() * ((e.length - e.cctvNum * 20)>0?(e.length - e.cctvNum * 20):0) * 0.5
			+ Settings.getShelterImp() * ((e.length - e.shelterNum * 50)>0?(e.length - e.shelterNum * 50):0) * 0.5
			+ Settings.getConvenienceImp() * ((e.length - e.convenienceNum * 50)>0?(e.length - e.convenienceNum * 50):0) * 0.5
			+ Settings.getWidthImp() * (e.averageWidth > 10 ? 0 : (10 - e.averageWidth)) * e.length * 0.01
			+ Settings.getBrightnessImp() * (1 - e.averageBrightness) * e.length * 0.02
			+ Settings.getAdultEntImp() * e.adultEntNum
			+ Settings.getConstructionImp() * e.constructionNum * 2;
			
		}
		return 0;
	}
	
	public List<MapVertex> constructPath(String endId) {
		List<MapVertex> path = new ArrayList<>();
		MapVertex v = findVertexById(endId);
		path.add(v);
		if(v != null) {
		while(v.predecessor!=null) {
				v = findVertexById(v.predecessor);
				if(v == null)
					break;
				path.add(v);
			}
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
			int cctvNum=0;
			int shelterNum=0;
			int convenienceNum=0;
			double averageWidth=0;
			double averageBrightness=0;
			
			int adultEntNum=0;
			int constructionNum=0;
			
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
				else if(nodeName.equals("cctvNum")) {
					cctvNum = Integer.parseInt(snode.getFirstChild().getTextContent());
				}
				else if(nodeName.equals("shelterNum")) {
					shelterNum = Integer.parseInt(snode.getFirstChild().getTextContent());
				}
				else if(nodeName.equals("convNum")) {
					convenienceNum = Integer.parseInt(snode.getFirstChild().getTextContent());
				}
				else if(nodeName.equals("width")) {
					averageWidth = Double.parseDouble(snode.getFirstChild().getTextContent());
				}
				else if(nodeName.equals("brightness")) {
					averageBrightness = Double.parseDouble(snode.getFirstChild().getTextContent());
				}
				else if(nodeName.equals("adultEntNum")) {
					adultEntNum = Integer.parseInt(snode.getFirstChild().getTextContent());
				}
				else if(nodeName.equals("constructNum")) {
					constructionNum = Integer.parseInt(snode.getFirstChild().getTextContent());
				}
			}
			MapEdge me = new MapEdge(adjacentNodes, length, cctvNum, shelterNum, convenienceNum, averageWidth, averageBrightness, adultEntNum, constructionNum);
			addEdge(me);
			
		}
	}
	
}
