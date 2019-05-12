import java.io.*;
import java.util.*;


public class Util {
	
	static ArrayList<String> cityList = new ArrayList<>();
	static ArrayList<City> cityObjectList = new ArrayList<>();
	
	int numberOfCities = -1;
	
	public void FileRead(String type) {
		
		if(type.equals("network")) {
		
		FileReader fr = null, fr2 = null;
		try {
			fr = new FileReader(main.myArgs[0]);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}

		BufferedReader br1 = new BufferedReader(fr);

		String line = null, currentWay = null;
		
			try {
				
				// Find number of Cities
				
				while ((line = br1.readLine()) != null) {
					
					if(line.length() < 3) {break;}
					
					if(line.contains("Airway") || line.contains("Railway") || line.contains("Highway") && numberOfCities == 0) {
						
						numberOfCities++;
						
						continue;
						
					}
					
					else if(!line.contains("Airway") || !line.contains("Railway") || !line.contains("Highway") && line.length() > 3) {

						String splittedLine[] = line.split(" ",2);
						numberOfCities++;
						cityList.add(splittedLine[0]);
						
						continue;
						
					}						
					
					if(numberOfCities != -1) {numberOfCities++;}
					
			}
				
				for(int i = 0 ; i < numberOfCities; i++) {
					
					cityObjectList.add(new City(cityList.get(i)));
					
				}
			
			} catch (IOException e) {
				
				e.printStackTrace();
			}

			try {
				fr2 = new FileReader(main.myArgs[0]);
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			
			BufferedReader br2 = new BufferedReader(fr2);
			
			
			try {
				
				// Line-by-line execute queries
				
				while ((line = br2.readLine()) != null) {
					
					if(line.contains("Airway") || line.contains("Railway") || line.contains("Highway")) {
						
						currentWay = line;
						
					}
					
					else if((!line.contains("Airway") && !line.contains("Railway") && !line.contains("Highway")) && line.length() > 4) {
						
						String splittedLine[] = line.split(" ",2);
						
						for(int m = 0; m < numberOfCities; m++) {
							
							if(Character.getNumericValue(splittedLine[1].charAt(m)) == 1) {
								
								Edge myEdge = new Edge(splittedLine[0], getCityName(m), currentWay); // Create Edge
								for(City c : cityObjectList) {
									
									if((c.name).equals(splittedLine[0])) {
										
										c.connections.add(myEdge);
										
									}
									
								}
								
							}
							
						}
						
						
					}
					
				}
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		
		else if(type.equals("query")) {
			
			Query myQuery = new Query();
			
			FileReader fr = null;
			try {
				fr = new FileReader(main.myArgs[1]);
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}

			BufferedReader br1 = new BufferedReader(fr);
			
			String line = null;
			
			try {
				while ((line = br1.readLine()) != null) {
					
					if(line.contains("Q1")) {
									
						String splittedLine[] = line.split(" ",5);
						
						System.out.println(Arrays.toString(splittedLine).replace("[","").replace("]",""));
						
						String city1 = splittedLine[1], city2 = splittedLine[2], transportTypeChar = splittedLine[4];
						
						int scalar = Integer.parseInt(splittedLine[3]);
						
						myQuery.query1(city1, city2, scalar, transportTypeChar);
										
					}
					
					else if(line.contains("Q2")) {
						
						String splittedLine[] = line.split(" ",4);
						
						System.out.println(Arrays.toString(splittedLine).replace("[","").replace("]",""));
						
						String start = splittedLine[1], end = splittedLine[2], middle = splittedLine[3];
						
						myQuery.query2(start, middle, end);
						
					}
					
					else if(line.contains("Q3")) {
						
						String splittedLine[] = line.split(" ",4);
						
						System.out.println(Arrays.toString(splittedLine).replace("[","").replace("]",""));
						
						String start = splittedLine[1], end = splittedLine[2], transportTypeChar = splittedLine[3];
						
						myQuery.query3(start, end, transportTypeChar.charAt(0));
						
					}
					
					else if(line.contains("Q4")) {
						
						String splittedLine[] = line.split(" ",6);
						
						System.out.println(Arrays.toString(splittedLine).replace("[","").replace("]",""));
						
						String start = splittedLine[1], end = splittedLine[2], airway = splittedLine[3], highway = splittedLine[4], railway = splittedLine[5];
						
						int airwayCount = Character.getNumericValue(airway.charAt(1)), highwayCount = Character.getNumericValue(highway.charAt(1)), railwayCount = Character.getNumericValue(railway.charAt(1));
						
						myQuery.query4(start, end, airwayCount, highwayCount, railwayCount);
						
					}
					
					else if(line.contains("PRINTGRAPH")) {
						
						System.out.println("PRINTGRAPH");
						
						for(City c: cityObjectList) {
							
							System.out.println(c);
							
						}
						
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public int getCityIndex(String cityName) {

		for(String s : cityList) {
			
			if(s.contains(cityName)) {return cityList.indexOf(s);}
			
		}
		
		return -1;
		
	}
	
	public String getCityName(int cityIndex) {
		
			return cityList.get(cityIndex);
			
	}

}
