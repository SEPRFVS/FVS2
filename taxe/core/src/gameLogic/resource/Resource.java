package gameLogic.resource;

import gameLogic.Disposable;
import gameLogic.Player;

public abstract class Resource implements Disposable {
	protected String name;
	private Player player;

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isOwnedBy(Player player) {
		return player == this.player;
	}

	@Override
	public String toString() {
		return name;
	}

	protected void changed() {
		player.changed();
	}
}