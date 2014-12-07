package gameLogic;

public class Game {
	public void Initialise() {
		PlayerManager playerManager = new PlayerManager();
		playerManager.InitialisePlayers(2);
	}
}
