package org.src.todojpa.constants;

import io.jsonwebtoken.SignatureAlgorithm;

public class AuthConstants {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORITY = "Authority";
    public static final long TOKEN_TIME = 60 * 60 * 1000L;
    public static final String JWT_SECRET_KEY = "${jwt.secret.key}";
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private AuthConstants() {
    }
}
