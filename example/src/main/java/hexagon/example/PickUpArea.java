package hexagon.example;

import hexagon.core.GameEntity;
import hexagon.core.components.TriggerArea;

public class PickUpArea extends TriggerArea {

	@Override
	public void onBodyEntered(GameEntity thisEntity, GameEntity otherEntity) {
		// TODO - if(otherEntity is Player)?
		thisEntity.markToRemove();
	}
}
