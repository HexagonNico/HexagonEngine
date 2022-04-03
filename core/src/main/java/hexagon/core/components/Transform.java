package hexagon.core.components;

import hexagon.math.matrix.Matrix4;

/**
 * Component that stores geometry transformations for an entity.
 * Represents the transformation with a {@link Matrix4} that can be used for rendering.
 * 
 * @author Nico
 */
public abstract class Transform extends Component {

	/**
	 * Computes the transformation matrix.
	 * 
	 * @return A transformation matrix with this transform's transformation
	 */
	public abstract Matrix4 matrix();
}
