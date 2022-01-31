#version 400 core

in vec3 vertex;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

void main(void) {
    gl_Position = projection_matrix * view_matrix * transformation_matrix * vec4(vertex, 1.0);
}