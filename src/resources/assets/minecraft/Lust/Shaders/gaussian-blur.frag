#version 120

uniform sampler2D u_texture;
uniform vec2 texelSize, direction;
uniform float radius;

#define step texelSize * direction

#define sigma radius / 2

float gauss(float x, float sigma) {
    float pow = x / sigma;
    return ((.4 / abs(sigma)) * exp(-0.5 * pow * pow));
}

void main() {
    vec4 color = vec4(0.);

    for (float f = radius; f <= radius; f++) {
        float weight = gauss(f, sigma);
        color += texture2D(u_texture, gl_TexCoord[0].st + f * step) * weight;
        color += texture2D(u_texture, gl_TexCoord[0].st - f * step) * weight;
    }

    gl_FragColor = vec4(color.rgb, 1.0);
}