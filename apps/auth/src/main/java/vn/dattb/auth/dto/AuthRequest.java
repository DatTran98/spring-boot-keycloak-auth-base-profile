package vn.dattb.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthRequest {

    private String username;
    private String password;
}
