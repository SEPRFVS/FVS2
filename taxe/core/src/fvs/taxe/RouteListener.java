package fvs.taxe;

import fvs.taxe.controller.Context;
import fvs.taxe.controller.RouteController;
import fvs.taxe.controller.TrainController;
import gameLogic.map.IPositionable;
import gameLogic.map.Map;
import gameLogic.map.Station;
import gameLogic.map.StationHelper;
import gameLogic.resource.Train;

import java.util.ArrayList;
import java.util.List;

public class RouteListener implements StationClickListener, RouteConfirmedListener {
    private List<IPositionable> positions = new ArrayList<IPositionable>();
    private Train train;
    private Context context;
    private Map map;

    public RouteListener(Context context, Train train) {
        this.train = train;
        this.context = context;
        map = context.getGameLogic().getMap();

        positions.add(train.getPosition());
        RouteController routeController = new RouteController(context, this, positions);
    }

    @Override
    public void clicked(Station station) {

        // TODO check immediate connection exists in Map.connections between current and clicked position
        // TODO check connection doesn't already exist in route

        // the latest position chosen in the route so far
        IPositionable lastPosition =  positions.get(positions.size() - 1);
        Station lastStation = map.getStationFromPosition(lastPosition);

        if(StationHelper.doesConnectionExist(station.getName(), lastStation.getName())) {
            positions.add(station.getLocation());
            setPlacingPositions(positions);
        }
    }

    @Override
    public void confirmed() {
        List<Station> route = new ArrayList<Station>();

        for (IPositionable position : positions) {
            route.add(map.getStationFromPosition(position));
        }

        train.setRoute(route);

        TrainController trainController = new TrainController(context);
        trainController.addMoveActions(train);
    }
}
