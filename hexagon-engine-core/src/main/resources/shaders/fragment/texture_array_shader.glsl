#version 400 core

in vec3 texture_coords;

out vec4 final_color;

uniform sampler2DArray texture_sampler;

void main(void) {
	//final_color = texture(texture_sampler, vec3(texture_coords.xy, 2));
	final_color = vec4(texture_coords, 1.0);
}