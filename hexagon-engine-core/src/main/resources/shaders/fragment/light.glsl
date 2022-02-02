#version 400 core

uniform float diffuse_light;

uniform vec3 light_position;
uniform vec3 light_color;
uniform float light_intensity;

uniform float shine_damper;
uniform float reflectivity;

// TODO - Support for multiple lights should also solve the problem of not having lights

vec3 pixel_light(vec3 surface_normal, vec3 world_position) {
	vec3 unit_normal = normalize(surface_normal);
	vec3 to_light_vector = normalize(light_position - world_position);
	float brightness = max(dot(unit_normal, to_light_vector), diffuse_light);
	return brightness * light_color * light_intensity;
}