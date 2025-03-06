package examen.nmit07e08.domain.dto.security;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioJwt {
    private String accessToken;
    private String tokenType;
    private Long id;
    private String nombre;
    private String rol;
}
