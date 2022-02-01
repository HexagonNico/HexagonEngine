#version 400 core

in vec2 vertex;

out vec2 texture_coords;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

uniform vec2 uv;
uniform vec2 size;

void main(void) {
	gl_Position = projection_matrix * view_matrix * transformation_matrix * vec4(vertex, 0.0, 1.0);
	texture_coords = vec2((vertex.x + 0.5) * size.x, (0.5 - vertex.y) * size.y) + uv;
}