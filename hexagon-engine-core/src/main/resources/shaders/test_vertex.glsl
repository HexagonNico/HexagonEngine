#version 400 core

in vec2 vertex;

out vec3 color;

void main(void) {
    gl_Position =vec4(vertex, 0.0, 1.0);
    color = vec3(vertex.x + 0.5, vertex.y - 0.5, 0.5);
}