public class Salami extends PizzaDekor {

	Salami(Pizza pizzam) {
		super(pizzam);
	}
	
	public String getDescription() {
		
		return pizzam.getDescription() + "Salami ";
	}

	@Override
	public double getCost() {
		
		return pizzam.getCost() + 3.00 ;
	}
	
	

}
