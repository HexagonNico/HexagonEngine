#version 400 core

in vec3 texture_coords;

out vec4 final_color;

uniform sampler2DArray texture_sampler;

void main(void) {
	final_color = texture(texture_sampler, texture_coords);
}