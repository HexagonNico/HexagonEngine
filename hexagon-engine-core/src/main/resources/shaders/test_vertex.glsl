#version 400 core

in vec2 vertex;

out vec2 texture_coords;

void main(void) {
    gl_Position =vec4(vertex, 0.0, 1.0);
    texture_coords = vec2(vertex.x + 0.5, 0.5 - vertex.y);
}