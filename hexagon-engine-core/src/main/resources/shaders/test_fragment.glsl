#version 400 core

in vec2 pass_texture_coords;

out vec4 final_color;

uniform sampler2D texture_sampler;

void main(void) {
    final_color = texture(texture_sampler, pass_texture_coords);
}