import java.util.*;

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
		
		public double getCost() {
			return length;
		}
		
		public MapVertex getAnotherVertex(MapVertex v) {
			if(this.adjacentNodes[0].equals(v.getId())) return findVertexById(adjacentNodes[1]);
			else if(this.adjacentNodes[1].equals(v.getId())) return findVertexById(adjacentNodes[0]);
			else return null;
		}
	}
	
	private Set<MapVertex> vertices = new HashSet<>();
	private Set<MapEdge> edges = new HashSet<>();

	public void addVertex(String id, String name, int x, int y, boolean isIntersection) {
		vertices.add(new MapVertex(id, name, x, y, isIntersection));
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
	
	public MapEdge findEdgeById(String id) {
		for(MapEdge e : edges) {
			if(e.id.equals(id)) {
				return e;
			}
		}
		return null;
	}
	
	public double getDistance(MapVertex v, MapVertex u) {
		return Math.sqrt((v.x-u.x)*(v.x-u.x)+(v.y-u.y)*(v.y-u.y));
	}
	
	public double getEdgeCost(MapVertex v, MapVertex u) {
		for(MapEdge e : edges) {
			if(findVertexById(e.adjacentNodes[0]).equals(v) && findVertexById(e.adjacentNodes[1]).equals(u) ||
					findVertexById(e.adjacentNodes[0]).equals(u) && findVertexById(e.adjacentNodes[1]).equals(v))
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
}
