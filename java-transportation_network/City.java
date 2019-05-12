import java.util.ArrayList;

public class City {
	
	// City object where every city have a list of outgoing edges
	
	String name;
	ArrayList<Edge> connections = new ArrayList<>();
	
	public City(String name) {
		
		this.name = name;
		
	}
	
	// Printing adjacent cities with edge information
	
	public String toString() {
		
		String toReturn = "";
		
		toReturn += name + " --> ";
		for(Edge e : connections) {
			
			if(!toReturn.contains(e.end.name)) {
				toReturn += e;
				toReturn += " ";
			}
		}
		return toReturn;
		
	}

}
