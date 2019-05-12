import java.io.*;
import java.util.*;

public class CustomerDaoImpl implements CustomerDao {
	
	public List<Customer> Customers = new ArrayList<Customer>(); // Array of Customer objects

	@Override
	
	// Method to list all customers, checks the compare according to name or ID, if true sorts to names
	
	public List<Customer> getAllCustomers(boolean check) {
		
		if(check == true) {
			
			System.out.println("Customer List:");
			Collections.sort(Customers, Customer.CustomerNameComparator);
			
		}
		
		else if (check == false) {
			Collections.sort(Customers);
			
		}
		
		return Customers;
	}

	@Override
	
	// Method to delete customer
	public void deleteCustomer(int id) {
		
		for (int x=0; x<Customers.size(); x++) {
			
			if (Customers.get(x).getId() == id) {
				
				Customers.remove(x);
			}
			
		}
		
	}
	
	//Method to get a customer's name from its ID
	
	public String id_to_name(int id) {
		
		String all_info = null;
		
		String name = null;
		
		for (int x=0; x<Customers.size(); x++) {
			
			if (Customers.get(x).getId() == id) {
				
				all_info =  Customers.get(x).getOther_info();
				
				name = all_info.split((" "), 2)[0];
				break;
			}
			
		}
		return name;
		
	}

	@Override
	// Method to create a customer and then add it to the list
	
	public void addCustomer(int customerID, String other_info) {
		
		Customer newCustomer = new Customer(customerID, other_info);
		
		Customers.add(newCustomer);
		
	}

	
}
