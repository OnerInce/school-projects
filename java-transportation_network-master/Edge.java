public class Edge {
	
	City start;
	City end;
	String type; // Railway or Airway or Highway
	
	// Creating a new edge
	
    public Edge(String source, String destination, String type) {
        
    	this.start = new City(source);
        this.end = new City(destination);
        this.type = type;
        
    }
    
    public String toString() {
    	
    	return end.name;
    	
    }

}
