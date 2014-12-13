package gameLogic;

import gameLogic.goal.GoalManager;
import gameLogic.resource.ResourceManager;

public class Game {
	private static Game instance;
	private PlayerManager playerManager;
	private GoalManager goalManager;
	private ResourceManager resourceManager;

	private final int CONFIG_PLAYERS = 2;

	private Game() {
		playerManager = new PlayerManager();
		playerManager.initialisePlayers(CONFIG_PLAYERS);

		goalManager = new GoalManager();
		resourceManager = new ResourceManager();
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}

		return instance;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public GoalManager getGoalManager() {
		return goalManager;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}
}
