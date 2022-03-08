#version 400 core

in vec2 vertex;
int int tile;

out vec3 texture_coords;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

void main(void) {
	gl_Position = projection_matrix * view_matrix * transformation_matrix * vec4(vertex, 0.0, 1.0);
	texture_coords = vec3(vertex.x, -vertex.y, float(tile));
}