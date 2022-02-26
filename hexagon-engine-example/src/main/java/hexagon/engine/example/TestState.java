package hexagon.engine.example;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.ecs.components.Camera3D;
import hexagon.engine.ecs.components.LightComponent;
import hexagon.engine.ecs.components.ModelComponent;
import hexagon.engine.ecs.components.ReflectivityComponent;
import hexagon.engine.ecs.components.SpriteComponent;
import hexagon.engine.ecs.systems.LightSystem;
import hexagon.engine.ecs.systems.ModelRenderer;
import hexagon.engine.ecs.systems.SpriteRenderer;
import hexagon.engine.math.color.Color;
import hexagon.engine.math.vector.Float2;
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
		Camera3D cameraComponent = cameraEntity.addComponent(Camera3D::new);
		cameraComponent.position = new Float3(0.0f, 9.0f, 5.0f);
		cameraComponent.pitch = 30.0f;
		GameEntity dragonEntity = new GameEntity(this);
		ModelComponent dragonModel = dragonEntity.addComponent(ModelComponent::new);
		dragonModel.position = new Float3(0.0f, -1.0f, -10.0f);
		dragonModel.model = Model.getOrLoad("/models/dragon.obj");
		dragonModel.color = new Color(0.83f, 0.68f, 0.21f);
		ReflectivityComponent dragonReflectivity = dragonEntity.addComponent(ReflectivityComponent::new);
		dragonReflectivity.reflectivity = 1.0f;
		GameEntity squareEntity = new GameEntity(this);
		SpriteComponent squareSprite = squareEntity.addComponent(SpriteComponent::new);
		squareSprite.position = new Float2(0.0f, -1.0f);
		squareSprite.texture = Texture.getOrLoad("/textures/windmill.png");
		GameEntity sunEntity = new GameEntity(this);
		LightComponent lightComponent = sunEntity.addComponent(LightComponent::new);
		lightComponent.position = new Float3(200.0f, 200.0f, 100.0f);
	}

	@Override
	public void onUpdate() {
		
	}

	@Override
	public void onExit() {

	}
}
