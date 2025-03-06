package examen.nmit07e08.domain.dto.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String nombre;
    private String password;
}
