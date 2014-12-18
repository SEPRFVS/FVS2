package fvs.taxe;

import com.badlogic.gdx.graphics.Color;
import gameLogic.map.IPositionable;
import gameLogic.map.Station;

import java.util.ArrayList;
import java.util.List;

public class RouteListener implements StationClickListener {
    private List<IPositionable> positions = new ArrayList<IPositionable>();
    private MapRenderer mapRenderer;

    public RouteListener(MapRenderer mapRenderer, IPositionable trainCurrentPosition) {
        this.mapRenderer = mapRenderer;

        positions.add(trainCurrentPosition);
        mapRenderer.setPlacingPositions(positions);
        mapRenderer.setState(GameState.PLACING);
    }

    @Override
    public void clicked(Station station) {
        positions.add(station.getLocation());
        mapRenderer.setPlacingPositions(positions);
    }
}
