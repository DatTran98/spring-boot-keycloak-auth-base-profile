package vn.dattb.auth.service;

import vn.dattb.auth.dto.AuthRequest;
import vn.dattb.auth.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
}
