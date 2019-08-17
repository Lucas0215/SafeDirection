import java.util.*;

public class SafeAStarSearch {
	
	private MapGraph mg = null;
	
	private Queue<MapGraph.MapVertex> frontier = new PriorityQueue<>();
    
    private List<String> explored = new ArrayList<>();
    
    public SafeAStarSearch(MapGraph mg) {
    	this.mg = mg;
    }
    
    public List<MapGraph.MapVertex> AStarSearch(String startId, String endId) {
    	
    	MapGraph.MapVertex startv = mg.findVertexById(startId);
    	MapGraph.MapVertex endv = mg.findVertexById(endId);
    	
    	mg.initCosts();
    	
    	startv.updateCost(0, mg.getDistance(startv, endv), null);
    	frontier.add(startv);
    	
    	while(true) {
    		
    		if(frontier.isEmpty()) return null;
    		
    		MapGraph.MapVertex v = frontier.remove();
			System.out.println(v.getCost());
    		if(v.getId().equals(endId)) {
    			System.out.println(v.getCost());
    			return mg.constructPath(endId);
    		}
    		explored.add(v.getId());
    		
    		for(MapGraph.MapVertex u : v.getNeighbors()) {
    			if(!frontier.contains(u) && !explored.contains(u.getId())) {
    				double cost = v.getCost() + mg.getEdgeCost(v, u);
    				u.updateCost(cost, mg.getDistance(u, endv), v.getId());
    				frontier.add(u);
    			}
    			else if(frontier.contains(u) && u.getCost() > v.getCost() + mg.getEdgeCost(v, u)) {
    				frontier.remove(u);
    				double cost = v.getCost() + mg.getEdgeCost(v, u);
    				u.updateCost(cost, mg.getDistance(u, endv), v.getId());
    				frontier.add(u);
    			}
    		}
    	}
    }

}