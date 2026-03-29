#version 330 core
layout (Location = 0) in vec3 aCoords;
layout (Location = 1) in vec2 aTexCoords;

out vec2 texCoords;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
	gl_Position = vec4(aCoords, 1.0) * model * view * projection;
	texCoords = aTexCoords;
}