#version 330

layout(location=0) in vec4 v_position;
//layout(location=1) in vec3 v_normal;
layout(location=2) in vec2 tex_coord;

uniform mat4 worldMatrix;
uniform mat4 projMatrix;

out vec2 texcoord;

void main() {

	texcoord = tex_coord;
	gl_Position = projMatrix * worldMatrix * v_position;

}
