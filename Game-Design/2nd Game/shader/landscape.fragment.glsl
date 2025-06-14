#version 450 core
out vec4 FragColor;

struct Material {
    sampler2DArray diffuse;
    vec3 specular;    
    float shininess;
}; 

struct Light {
    vec3 position;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

in vec3 FragPos;  
in vec3 Normal;  
in vec3 TexCoords;

uniform vec3 viewPos;
uniform Material material;
uniform Light light;

void main() {
    vec4 tex = vec4(texture(material.diffuse, TexCoords));
    vec3 ambient = light.ambient * tex.rgb;

    if (tex.a < 0.5)
        discard;
    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(light.position - FragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = light.diffuse * diff * tex.rgb;  

    vec3 viewDir = normalize(viewPos - FragPos);
    vec3 reflectDir = reflect(-lightDir, norm);  
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular = light.specular * (spec * material.specular);  
        
    vec3 result = ambient + diffuse + specular;

    // if (result.a < 0.5)
    //     discard;
    FragColor = vec4(result, 1.0);
}