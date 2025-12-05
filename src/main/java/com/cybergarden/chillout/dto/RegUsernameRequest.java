package com.cybergarden.chillout.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegUsernameRequest")
public record RegUsernameRequest(
        String username
) { }
