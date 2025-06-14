#version 450 core
out vec4 FragColor;

in vec3 TexCoords;

uniform sampler2D texture_diffuse1;

void main() {
    vec4 texColor = texture(texture_diffuse1, vec2(TexCoords));
    FragColor = texColor;
}