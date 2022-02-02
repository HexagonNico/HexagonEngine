#version 400 core

in vec3 point_light_result;
//in vec3 specular_light_result;

out vec4 final_color;

uniform vec3 color;

void main(void) {
	final_color = vec4(color * point_light_result/* + specular_light_result*/, 1.0);
}