package gameLogic;

import gameLogic.goal.GoalManager;
import gameLogic.map.Map;
import gameLogic.resource.ResourceManager;

public class Game {
	private static Game instance;
	private PlayerManager playerManager;
	private GoalManager goalManager;
	private ResourceManager resourceManager;
	private Map map;
	private GameState state;

	private final int CONFIG_PLAYERS = 2;

	private Game() {
		playerManager = new PlayerManager();
		playerManager.createPlayers(CONFIG_PLAYERS);

		goalManager = new GoalManager();
		resourceManager = new ResourceManager();
		map = new Map();
		
		state = GameState.NORMAL;
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
			// initialisePlayers gives them a goal, and the GoalManager requires an instance of game to exist so this
			// method can't be called in the constructor
			instance.initialisePlayers();
		}

		return instance;
	}

	/**
	 * players should start with a goal and resource
	 */
	private void initialisePlayers() {
		for (Player player : playerManager.getAllPlayers()) {
			//TODO Needs access to station list
			goalManager.givePlayerGoal(player);
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

	public Map getMap() {
		return map;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}
}
