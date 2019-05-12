public class Neapolitan extends PizzaDekor {

	Neapolitan(Pizza pizzam) {
		super(pizzam);
		// TODO Auto-generated constructor stub
	}
	
	public String getDescription() {
		
		return pizzam.getDescription() + "Neapolitan ";
	}

	@Override
	public double getCost() {
		
		return pizzam.getCost() + 10.00 ;
	}

}
