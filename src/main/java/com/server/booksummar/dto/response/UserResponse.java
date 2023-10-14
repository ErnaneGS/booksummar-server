package com.server.booksummar.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {

    private UUID id;

    private String name;

    private String email;

    private String userName;

    private String avatarURL;

}
