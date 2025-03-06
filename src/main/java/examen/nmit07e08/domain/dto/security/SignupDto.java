package examen.nmit07e08.domain.dto.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    private String nombre;
    private String dni; 
    private String email; 
    private String password;
}
