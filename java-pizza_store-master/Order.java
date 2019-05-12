import java.util.ArrayList;
import java.util.List;

public class Order implements Comparable<Order> {
	
	// Private variables and getter/setter methods of Order class
	
	private int order_id;
	private int customer_id;
	private List<String> order_info_list = new ArrayList<String>();
	private String order_info;
	private double order_cost;
	
	//Constructor
	
	Order(int order_id, int customer_id, String order_info, double order_cost) {
		this.customer_id = customer_id;
		this.order_id = order_id;
		this.order_info = order_info;
		this.order_cost = order_cost;
		order_info_list.add(order_info);
	}
	
	public List<String> getOrder_info_list() {
		return order_info_list;
	}

	public void setOrder_info_list(List<String> order_info_list) {
		this.order_info_list = order_info_list;
	}

	public String getOrder_info() {
		return order_info;
	}

	public void setOrder_info(String order_info) {
		this.order_info = order_info;
	}
	
	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}	
	
	public double getOrder_cost() {
		return order_cost;
	}

	public void setOrder_cost(int order_cost) {
		this.order_cost = order_cost;
	}
	
	//Method to compare orders according to their IDs
	
	public int compareTo(Order compareOrder) {
		
		int compareID = ((Order) compareOrder).getOrder_id(); 
		
		return this.getOrder_id() - compareID;
		
	}
}
