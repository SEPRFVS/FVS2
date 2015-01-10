package gameLogic.resource;

import gameLogic.Player;

import java.util.Random;

import Util.Tuple;

public class ResourceManager {
    private final int CONFIG_MAX_RESOURCES = 7;
    private Random random = new Random();

    private Resource getRandomResource() {
            	
    	int index = random.nextInt(TrainHelper.getTrainNames().size());
    	Tuple<String, Integer> train = TrainHelper.getTrains().get(index);
    	return new Train(train.getFirst(), train.getFirst().replaceAll(" ", "") + ".png",train.getSecond());
    	
    }

    public void addRandomResourceToPlayer(Player player) {
        addResourceToPlayer(player, getRandomResource());
    }

    private void addResourceToPlayer(Player player, Resource resource) {
        if (player.getResources().size() >= CONFIG_MAX_RESOURCES) {
            //throw new RuntimeException("Player has exceeded CONFIG_MAX_RESOURCES");
        	return;
        }

        resource.setPlayer(player);
        player.addResource(resource);
    }
}