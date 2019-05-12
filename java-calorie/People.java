public class People {

	int personID;
	String name;
	String gender;
	int weight;
	int height;
	int dateOfBirth;
	int dailyCalorieNeeds;
	int age;
	int caloriesTaken;
	int caloriesBurned;
	int result;
	
	// Constructor for People class
	
	public People(int id, String isim, String cinsiyet, int kilo, int boy, int dogum) {
		
		personID = id;
		name = isim;
		gender = cinsiyet;
		weight = kilo;
		height = boy;
		dateOfBirth = dogum;
		age = 2018 - dateOfBirth;
		
		// Calculate dailyCalorieNeeds for each person
		
		if ( gender.equals("male")) {
			
			dailyCalorieNeeds = (int) Math.round(66 + (13.75 * weight) + (5 * height) - (6.8 * (2018 - dateOfBirth)));
		}
		
		else if ( gender.equals("female")) {
			
			dailyCalorieNeeds = (int)Math.round (665 + (9.6 * weight) + (1.7 * height) - (4.7 * (2018 - dateOfBirth)));
		}
		
	
	}
	
	// Printing format for each object in this Class
	
	public String toString() {
		
		result = -dailyCalorieNeeds + caloriesTaken - caloriesBurned;
        
		return String.format("%s\t%d\t%dkcal\t%dkcal\t%dkcal\t%+dkcal", 
                name, age, dailyCalorieNeeds, caloriesTaken, caloriesBurned, result);
    }

}
