#version 400 core

in vec3 vertex;
in vec2 texture_coordinates;
in vec3 normal;

out vec2 texture_coords;

out vec3 surface_normal;
out vec3 to_light_vector;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

uniform vec3 light_position;

void main(void) {
	
	vec4 world_position = transformation_matrix * vec4(vertex, 1.0);
	gl_Position = projection_matrix * view_matrix * world_position;
	
	texture_coords = texture_coordinates;

	surface_normal = (transformation_matrix * vec4(normal, 0.0)).xyz;
	to_light_vector = light_position - world_position.xyz;
}