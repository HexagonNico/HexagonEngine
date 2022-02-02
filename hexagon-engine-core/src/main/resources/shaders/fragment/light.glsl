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

vec3 specular_light(vec3 surface_normal, vec3 world_position, vec3 to_camera_vector) {
	vec3 unit_camera_vector = normalize(to_camera_vector);
	vec3 light_direction = -normalize(light_position - world_position);
	vec3 reflected_light = reflect(light_direction, normalize(surface_normal));
	float specular_factor = max(dot(reflected_light, unit_camera_vector), 0.0);
	float damped_factor = pow(specular_factor, shine_damper);
	return light_color * light_intensity * specular_factor * reflectivity;
}