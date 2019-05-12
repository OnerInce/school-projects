public class Onion extends PizzaDekor {

	Onion(Pizza pizzam) {
		super(pizzam);
		// TODO Auto-generated constructor stub
	}
	
	public String getDescription() {
		
		return pizzam.getDescription() + "Onion ";
	}

	@Override
	public double getCost() {
		
		return pizzam.getCost() + 2.00 ;
	}
}
