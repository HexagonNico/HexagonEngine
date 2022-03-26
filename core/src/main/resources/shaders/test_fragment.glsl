#version 400 core


in vec2 texture_coords;
//in vec3 pass_color;

out vec4 final_color;

uniform sampler2D texture_sampler;


void main(void) {
	//final_color = vec4(pass_color, 1.0);
	final_color = texture(texture_sampler, texture_coords);
}