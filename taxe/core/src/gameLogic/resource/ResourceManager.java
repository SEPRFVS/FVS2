package gameLogic.resource;

import gameLogic.Player;

import java.util.Random;

public class ResourceManager {
    private final int CONFIG_MAX_RESOURCES = 7;
    private Random random = new Random();

    private Resource getRandomResource() {
        int index = random.nextInt(2);

        switch(index) {
            case 0:
                return new Train("Bullet Train", "BulletTrain.png", 50);
            case 1:
                return new Train("Nuclear Train", "NuclearTrain.png", 100);
        }

        throw new RuntimeException("local int index must be wrong" + index);
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