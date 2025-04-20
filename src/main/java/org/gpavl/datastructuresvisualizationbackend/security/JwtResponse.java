package org.gpavl.datastructuresvisualizationbackend.security;

import lombok.Getter;

@Getter
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    public JwtResponse(String token) {
        this.token = token;
    }
}
