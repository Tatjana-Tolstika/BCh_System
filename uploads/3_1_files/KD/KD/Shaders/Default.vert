#version 330 core
layout (Location = 0) in vec3 aCoords;


out vec2 texCoords;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
	 gl_Position = vec4(aPos, 1.0) * projection * view * model;
	
}