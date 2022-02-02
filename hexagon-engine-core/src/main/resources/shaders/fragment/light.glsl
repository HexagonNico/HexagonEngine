#version 400 core

uniform float diffuse_light;

uniform vec3 light_position;
uniform vec3 light_color;
uniform float light_intensity;

uniform float shine_damper;
uniform float reflectivity;

// TODO - Support for multiple lights should also solve the problem of not having lights

vec3 point_light_effect(vec3 surface_normal, vec3 world_position) {
	vec3 unit_normal = normalize(surface_normal);
	vec3 to_light_vector = normalize(light_position - world_position);
	float brightness = clamp(dot(unit_normal, to_light_vector), diffuse_light, 1.0);
	return brightness * light_color * light_intensity;
}

vec3 specular_light_effect(vec3 surface_normal, vec3 world_position, vec3 to_camera_vector) {
	vec3 unit_to_camera_vector = normalize(to_camera_vector);
	vec3 light_vector = -normalize(light_position - world_position);
	vec3 reflected_light = reflect(light_vector, surface_normal);
	float specular_factor = max(dot(reflected_light, unit_to_camera_vector), 0.0);
	float damped_factor = pow(specular_factor, /*shine_damper*/10.0);
	return damped_factor * /*reflectivity*/1.0 * light_color * light_intensity;
}