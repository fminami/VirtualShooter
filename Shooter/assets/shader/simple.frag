#version 100

#ifdef GL_FRAGMENT_PRECISION_HIGH
precision highp float;
#else
precision mediump float;
#endif

uniform float u_Alpha;

uniform vec3 u_Diffuse;
uniform vec3 u_Specular;
uniform vec3 u_Ambient;
uniform float u_Shininess;

uniform vec3 u_LightDirection;
uniform vec3 u_LightDiffuse;
uniform vec3 u_LightSpecular;
uniform vec3 u_LightAmbient;

varying vec3 v_Position;
varying vec3 v_Normal;

void main()
{
    vec3 V = normalize(-v_Position);
    vec3 N = normalize(v_Normal);
    vec3 L = -u_LightDirection;
    
    vec3 H = normalize(L + V);
    
    vec3 diffuse = u_Diffuse * u_LightDiffuse * max(dot(L, N), 0.0);
    vec3 specular = u_Specular * u_LightSpecular * pow(max(dot(H, N), 0.0), u_Shininess);
    vec3 ambient = u_Ambient * u_LightAmbient;
    
    gl_FragColor.rgb = diffuse + specular + ambient;
    gl_FragColor.a = u_Alpha;
}