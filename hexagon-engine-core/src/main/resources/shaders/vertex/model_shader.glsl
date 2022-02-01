#version 400 core

in vec3 vertex;
in vec2 texture_coordinates;
in vec3 normal;

out vec2 texture_coords;
out vec3 point_light_result;
out vec3 specular_light_result;

uniform mat4 transformation_matrix;
uniform mat4 projection_matrix;
uniform mat4 view_matrix;

vec3 point_light_effect(vec3 surface_normal, vec3 world_position);
vec3 specular_light_effect(vec3 surface_normal, vec3 world_position, vec3 to_camera_vector);

void main(void) {
	vec4 world_position = transformation_matrix * vec4(vertex, 1.0);
	gl_Position = projection_matrix * view_matrix * world_position;
	texture_coords = texture_coordinates;

	vec4 surface_normal = transformation_matrix * vec4(normal, 1.0);
	vec4 to_camera_vector = inverse(view_matrix) * vec4(0.0, 0.0, 0.0, 1.0) - world_position;
	point_light_result = point_light_effect(surface_normal.xyz, world_position.xyz);
	specular_light_result = specular_light_effect(surface_normal.xyz, world_position.xyz, to_camera_vector.xyz);
}