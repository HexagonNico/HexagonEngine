#version 400 core

in vec3 vertex;
in vec2 texture_coordinates;

out vec2 texture_coords;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

void main(void) {
	gl_Position = projection_matrix * view_matrix * transformation_matrix * vec4(vertex, 1.0);
	texture_coords = texture_coordinates;
}