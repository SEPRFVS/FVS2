package gameLogic;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
	private ArrayList<Player> players = new ArrayList<Player>();
	private int currentTurn = 0;
	private List<TurnListener> turnListeners = new ArrayList<TurnListener>();
	private List<PlayerChangedListener> playerListeners = new ArrayList<PlayerChangedListener>();
	
	public void initialisePlayers(int count) {
		for (int i = 0; i < count; i++) {
			players.add(new Player(this));
		}
	}
	
	public Player getCurrentPlayer() {
		return players.get(currentTurn);
	}
	
	public void turnOver() {
		currentTurn = currentTurn == 1 ? 0 : 1;
		turnChanged();
		playerChanged();
	}


	public void subscribeTurnChanged(TurnListener listener) {
		turnListeners.add(listener);
	}

	private void turnChanged() {
		for(TurnListener listener : turnListeners) {
			listener.changed();
		}
	}

	public void subscribePlayerChanged(PlayerChangedListener listener) {
		playerListeners.add(listener);
	}

	// very general event which is fired when player's goals / resources are changed
	public void playerChanged() {
		for (PlayerChangedListener listener : playerListeners) {
			listener.changed();
		}
	}
}
