public class Soudjouk extends PizzaDekor {

	Soudjouk(Pizza pizzam) {
		super(pizzam);
		// TODO Auto-generated constructor stub
	}
	
	public String getDescription() {
		
		return pizzam.getDescription() + "Soudjouk ";
	}

	@Override
	public double getCost() {
		
		return pizzam.getCost() + 3.00 ;
	}

}
