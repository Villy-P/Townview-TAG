#version 450 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aTexCoords;
layout (location = 2) in vec3 aNormal;
layout (location = 3) in vec3 aTangent;
layout (location = 4) in vec3 aBitangent;
layout (location = 5) in mat4 instanceMatrix;

out vec3 FragPos;
out vec3 Normal;
out vec3 TexCoords;

uniform mat4 projection;
uniform mat4 view;

void main() {
    FragPos = vec3(instanceMatrix * vec4(aPos, 1.0));
    Normal = mat3(transpose(inverse(instanceMatrix))) * aNormal;  
    TexCoords = aTexCoords;
    
    gl_Position = projection * view * vec4(FragPos, 1.0);
}