#version 450 core
out vec4 FragColor;

struct Material {
    sampler2DArray diffuse;
    vec3 specular;    
    float shininess;
}; 

struct Light {
    vec3 direction;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

struct Maps {
    sampler2D grassNormal;
    sampler2D rockNormal;
    sampler2D snowNormal;

    sampler2D grassHeight;
    sampler2D rockHeight;
    sampler2D snowHeight;
};

in vec3 TexCoords;
in vec3 TangentViewPos;
in vec3 TangentFragPos;

uniform Material material;
uniform Light light;
uniform Maps terrainMaps;

vec2 ParallaxMapping(vec2 texCoords, vec3 viewDir, sampler2D depthMap) {
    float height =  texture(depthMap, texCoords).r;    
    vec2 p = viewDir.xy / viewDir.z * (height * 2.0);
    return texCoords - p;
}

void main() {
    float layer = TexCoords.z;
    vec3 viewDir = normalize(TangentViewPos - TangentFragPos);

    vec3 n;
    vec2 textureCoordinates;

    if (layer == 0.0) {
        n = texture(terrainMaps.grassNormal, vec2(TexCoords)).rgb;
        textureCoordinates = ParallaxMapping(vec2(TexCoords), viewDir, terrainMaps.grassHeight);
    } if (layer == 1.0) {
        n = texture(terrainMaps.rockNormal, vec2(TexCoords)).rgb;
        textureCoordinates = ParallaxMapping(vec2(TexCoords), viewDir, terrainMaps.rockHeight);
    } if (layer == 2.0) {
        n = texture(terrainMaps.snowNormal, vec2(TexCoords)).rgb;
        textureCoordinates = ParallaxMapping(vec2(TexCoords), viewDir, terrainMaps.snowHeight);
    }
    vec4 tex = vec4(texture(material.diffuse, TexCoords));
    vec3 ambient = light.ambient * tex.rgb;

    if (tex.a < 0.5)
        discard;
    vec3 norm = n * 2.0 - 1.0;
    norm = normalize(norm);
    vec3 lightDir = normalize(-light.direction);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = light.diffuse * diff * tex.rgb;  

    vec3 reflectDir = reflect(-lightDir, norm);  
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    vec3 specular = light.specular * (spec * material.specular);  
        
    vec3 result = ambient + diffuse + specular;

    // if (result.a < 0.5)
    //     discard;
    FragColor = vec4(result, 1.0);
}