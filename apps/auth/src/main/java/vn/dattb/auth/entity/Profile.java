package vn.dattb.auth.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Profile {
    private Long id;
    private String email;
    private String phone;
    private Long userId;
}
