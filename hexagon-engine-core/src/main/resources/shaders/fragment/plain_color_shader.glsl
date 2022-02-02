#version 400 core

in vec3 surface_normal;
in vec4 world_position;

out vec4 final_color;

uniform vec3 color;

vec3 pixel_light(vec3 surface_normal, vec3 world_position);

void main(void) {
	vec3 light = pixel_light(surface_normal, world_position.xyz);
	final_color = vec4(color * light, 1.0);
}