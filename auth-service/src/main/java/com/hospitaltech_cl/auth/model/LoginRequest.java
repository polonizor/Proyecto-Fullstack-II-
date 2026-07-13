package com.hospitaltech_cl.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(example = "Username")
    private String username;

    @Schema(example = "123456789")
    private String password;
}