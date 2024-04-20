package com.myproject.blog.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min =4, message = "User Name must be min of 4 charectors")
    private String name;
    @NotEmpty
    @Size(min =3, max =10, message = "Password must be min of 3 chars and maximum of 10 chars")
    private String password;
    @Email(message = "Email address is not valid")
    private String email;
    @NotEmpty
    private String about;
}
