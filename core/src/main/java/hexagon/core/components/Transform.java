package hexagon.core.components;

import hexagon.core.base.Component;
import hexagon.core.base.GameEntity;
import hexagon.math.matrix.Matrix4;

public abstract class Transform extends Component {
	
	public Transform(GameEntity entity) {
		super(entity);
		//TODO Auto-generated constructor stub
	}

	public abstract Matrix4 matrix();
}
