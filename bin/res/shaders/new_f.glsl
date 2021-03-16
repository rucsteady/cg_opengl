#version 330

in vec3 normalVektor;
in vec2 texcoord;

out vec3 f_color;

uniform sampler2D smplr;


void main(){

	f_color = texture(smplr, texcoord).rgb;
}
