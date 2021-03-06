#version 400 core

in vec2 vertex;

out vec2 texture_coords;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

uniform vec2 offset;

void main(void) {
	gl_Position = projection_matrix * view_matrix * transformation_matrix * vec4(vertex + offset, 0.0, 1.0);
	texture_coords = vec2(vertex.x + 0.5, 0.5 - vertex.y);
}