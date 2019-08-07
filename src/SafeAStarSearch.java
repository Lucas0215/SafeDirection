import java.util.*;

public class SafeAStarSearch {
	
	MapGraph mg = new MapGraph();
	
	private Queue<MapGraph.MapVertex> frontier = new PriorityQueue<>();
    
    private List<String> explored = new ArrayList<>();
    
    public List<MapGraph.MapVertex> AStarSearch(String startId, String endId) {
    	
    	MapGraph.MapVertex v = mg.findVertexById(startId);
    	MapGraph.MapVertex endv = mg.findVertexById(endId);
    	
    	v.updateCost(mg.getDistance(v, endv), null);
    	frontier.add(v);
    	
    	while(true) {
    		
    		if(frontier.isEmpty()) return null;
    		
    		v = frontier.remove();
    		if(v.getId().equals(endId)) {
    			return mg.constructPath(endId);
    		}
    		explored.add(v.getId());
    		
    		for(MapGraph.MapVertex u : v.getNeighbors()) {
    			if(!frontier.contains(u) && !explored.contains(u.getId())) {
    				u.updateCost(v.getCost() + mg.getEdgeCost(v, u) + mg.getDistance(u, endv), v.getId());
    				frontier.add(u);
    			}
    			else if(frontier.contains(u) && u.getCost() > v.getCost() + mg.getEdgeCost(v, u) + mg.getDistance(u, endv)) {
    				u.updateCost(v.getCost() + mg.getEdgeCost(v, u) + mg.getDistance(u, endv), v.getId());
    				frontier.remove(u);
    				frontier.add(u);
    			}
    		}
    	}
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
