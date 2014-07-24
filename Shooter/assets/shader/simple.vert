#version 100

uniform mat4 u_MVPMatrix;
uniform mat4 u_MVMatrix;

attribute vec4 a_Position;
attribute vec3 a_Normal;

varying vec3 v_Position;
varying vec3 v_Normal;

void main()
{
    v_Position = (u_MVMatrix * a_Position).xyz;
    v_Normal = (mat3(u_MVMatrix) * a_Normal);
    
    gl_Position = u_MVPMatrix * a_Position;
}