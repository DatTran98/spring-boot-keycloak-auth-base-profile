package vn.dattb.auth.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthResponse {
    @JsonAlias("access_token")
    private String accessToken;
    @JsonAlias("refresh_token")
    private String refreshToken;

    @JsonAlias("expires_in")
    private Long expiresIn;

    @JsonAlias("token_type")
    private String tokenType;

    @JsonAlias("scope")
    private String scope;

    @JsonAlias("not-before-policy")
    private Long notBeforePolicy;

    @JsonAlias("session_state")
    private String sessionState;

    @JsonAlias("refresh_expires_in")
    private Long refreshExpiresIn;

}
