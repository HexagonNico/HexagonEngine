#version 400 core

in vec3 surface_normal;
in vec4 world_position;
in vec3 to_camera_vector;

out vec4 final_color;

uniform vec3 color;

vec3 pixel_light(vec3 surface_normal, vec3 world_position);
vec3 specular_light(vec3 surface_normal, vec3 world_position, vec3 to_camera_vector);

void main(void) {
	vec3 light = pixel_light(surface_normal, world_position.xyz);
	vec3 specular = specular_light(surface_normal, world_position.xyz, to_camera_vector);
	final_color = vec4(color * light + specular, 1.0);
}