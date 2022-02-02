#version 400 core

in vec3 surface_normal;
in vec3 to_light_vector;

out vec4 final_color;

uniform vec3 color;

uniform vec3 light_color;

//vec3 point_light_effect(vec3 normal, vec3 position);
//vec3 specular_light_effect(vec3 normal, vec3 world_pos, vec3 to_camera_vector);

void main(void) {

	vec3 unit_normal = normalize(surface_normal);
	vec3 unit_to_light_vector = normalize(to_light_vector);

	float dot1 = dot(unit_normal, unit_to_light_vector);
	float brightness = max(dot1, 0.0);
	vec3 diffuse = brightness * light_color;

	final_color = vec4(color * diffuse, 1.0);
}