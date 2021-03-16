#version 330

layout(location=0) in vec4 v_position;
layout(location=1) in vec3 v_normal;
layout(location=2) in vec2 v_tex;

uniform mat4 worldMatrix;
uniform mat4 projMatrix;

out vec3 normal_f;
out vec3 surfaceKoordinaten;
out vec2 uvKoordinaten;

void main() {
	uvKoordinaten = v_tex;
	surfaceKoordinaten = (worldMatrix * v_position).xyz;
	normal_f = normalize(inverse(transpose(mat3(worldMatrix)))*v_normal);

	gl_Position = projMatrix * worldMatrix * v_position;

}
