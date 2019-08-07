import java.util.*;

public class SafeAStarSearch {
	
	MapGraph mg = new MapGraph();
	
	private Queue<MapGraph.MapVertex> frontier = new PriorityQueue<>();
    
    private List<String> explored = new ArrayList<>();
    
    public void AStarSearch(String startId, String endId) {
    	
    	MapGraph.MapVertex v = mg.findVertexById(startId);
    	MapGraph.MapVertex endV = mg.findVertexById(endId);
    	
    	v.updateCost(mg.getDistance(v, endV), null);
    	frontier.add(v);
    	
    	while(true) {
    		
    		if(frontier.isEmpty()) return;
    		
    		v = frontier.remove();
    		if(v.getId().equals(endId)) return;
    		explored.add(v.getId());
    		
    		for(MapGraph.MapVertex u : v.getNeighbors()) {
    			if(!frontier.contains(u) && !explored.contains(u.getId())) {
    				// 큐에서도 업데이트가 제대로 될까?
    				u.updateCost(v.getCost() + mg.getEdgeCost(v, u) + mg.getDistance(u, endV), v.getId());
    				frontier.add(u);
    			}
    			else if(frontier.contains(u) && u.getCost() > v.getCost() + mg.getEdgeCost(v, u) + mg.getDistance(u, endV)) {
    				u.updateCost(v.getCost() + mg.getEdgeCost(v, u) + mg.getDistance(u, endV), v.getId());
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
