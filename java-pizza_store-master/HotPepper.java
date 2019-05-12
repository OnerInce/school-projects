public class HotPepper extends PizzaDekor {

	HotPepper(Pizza pizzam) {
		super(pizzam);
		// TODO Auto-generated constructor stub
	}
	
	public String getDescription() {
		
		return pizzam.getDescription() + "HotPepper ";
	}

	@Override
	public double getCost() {
		
		return pizzam.getCost() + 1.00 ;
	}
}
