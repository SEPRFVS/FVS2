package gameLogic;

import java.util.ArrayList;

public class PlayerManager {
	private ArrayList<Player> players;
	private int currentTurn = 0;
	
	public void InitialisePlayers(int count) {
		for (int i = 0; i < count; i++) {
			players.add(new Player());
		}
	}
	
	public Player GetCurrentPlayer() {
		return players.get(currentTurn);
	}
	
	public void TurnOver() {
		currentTurn = currentTurn == 1 ? 0 : 1;
	}
}
