#version 400 core


in vec2 vertex;

out vec2 texture_coords;
//out vec3 pass_color;

uniform mat4 transformation_matrix;

void main(void) {
	gl_Position = transformation_matrix * vec4(vertex, 0.0, 1.0);
	texture_coords = vec2(vertex.x + 0.5, 0.5 - vertex.y);
	//pass_color = vec3(vertex.x + 0.5, 0.5 - vertex.y, 0.7);
}