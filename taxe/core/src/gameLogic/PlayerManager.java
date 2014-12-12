package gameLogic;

import java.util.ArrayList;

public class PlayerManager {
	private ArrayList<Player> players = new ArrayList<Player>();
	private int currentTurn = 0;
	
	public void initialisePlayers(int count) {
		for (int i = 0; i < count; i++) {
			players.add(new Player());
		}
	}
	
	public Player getCurrentPlayer() {
		return players.get(currentTurn);
	}
	
	public void turnOver() {
		currentTurn = currentTurn == 1 ? 0 : 1;
	}
}
