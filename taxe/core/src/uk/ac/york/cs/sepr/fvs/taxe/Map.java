package uk.ac.york.cs.sepr.fvs.taxe;

import java.util.List;

public class Map {
    private List<IPositionable> objects;

    public List<IPositionable> getObjects() {
        return objects;
    }

    public void addObject(IPositionable object){
        objects.add(object);
    }
}
