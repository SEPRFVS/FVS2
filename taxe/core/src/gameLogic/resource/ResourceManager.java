package gameLogic.resource;

import gameLogic.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResourceManager {
    private List<Resource> resources;
    private int CONFIG_MAX_RESOURCES = 10;

    public ResourceManager() {
        resources = new ArrayList<Resource>();
        resources.add(new Train("Bullet Train", "BulletTrain.png"));
        resources.add(new Train("Nulcear Train", "NuclearTrain.png"));
    }

    private Resource getRandomResource() {
        Random r = new Random();
        int index = r.nextInt(resources.size());
        return resources.get(index);
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