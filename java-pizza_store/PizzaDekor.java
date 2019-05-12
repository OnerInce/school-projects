// Class which implements Pizza class for the Decorative pattern

public abstract class PizzaDekor implements Pizza {
	
	protected Pizza pizzam;
	
	PizzaDekor (Pizza pizzam){
		
		this.pizzam = pizzam;
	
	}
	
	public String getDescription() {
		
		return pizzam.getDescription();
	}

	@Override
	public double getCost() {
		
		return pizzam.getCost();
	}

}
