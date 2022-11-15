package me.lagbug.giveaways.global.enums;

public enum Phase {
	ZERO(0), 
	ONE(1), 
	TWO(2), 
	THREE(3), 
	FOUR(4),
	FIVE(5);
	
	private final int number;
	
    private Phase(int number) {
        this.number = number;
    }
    
    public int getNumber() {
    	return number;
    }
}
