#version 330

in vec3 normal_f;
in vec3 surfaceKoordinaten;
in vec2 uvKoordinaten;

out vec3 farbeAusTextur;

uniform sampler2D smplr;


void main(){

	vec3 lightPos = vec3(0.0,-2.0,3.0);
	vec3 light = normalize(lightPos - surfaceKoordinaten);
	vec3 view = normalize(-surfaceKoordinaten);
	vec3 normal  = normal_f;
	vec3 reflection  = reflect(-light,normal );

	float I = ((dot(light,normal ))+pow((max(0,dot(reflection ,view))),10));
	farbeAusTextur = texture(smplr, uvKoordinaten).rgb * I;
}
