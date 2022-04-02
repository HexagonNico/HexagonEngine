package hexagon.core.components;

import hexagon.core.base.Component;
import hexagon.math.matrix.Matrix4;

public abstract class Transform extends Component {

	public abstract Matrix4 matrix();
}
