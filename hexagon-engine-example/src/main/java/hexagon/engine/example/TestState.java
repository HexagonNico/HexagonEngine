package hexagon.engine.example;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.ecs.components.Camera;
import hexagon.engine.ecs.components.LightComponent;
import hexagon.engine.ecs.components.ModelComponent;
import hexagon.engine.ecs.components.ReflectivityComponent;
import hexagon.engine.ecs.components.SpriteComponent;
import hexagon.engine.ecs.components.Transform2D;
import hexagon.engine.ecs.components.Transform3D;
import hexagon.engine.ecs.systems.LightSystem;
import hexagon.engine.ecs.systems.ModelRenderer;
import hexagon.engine.ecs.systems.SpriteRenderer;
import hexagon.engine.math.color.Color;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.opengl.Texture;
import hexagon.engine.states.GameState;
import hexagon.engine.utils.models.Model;

public final class TestState extends GameState {

	public TestState() {
		super(new SpriteRenderer(), new ModelRenderer(), new LightSystem());
	}

	@Override
	public void onStart() {
		GameEntity cameraEntity = new GameEntity(this);
		Camera cameraComponent = cameraEntity.addComponent(Camera::new);
		cameraComponent.position = new Float3(0.0f, 0.0f, 5.0f);
		cameraComponent.pitch = 0.0f;

		GameEntity dragonEntity = new GameEntity(this);
		Transform3D dragonTransform = dragonEntity.addComponent(Transform3D::new);
		dragonTransform.setPosition(0.0f, -4.0f, -10.0f);
		ModelComponent dragonModel = dragonEntity.addComponent(ModelComponent::new);
		dragonModel.model = Model.getOrLoad("/models/dragon.obj");
		dragonModel.color = new Color(0.83f, 0.68f, 0.21f);
		ReflectivityComponent dragonReflectivity = dragonEntity.addComponent(ReflectivityComponent::new);
		dragonReflectivity.reflectivity = 1.0f;

		GameEntity squareEntity = new GameEntity(this);
		squareEntity.addComponent(Transform2D::new);
		SpriteComponent squareSprite = squareEntity.addComponent(SpriteComponent::new);
		squareSprite.texture = Texture.getOrLoad("/textures/windmill.png");

		GameEntity sunEntity = new GameEntity(this);
		Transform3D sunTransform = sunEntity.addComponent(Transform3D::new);
		sunTransform.setPosition(200.0f, 200.0f, 100.0f);
		sunEntity.addComponent(LightComponent::new);
	}

	@Override
	public void onUpdate() {
		
	}

	@Override
	public void onExit() {

	}
}
