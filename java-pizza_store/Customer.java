import java.util.Comparator;

public class Customer implements Comparable<Customer> {
	
	// Private variables and getter/setter methods of Customer class

	private int customer_id;
	private String other_info;
	
	// Constructor

	Customer(int customer_id, String other_info) {
		this.customer_id = customer_id;
		this.other_info = other_info;
	}

	public int getId() {
		return customer_id;
	}

	public void setId(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getOther_info() {
		return other_info;
	}

	public void setOther_info(String other_info) {
		this.other_info = other_info;
	}
	
	// Method which compares Customer objects according to their names

	public static Comparator<Customer> CustomerNameComparator = new Comparator<Customer>() {

		public int compare(Customer customer1, Customer customer2) {

			String customerName1 = customer1.getOther_info().toUpperCase();
			String customerName2 = customer2.getOther_info().toUpperCase();

			return customerName1.compareTo(customerName2);
		}

	};

	@Override
	
	// Method which compares Customer objects according to their IDs
	
	public int compareTo(Customer compare_customer) {
		
		int compareQuantity = ((Customer) compare_customer).getId(); 
		

		return this.getId() - compareQuantity;

		
	}
}
