#version 400 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform vec3 lightColour[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColour;

void main(void) {

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for (int x = 0; x < 4; x++) {
        float distance = length(toLightVector[x]);
        float attFactor = attenuation[x].x + (attenuation[x].y * distance) + (attenuation[x].z * distance * distance);
        vec3 unitLightVector = normalize(toLightVector[x]);
        float nDot1 = dot(unitNormal, unitLightVector);
        float brightness = max(nDot1, 0.0);
        vec3 lightDirection = -unitLightVector;
        vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);
        float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
        specularFactor = max(specularFactor, 0.0);
        float dampedFactor = pow(specularFactor, shineDamper);
        totalDiffuse = totalDiffuse + (brightness * lightColour[x])/attFactor;
        totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColour[x])/attFactor;
    }
    totalDiffuse = max(totalDiffuse, 0.2);

    vec4 textureColour = texture(textureSampler, pass_textureCoords);
    if(textureColour.a<0.5) {
        discard;
    }

    out_Color = vec4(totalDiffuse, 1.0) * textureColour + vec4(totalSpecular, 1.0);
    out_Color = mix(vec4(skyColour,1.0), out_Color, visibility);
}