package com.tcc.joaomyrlla.appcode2know.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
    @NotBlank(message = "O campo de usuário ou email deve ser informado")
    private String usernameOrEmail;

    @NotBlank(message = "O campo de senha deve ser informado")
    private String password;
}
