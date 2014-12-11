package gameLogic;

import gameLogic.goal.GoalManager;

public class Game {
	private static Game instance = null;
	private PlayerManager playerManager = null;
	private GoalManager goalManager = null;

	private final int CONFIG_PLAYERS = 2;

	private Game() {
		Initialise();
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}

		return instance;
	}

	private void Initialise() {
		playerManager = new PlayerManager();
		playerManager.InitialisePlayers(CONFIG_PLAYERS);

		goalManager = new GoalManager();
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public GoalManager getGoalManager() {
		return goalManager;
	}
}
