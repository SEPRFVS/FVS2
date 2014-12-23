package fvs.taxe;

import gameLogic.map.IPositionable;
import gameLogic.map.Map;
import gameLogic.map.Station;
import gameLogic.map.StationHelper;
import gameLogic.resource.Train;

import java.util.ArrayList;
import java.util.List;

public class RouteListener implements StationClickListener, RouteConfirmedListener {
    private List<IPositionable> positions = new ArrayList<IPositionable>();
    private MapRenderer mapRenderer;
    private Train train;

    public RouteListener(MapRenderer mapRenderer, Train train) {
        this.mapRenderer = mapRenderer;
        this.train = train;

        positions.add(train.getPosition());
        mapRenderer.beginRoutingState(this, positions);
    }

    @Override
    public void clicked(Station station) {

        // TODO check immediate connection exists in Map.connections between current and clicked position
        // TODO check connection doesn't already exist in route

        // the latest position chosen in the route so far
        IPositionable lastPosition =  positions.get(positions.size() - 1);
        Station lastStation = mapRenderer.getMap().getStationFromPosition(lastPosition);

        if(StationHelper.doesConnectionExist(station.getName(), lastStation.getName())) {
            positions.add(station.getLocation());
            mapRenderer.setPlacingPositions(positions);
        }
    }

    @Override
    public void confirmed() {
        List<Station> route = new ArrayList<Station>();
        Map map = mapRenderer.getMap();

        for (IPositionable position : positions) {
            route.add(map.getStationFromPosition(position));
        }

        train.setRoute(route);
        mapRenderer.addMoveActions(train);
    }
}
