import java.io.*;
import java.util.*;

// Class for reading and writing to files

public class FileRead {
	
	// Create lists and variables
	
	CustomerDao CustomerDao = new CustomerDaoImpl();
	
	OrderDao OrderDao = new OrderDaoImpl();
	
	ArrayList<String> customer_list = new ArrayList<String>();
	
	ArrayList<Integer> order_list = new ArrayList<Integer>();
	
	int order_id, customer_id, total_cost;
	
	double cost, cost2;
	
	boolean add_drink;
	
	Pizza pizzam, pizzam2;
	
	public void FileHandle(String command_type) throws NumberFormatException, IOException {
		
		Main a = new Main();
		
		String my_input = a.savedArg; // Save command line argument
		
		File file = new File("output.txt"); 
		FileOutputStream fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
		
		if (command_type.equals("customer")) {
			
			// Adding customers using DAO
			
			FileReader fr = new FileReader("customer.txt");

			BufferedReader br = new BufferedReader(fr);

			String line = null;

			while ((line = br.readLine()) != null) {
				
				String customer_arr[] = line.split(" ",2);
				
				CustomerDao.addCustomer(Integer.parseInt(customer_arr[0]), customer_arr[1]);
					
			}

		}
		
		else if (command_type.equals("order")) {
			
			FileReader fr = new FileReader("src/ass3/order.txt");

			BufferedReader br = new BufferedReader(fr);

			String line = null;
			
			while ((line = br.readLine()) != null) {
				
				String[] command_line = line.split(" ",2);
				
				String command = command_line[0];
				
				String rest_of_command = command_line[1];
			}
		}
		
		else if (command_type.equals("input")) {
			
			// Opening input file and executing line-by-line
			
			FileReader fr = new FileReader(my_input);

			BufferedReader br = new BufferedReader(fr);

			String line = null;
			
			while ((line = br.readLine()) != null) {
				
				String[] command_line = line.split(" ",2);
				
				String command = command_line[0];
				
				String rest_of_command = command_line[1];
				
				//For customer commands use DAO for Customer class
				
				if (command.equals("AddCustomer")){
					
					String[] rest_of_command_arr = rest_of_command.split(" ",2);
					
					String[] name_arr = rest_of_command.split(" ",3);
					
					String name = name_arr[1];
					
					System.out.println("Customer " + rest_of_command_arr[0] + " " + name + " Added");
					
					CustomerDao.addCustomer(Integer.parseInt(rest_of_command_arr[0]), rest_of_command_arr[1]);
				}
				
				else if (command.equals("RemoveCustomer")) {
					
					int delete_id = Integer.parseInt(rest_of_command);
					
					System.out.printf("Customer %d %s removed\n",delete_id, CustomerDao.id_to_name(delete_id));
					
					CustomerDao.deleteCustomer(delete_id);
					
				}
				
				else if (command.equals("List")) {
					
					for (Customer Customer : CustomerDao.getAllCustomers(true)) 
			        {
			            System.out.println(Customer.getId() + " " +Customer.getOther_info());
			        }
					
				}
				
				else if (command.equals("CreateOrder")) {
					
					order_id = Integer.parseInt(rest_of_command.split(" ", 2)[0]);
					System.out.println("Order " + order_id + " Created");
					
					customer_id = Integer.parseInt(rest_of_command.split(" ", 2)[1]);
					
					}
				
				else if (command.equals("AddPizza")) {
					
					// Use decorative pattern to create pizzas and add toppings
					
					if (rest_of_command.split(" ", 3)[1].equals("AmericanPan")) {
						
						System.out.println("AmericanPan pizza added to order " + rest_of_command.split(" ", 2)[0]);
						
						int icerik_sayisi = rest_of_command.split(" ", 2)[1].split("\\w+").length;
						
						String[] icerik_arr = rest_of_command.split(" ", 2)[1].split(" ", icerik_sayisi);
						
						// Decorative pattern
						
						pizzam = new BosPizza();
						
						for(String x:icerik_arr) {
							
							if (x.equals("AmericanPan")) {
								
								pizzam = new AmericanPan(pizzam);
							}
							
							else if (x.equals("HotPepper")) {
								
								pizzam = new HotPepper(pizzam);
							}
							
							else if (x.equals("Onion")) {
								
								pizzam = new Onion(pizzam);
							}
							
							else if (x.equals("Salami")) {
								
								pizzam = new Salami(pizzam);
							}
							
							else if (x.equals("Soudjouk")) {
								
								pizzam = new Soudjouk(pizzam);
							}
						}
						
						OrderDao.createOrder(order_id, customer_id,pizzam.getDescription(),pizzam.getCost());
			}
					
					else if (rest_of_command.split(" ", 3)[1].equals("Neapolitan")) {
						
						System.out.println("Neapolitan pizza added to order " + rest_of_command.split(" ", 2)[0]);
						
						int icerik_sayisi = rest_of_command.split(" ", 2)[1].split("\\w+").length;
						
						String[] icerik_arr = rest_of_command.split(" ", 2)[1].split(" ", icerik_sayisi);
			
						pizzam2 = new BosPizza();
						
						for(String x:icerik_arr) {
							
							if (x.equals("Neapolitan")) {
								
								pizzam2 = new Neapolitan(pizzam2);
							}
							
							else if (x.equals("HotPepper")) {
								
								pizzam2 = new HotPepper(pizzam2);
							}
							
							else if (x.equals("Onion")) {
								
								pizzam2 = new Onion(pizzam2);
							}
							
							else if (x.equals("Salami")) {
								
								pizzam2 = new Salami(pizzam2);
							}
							
							else if (x.equals("Soudjouk")) {
								
								pizzam2 = new Soudjouk(pizzam2);
							}
						}
						
						OrderDao.createOrder(order_id, customer_id,pizzam2.getDescription(),pizzam2.getCost());

					}
				}
				
				else if (command.equals("AddDrink")) {
					
					System.out.println("Drink added to order " + rest_of_command);
					
					add_drink = true;
					
					total_cost = 2;
					
				}
				
				else if (command.equals("PayCheck")) {
					
					
					System.out.println("PayCheck for order " + rest_of_command);
					
					OrderDao.payCheck(Integer.parseInt(rest_of_command), add_drink);
					
					add_drink = false;
					
				}
				
			}
			
			// Write to the customer informations to the customer.txt
			
			File customer_output = new File("customer.txt");
			
			FileWriter customer_out = new FileWriter(customer_output);
			
			for (Customer Customer : CustomerDao.getAllCustomers(false)) 
	        {
				
				customer_out.write(Customer.getId() + " " +Customer.getOther_info());
	            customer_out.write("\n");
	            
	        }
			
			// Closing files
			br.close();
			customer_out.close();
		}

		
	}
	


}
	
