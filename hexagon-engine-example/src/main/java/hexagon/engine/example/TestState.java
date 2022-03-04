package hexagon.engine.example;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.ecs.components.Transform3D;
import hexagon.engine.ecs.systems.LightSystem;
import hexagon.engine.ecs.systems.ModelRenderer;
import hexagon.engine.states.EntitiesLoader;
import hexagon.engine.states.GameState;

public final class TestState extends GameState {

	private GameEntity dragonEntity;

	public TestState() {
		super(new ModelRenderer(), new LightSystem());
	}

	@Override
	public void onStart() {
		dragonEntity = EntitiesLoader.loadEntities(this, "/entities/dragon-orbit.json", 1);
		/*
		GameEntity cameraEntity = new GameEntity(this);
		Camera cameraComponent = cameraEntity.addComponent(Camera::new);
		cameraComponent.setPosition(new Float3(0.0f, 0.0f, 5.0f));

		GameEntity dragonEntity = new GameEntity(this);
		Transform3D dragonTransform = dragonEntity.addComponent(Transform3D::new);
		dragonTransform.setPosition(0.0f, -4.0f, -10.0f);
		ModelComponent dragonModel = dragonEntity.addComponent(ModelComponent::new);
		dragonModel.model = Model.getOrLoad("/models/dragon.obj");
		dragonModel.color = new Color(0.83f, 0.68f, 0.21f);
		ReflectivityComponent dragonReflectivity = dragonEntity.addComponent(ReflectivityComponent::new);
		dragonReflectivity.setReflectivity(1.0f);

		GameEntity squareEntity = new GameEntity(this);
		squareEntity.addComponent(Transform2D::new);
		SpriteComponent squareSprite = squareEntity.addComponent(SpriteComponent::new);
		squareSprite.setTexture("/textures/windmill.png");

		GameEntity sunEntity = new GameEntity(this);
		Transform3D sunTransform = sunEntity.addComponent(Transform3D::new);
		sunTransform.setPosition(200.0f, 200.0f, 100.0f);
		sunEntity.addComponent(LightComponent::new);
		*/
	}

	@Override
	public void onUpdate() {
		this.dragonEntity.getComponent(Transform3D.class).ifPresent(transform -> {
			transform.rotate(0.0f, 0.1f, 0.0f);
		});
	}

	@Override
	public void onExit() {

	}
}
