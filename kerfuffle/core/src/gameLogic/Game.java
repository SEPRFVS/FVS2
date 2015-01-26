package gameLogic;

import gameLogic.goal.GoalManager;
import gameLogic.map.Map;
import gameLogic.resource.ResourceManager;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private static Game instance;
	private PlayerManager playerManager;
	private GoalManager goalManager;
	private ResourceManager resourceManager;
	private Map map;
	private GameState state;
	private List<GameStateListener> gameStateListeners = new ArrayList<GameStateListener>();

	private final int CONFIG_PLAYERS = 2;
	public final int TOTAL_TURNS = 30;

	private Game() {
		playerManager = new PlayerManager();
		playerManager.createPlayers(CONFIG_PLAYERS);

		resourceManager = new ResourceManager();
		goalManager = new GoalManager(resourceManager);
		
		map = new Map();
		
		state = GameState.NORMAL;

		playerManager.subscribeTurnChanged(new TurnListener() {
			@Override
			public void changed() {
				Player currentPlayer = playerManager.getCurrentPlayer();
				goalManager.addRandomGoalToPlayer(currentPlayer);
				resourceManager.addRandomResourceToPlayer(currentPlayer);
				resourceManager.addRandomResourceToPlayer(currentPlayer);
			}
		});
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

	// Only the first player should be given goals and resources during init
	// The second player gets them when turn changes!
	private void initialisePlayers() {
		Player player = playerManager.getAllPlayers().get(0);
		goalManager.addRandomGoalToPlayer(player);
		resourceManager.addRandomResourceToPlayer(player);
		resourceManager.addRandomResourceToPlayer(player);
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
		stateChanged();
	}

	public void subscribeStateChanged(GameStateListener listener) {
		gameStateListeners.add(listener);
	}

	private void stateChanged() {
		for(GameStateListener listener : gameStateListeners) {
			listener.changed(state);
		}
	}
}
