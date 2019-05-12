import java.util.ArrayList;

public class Query {
	
	Util myUtil = new Util();
	
	public void query1(String city1, String city2, int scalar, String transportTypeChar) {
		
		// Run DFS
		
		boolean visited[] = new boolean[Util.cityList.size()]; 
		visited[myUtil.getCityIndex(city1)] = true;
		
        	ArrayList<String> pathList = new ArrayList<>(); 

		pathList.add(city1);
		
		int counter = 0;
		
		query1(city1, city2, scalar, transportTypeChar, visited, pathList, counter);
	}
	
	public void query1(String city1, String city2, int scalar, String transportTypeChar, boolean visited[], ArrayList<String> localPathList, int counter) {
		
		int city1Index = myUtil.getCityIndex(city1);
		
		visited[city1Index] = true;
		
		if(city1.equals(city2) && counter < scalar) {visited[city1Index] = false;return;}
		
		if(city1.equals(city2) && counter >= scalar) {System.out.println(localPathList.toString().replace("[","").replace("]","")); visited[city1Index] = false;return;}
		
		for(Edge e : Util.cityObjectList.get(city1Index).connections) {
			
			if(e != null && visited[myUtil.getCityIndex(e.end.name)] == false) {
				
				if(e.type.charAt(0) == transportTypeChar.charAt(0)) {counter++;}
				
				String cityName = e.end.name;
				
				localPathList.add(Character.toString(e.type.charAt(0)));
				localPathList.add(cityName);
				query1(cityName, city2, scalar, transportTypeChar, visited, localPathList, counter);
				
                		localPathList.remove(cityName);
                		localPathList.remove(localPathList.size() - 1);
                

                if(e.type.charAt(0) == transportTypeChar.charAt(0)) {counter--;}
			}
			
		}

		visited[city1Index] = false;
	}
	
	public void query2(String start, String middle, String end) {
		
		boolean visited[] = new boolean[Util.cityList.size()]; 
		visited[myUtil.getCityIndex(start)] = true;
		
        	ArrayList<String> pathList = new ArrayList<>(); 

		pathList.add(start);
		
		query2(start, middle, end, visited, pathList);
	}
	
	public void query2(String start, String middle, String end, boolean visited[], ArrayList<String> localPathList) {
		
		int startIndex = myUtil.getCityIndex(start);
		int middleIndex = myUtil.getCityIndex(middle);
		
		visited[startIndex] = true;
		
		if(start.equals(end) && visited[middleIndex] == true) {System.out.println(localPathList.toString().replace("[","").replace("]",""));visited[startIndex] = false;return;}
		
		if(start.equals(end) && visited[middleIndex] == false) {visited[startIndex] = false;return;}
		
		for(Edge e : Util.cityObjectList.get(startIndex).connections) {
			
			if(e != null && visited[myUtil.getCityIndex(e.end.name)] == false) {
				
				String cityName = e.end.name;
					
				localPathList.add(Character.toString(e.type.charAt(0)));
				localPathList.add(cityName);
				query2(cityName, middle, end, visited, localPathList);
				
                		localPathList.remove(cityName);
                		localPathList.remove(localPathList.size() - 1);
                
			}
			
		}

		visited[startIndex] = false;
	}
	
	public void query3(String start, String end, char transportType) {
		
		boolean visited[] = new boolean[Util.cityList.size()];
		
		visited[myUtil.getCityIndex(start)] = true;
		
        	ArrayList<String> pathList = new ArrayList<>(); 

		pathList.add(start);
		
		query3(start, end, transportType, visited, pathList);
	}
	
	public void query3(String start, String end, char transportType, boolean visited[], ArrayList<String> localPathList) {
		
		int startIndex = myUtil.getCityIndex(start);
		
		visited[startIndex] = true;
		
		if(start.equals(end)) {System.out.println(localPathList.toString().replace("[","").replace("]",""));visited[startIndex] = false;return;}
		
		for(Edge e : Util.cityObjectList.get(startIndex).connections) {
			
			if(e != null && visited[myUtil.getCityIndex(e.end.name)] == false && e.type.charAt(0) == transportType) {
				
				String cityName = e.end.name;
					
				localPathList.add(Character.toString(e.type.charAt(0)));
				localPathList.add(cityName);
				query3(cityName, end, transportType, visited, localPathList);
				
                		localPathList.remove(cityName);
                		localPathList.remove(localPathList.size() - 1);
                
			}
			
		}

		visited[startIndex] = false;
	}
	
	public void query4(String start, String end, int airway, int highway, int railway) {
		
		boolean visited[] = new boolean[Util.cityList.size()];
		
		visited[myUtil.getCityIndex(start)] = true;
		
        	ArrayList<String> pathList = new ArrayList<>(); 

		pathList.add(start);
		
		int airwayCounter = 0, highwayCounter = 0, railwayCounter = 0;
		
		query4(start, end, airwayCounter, highwayCounter, railwayCounter, airway, highway, railway, visited, pathList);
	}
	
	public void query4(String start, String end, int airwayCounter, int highwayCounter, int railwayCounter, int airway, int highway, int railway, boolean visited[], ArrayList<String> localPathList) {
		
		int startIndex = myUtil.getCityIndex(start);
		
		visited[startIndex] = true;
		
		if(start.equals(end) && airwayCounter == airway && railwayCounter == railway && highwayCounter == highway) {System.out.println(localPathList.toString().replace("[","").replace("]",""));visited[startIndex] = false;return;}
		
		else if(start.equals(end) && (airwayCounter != airway || railwayCounter != railway || highwayCounter != highway)) {visited[startIndex] = false;return;}
		
		for(Edge e : Util.cityObjectList.get(startIndex).connections) {
			
			if(e != null && visited[myUtil.getCityIndex(e.end.name)] == false) {
				
				if(e.type.charAt(0) == 'H') {highwayCounter++;}
				else if(e.type.charAt(0) == 'A') {airwayCounter++;}
				else if(e.type.charAt(0) == 'R') {railwayCounter++;}
				
				String cityName = e.end.name;
				
				localPathList.add(Character.toString(e.type.charAt(0)));
				localPathList.add(cityName);
				query4(cityName, end, airwayCounter, highwayCounter, railwayCounter, airway, highway, railway, visited, localPathList);
				
                		localPathList.remove(cityName);
                		localPathList.remove(localPathList.size() - 1);
                
				if(e.type.charAt(0) == 'H') {highwayCounter--;}
				else if(e.type.charAt(0) == 'A') {airwayCounter--;}
				else if(e.type.charAt(0) == 'R') {railwayCounter--;}
                
			}
			
		}

		visited[startIndex] = false;
	}

}
