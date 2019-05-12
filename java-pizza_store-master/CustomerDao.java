import java.util.List;

// Customer interface for DAO

public interface CustomerDao {
	
	public List<Customer> getAllCustomers(boolean check);
	public void addCustomer(int customerID, String other_info);
	public void deleteCustomer(int id);
	public String id_to_name(int id);

}
