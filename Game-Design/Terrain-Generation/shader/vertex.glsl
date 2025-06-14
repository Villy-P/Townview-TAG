#version 450 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aTexCoords;
layout (location = 2) in vec3 aNormal;
layout (location = 3) in vec3 aTangent;
layout (location = 4) in vec3 aBitangent;

out vec3 TexCoords;
out vec3 TangentViewPos;
out vec3 TangentFragPos;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform vec3 viewPos;

void main() {
    vec3 FragPos = vec3(model * vec4(aPos, 1.0));
    TexCoords = aTexCoords;

    vec3 T = normalize(vec3(model * vec4(aTangent,   0.0)));
    vec3 N = normalize(vec3(model * vec4(aNormal,    0.0)));
    T = normalize(T - dot(T, N) * N);
    vec3 B = cross(N, T);
    mat3 TBN = transpose(mat3(T, B, N));

    TangentViewPos = TBN * viewPos;
    TangentFragPos = TBN * vec3(model * vec4(aPos, 1.0));
    
    gl_Position = projection * view * vec4(FragPos, 1.0);
}