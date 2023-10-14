package com.server.booksummar.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "O campo 'nome' não pode estar em branco.")
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank(message = "O campo 'email' não pode estar em branco.")
    @Email(message = "O campo 'email' deve ser um endereço de e-mail válido.")
    private String email;

    @Size(min = 3, max = 20, message = "O campo 'username' deve ter entre 3 e 20 caracteres.")
    private String userName;

    private String avatarURL;

}
