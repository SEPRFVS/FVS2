package uk.ac.york.cs.sepr.fvs.taxe;

public interface IPositionable {
	
	public int xCoord = 0;
	public int yCoord = 0;
	
    public int getXCoordinate();
    public int getYCoordinate();
    public void setXCoordinate();
    public void setYCoordinate();
}