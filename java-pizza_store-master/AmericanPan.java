public class AmericanPan extends PizzaDekor {

	AmericanPan(Pizza pizzam) {
		super(pizzam);
		
	}
	
	public String getDescription() {
		
		return pizzam.getDescription() + "AmericanPan ";
	}

	@Override
	public double getCost() {
		
		return pizzam.getCost() + 5.00 ;
	}

}
