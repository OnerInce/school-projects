import java.io.*;
import java.util.*;

public class OrderDaoImpl implements OrderDao{
	
	//Array of Orders
	
	public List<Order> Orders = new ArrayList<Order>();

	@Override
	
	// Method to remove an order
	
	public void removeOrder(int orderID) {
			for (int y=0; y<Orders.size(); y++) {
			
			if (Orders.get(y).getOrder_id() == orderID) {
				
				Orders.remove(y);
			}
		}
	}

	@Override
	
	//Method to calculate and print the details of an order, checks for a drink as input
	
	public void payCheck(int orderID, boolean drink) {
		
		int total_price = 0;
		// TODO Auto-generated method stub
			for (int z=0; z<Orders.size(); z++) {
			
			if (Orders.get(z).getOrder_id() == orderID) {
				
				for (String info : Orders.get(z).getOrder_info_list()) {
					
					System.out.print("\t" + info);

					System.out.println((int)Orders.get(z).getOrder_cost() + "$");

					total_price += (int)Orders.get(z).getOrder_cost();
					
				}
		 }
			
	}
			//If there is a drink following lines will execute
			
				if(drink) {
						
						total_price += 1;
						System.out.println("\t" + "SoftDrink 1$");
						
					}
				
				System.out.println("\tTotal : " + total_price + "$");

		
	}
	
	@Override
	public List<Order> getAllOrders() {
		
		System.out.println("Order List:");

		Collections.sort(Orders);
		return Orders;
	}


	// Method to create an order, take information as parameter also adds order to the list
	
	public void createOrder(int orderID, int customerID, String order_info, double order_cost) {
		
		Order newOrder = new Order(orderID, customerID, order_info, order_cost);
		
		Orders.add(newOrder);
		
	}

}
