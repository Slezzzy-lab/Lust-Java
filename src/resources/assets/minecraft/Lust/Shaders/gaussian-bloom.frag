#version 120

uniform sampler2D u_texture, u_texture2;
uniform vec2 texelSize, direction;
uniform float radius;

#define step texelSize * direction

float gauss(float x, float sigma) {
    float pow = x / sigma;
    return (1.0 / (abs(sigma) * 2.50662827463) * exp(-0.5 * pow * pow));
}

void main() {
    if (direction.y == 1 && texture2D(u_texture2, gl_TexCoord[0].st).a != 0.0) discard;

    float alpha = 0.;

    for (float f = -radius; f <= radius; f++) {
        alpha += texture2D(u_texture, gl_TexCoord[0].st + f * step).a * gauss(f, radius / 2);
    }

    gl_FragColor = vec4(0., 0., 0., alpha);
}