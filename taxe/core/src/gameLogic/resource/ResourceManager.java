package gameLogic.resource;

import gameLogic.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResourceManager {
    private final int CONFIG_MAX_RESOURCES = 10;
    private Random random = new Random();

    private Resource getRandomResource() {
        int index = random.nextInt(2);

        switch(index) {
            case 0:
                return new Train("Bullet Train", "BulletTrain.png", 100);
            case 1:
                return new Train("Nulcear Train", "NuclearTrain.png", 200);
        }

        throw new RuntimeException("local int index must be wrong" + index);
    }

    public void addRandomResourceToPlayer(Player p) {
        addResourceToPlayer(p, getRandomResource());
    }

    public void addResourceToPlayer(Player p, Resource r) {
        if (p.getResources().size() >= CONFIG_MAX_RESOURCES) {
            throw new RuntimeException("Player has exceeded CONFIG_MAX_RESOURCES");
        }

        p.addResource(r);
    }
}