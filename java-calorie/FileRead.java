import java.io.*;

public class FileRead {
	
	// Arrays of objects to store information

	static People[] personS = new People[50];
	static Food[] foodS = new Food[100];
	static Sport[] sportS = new Sport[100];
	static int[] person_array = new int[100];
	static int[] stored_ids = new int[100];

	public void FileHandle(String file_type) throws NumberFormatException, IOException {
		
		Main a = new Main();
		
		String outum = a.savedArg; // Save command line argument

		if (file_type.equals("people")) {
			
			// Reading and processing people.txt

			int count = 0; // Counter for each person

			FileReader fr = new FileReader("people.txt");

			BufferedReader br = new BufferedReader(fr);

			String line = null;

			while ((line = br.readLine()) != null) {

				for (String retval : line.split("\n")) {
					
					String[] mylist = retval.split("\t");
					
					// Parsing and getting datas from each line

					int resultt = Integer.parseInt(mylist[0]);
					int id = resultt;

					String name = mylist[1];
					String gender = mylist[2];

					int result2 = Integer.parseInt(mylist[3]);
					int weight = result2;

					int result3 = Integer.parseInt(mylist[4]);
					int height = result3;

					int result4 = Integer.parseInt(mylist[5]);
					int birth_year = result4;
					
					// Create a new object from People class and add it to the array

					People newPeople = new People(id, name, gender, weight, height, birth_year);

					personS[count] = newPeople;

					count++;
				}
			}
			
			// Close the file

			br.close();
			fr.close();
		}

		else if (file_type.equals("food")) {
			
			// Reading and processing food.txt

			int count2 = 0; // Counter for each food

			FileReader fr2 = new FileReader("food.txt");

			BufferedReader br2 = new BufferedReader(fr2);

			String line2 = null;

			while ((line2 = br2.readLine()) != null) {

				for (String retval2 : line2.split("\n")) {
					
					String[] mylist2 = retval2.split("\t");
					
					// Parsing and getting datas from each line

					int resultt = Integer.parseInt(mylist2[0]);
					int id = resultt;

					String name = mylist2[1];

					int result2 = Integer.parseInt(mylist2[2]);
					int calorie = result2;
					
					// Create a new object from Food class and add it to the array

					Food newfood = new Food(id, name, calorie);

					foodS[count2] = newfood;

					count2++;

				}
			}
			
			// Close the file
			
			br2.close();
			fr2.close();
		}

		else if (file_type.equals("sport")) {

			int count3 = 0; // Counter for each sport

			FileReader fr3 = new FileReader("sport.txt");

			BufferedReader br3 = new BufferedReader(fr3);

			String line3 = null;

			while ((line3 = br3.readLine()) != null) {

				for (String retval3 : line3.split("\n")) {
					
					String[] mylist3 = retval3.split("\t");
					
					// Parsing and getting datas from each line

					int resultt = Integer.parseInt(mylist3[0]);
					int id = resultt;

					String name = mylist3[1];

					int result2 = Integer.parseInt(mylist3[2]);
					int calorie = result2;
					
					// Create a new object from Sport class and add it to the array

					Sport newsport = new Sport(id, name, calorie);

					sportS[count3] = newsport;

					count3++;

				}
			}
			
			// Close the file

			br3.close();
			fr3.close();

		}

		else if (file_type.equals("input")) {
			
			// Create the output file
			
			File output = new File("monitoring.txt");
			
			FileWriter monitor = new FileWriter(output);

			int count4 = 0; // Counter for the people to store from input file
			
			int count5 = 0; // Counter for the people to print

			int total_calorie = 0;

			String name_of_job = null;

			FileReader fr4 = new FileReader(outum);

			BufferedReader br4 = new BufferedReader(fr4);

			String line4 = null;

			while ((line4 = br4.readLine()) != null) {
				
				if (count4 != 0) {
					
					monitor.write("***************");
					monitor.write("\n");
				}


				for (String retval4 : line4.split("\n")) {

					if (retval4.contains("print") == false) {

						String[] mylist4 = retval4.split("\t");
						
						// Parsing and getting datas from each line of command.txt

						int resultt = Integer.parseInt(mylist4[0]);
						int person_id = resultt;
						
						// Storing person IDs to an array

						person_array[count4] = person_id;

						int result2 = Integer.parseInt(mylist4[1]);
						int job_id = result2;

						int result3 = Integer.parseInt(mylist4[2]);
						int amount_of_job = result3;

						if (job_id < 2000) {

							for (int z = 0; z < foodS.length; z++) {

								if (foodS[z] != null && foodS[z].foodID == job_id) {
									
									// Calculating total calorie of the eating

									total_calorie = (int) Math.round(foodS[z].calorieCount * amount_of_job);

									name_of_job = foodS[z].nameOfFood;
								}

							}

							for (int h = 0; h < personS.length; h++) {
								
								// Update the specific person's caloriesTaken info

								if (personS[h] != null && personS[h].personID == person_id) {

									personS[h].caloriesTaken += total_calorie;
								}
							}
							
							// Writing outputs to the file
							
							monitor.write(person_id + "\thas\ttaken\t" +total_calorie+ "kcal\tfrom\t" + name_of_job);
							monitor.write("\n");
						}

						else if (job_id > 2000) {

							for (int r = 0; r < sportS.length; r++) {

								if (sportS[r] != null && sportS[r].sportID == job_id) {
									
									// Calculating total calorie of the sport activity

									total_calorie = (int) (sportS[r].calorieBurned * (amount_of_job / (double)(60)));

									name_of_job = sportS[r].nameOfSport;
								}

							}

							for (int h = 0; h < personS.length; h++) {
								
								// Updating specific person's calorieBurned info

								if (personS[h] != null && personS[h].personID == person_id) {

									personS[h].caloriesBurned += total_calorie;
								}
							}
							
							// Writing outputs to the file
							
							monitor.write(person_id + "\thas\tburned\t" +total_calorie+ "kcal\tthanks\tto\t" + name_of_job);
							monitor.write("\n");
						}

					}

					else if (retval4.contains("print") == true) {

						if (retval4.contains("List") == false) {
							
							// Printing the infos of single person

							String parsedString = retval4.substring(retval4.indexOf("(") + 1, retval4.indexOf(")")); // Getting person ID to print

							int print_id = Integer.parseInt(parsedString);

							for (int m = 0; m < personS.length; m++) {

								if (personS[m] != null && personS[m].personID == print_id) {
									
									monitor.write(String.valueOf(personS[m]));
									monitor.write("\n");
								}
							}

						}

						if (retval4.contains("List") == true) {
							
							// Printing the infos of all given people

							for (int p = 0; p < count4; p++) {

								for (int h = 0; h < personS.length; h++) {
									
									// Check for being null and for duplicate data

									if (personS[h] != null && person_array[p] == personS[h].personID && ( contains(stored_ids,person_array[p])) == false) {
										
										monitor.write(String.valueOf(personS[h]));
										monitor.write("\n");
										
										stored_ids[count5] = person_array[p];
										
										count5++;
									}
								}
							}

						}
					}
				}
				count4++;
			}
			
			// Close the files

			br4.close();
			fr4.close();
			monitor.close();
		}
	}
	
	// Method to check if an array contains a specific value or not

	public boolean contains(final int[] array, final int v) {

		boolean result = false;

		for (int i : array) {
			if (i == v) {
				result = true;
				break;
			}
		}

		return result;
	}

}
