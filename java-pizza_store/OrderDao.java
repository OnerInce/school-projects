import java.util.List;

// Interface of Order class for DAO

public interface OrderDao {
	
	public List<Order> getAllOrders();
	public void createOrder(int orderID, int customerID, String order_info, double cost);
	public void removeOrder(int orderID);
	public void payCheck(int orderID, boolean drink);

}
