package fvs.taxe.dialog;

import fvs.taxe.controller.Context;
import gameLogic.Player;
import gameLogic.map.Station;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DialogStationMultitrain extends Dialog {
	
	private Context context;
	private boolean isTrain = false;
	
	public DialogStationMultitrain(Station station, Skin skin, Context context) {
		super(station.getName(), skin);
		
		this.context = context;
		
		text("Choose which train you would like");
		
		for(Player player : context.getGameLogic().getPlayerManager().getAllPlayers()) {
			for(Resource resource : player.getResources()) {
				if(resource instanceof Train) {
					if(((Train) resource).getPosition() == station.getLocation()) {
						String destination = "";
						if(((Train) resource).getFinalDestination() != null) {
							destination = " to " + ((Train) resource).getFinalDestination().getName();
						}
						button(((Train) resource).getName() + destination + " (Player " + ((Train) resource).getPlayer().getPlayerNumber() + ")", ((Train) resource));
						isTrain = true;
					}
				}
			}
		}
		
		button("Cancel","CANCEL");
		if(isTrain == false) {
			hide();
		}
	}
	
	@Override
	public Dialog show(Stage stage) {
		show(stage, null);
		setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));
		return this;
	}
	
	@Override
	public void hide() {
		hide(null);
	}
	
	@Override
	protected void result(Object obj) {
		if(obj == "CANCEL"){
			this.remove();
		} else {
			//Simulate click on train
			TrainClicked clicker = new TrainClicked(context, (Train) obj);
			clicker.clicked(null, 0, 0);
		}
	}
	
	public boolean getIsTrain() {
		return isTrain;
	}
}
