package gameLogic;

import gameLogic.goal.GoalManager;
import gameLogic.resource.ResourceManager;

public class Game {
	private static Game instance;
	private PlayerManager playerManager;
	private GoalManager goalManager;
	private ResourceManager resourceManager;
	private GameState state;

	private final int CONFIG_PLAYERS = 2;

	private Game() {
		playerManager = new PlayerManager();
		playerManager.createPlayers(CONFIG_PLAYERS);

		goalManager = new GoalManager();
		resourceManager = new ResourceManager();
		state = GameState.NORMAL;

		initialisePlayers();
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}

		return instance;
	}

	/**
	 * players should start with a goal and resource
	 */
	private void initialisePlayers() {
		for (Player player : playerManager.getAllPlayers()) {
			//TODO Needs access to station list
			//goalManager.givePlayerGoal(player);
			resourceManager.addRandomResourceToPlayer(player);
		}
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

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}
}
