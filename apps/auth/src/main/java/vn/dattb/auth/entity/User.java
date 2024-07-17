package vn.dattb.auth.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String keycloakId;
}
