package fvs.taxe.controller;

import gameLogic.map.IPositionable;

import java.util.List;

public class PlacingController {
    private List<IPositionable> placingPositions;

    public void setPlacingPositions(List<IPositionable> placingPositions) {
        this.placingPositions = placingPositions;
    }

    public List<IPositionable> getPlacingPositions() {
        return placingPositions;
    }
}
