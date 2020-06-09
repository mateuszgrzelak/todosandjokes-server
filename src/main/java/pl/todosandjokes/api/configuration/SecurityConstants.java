package pl.todosandjokes.api.configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;


public class SecurityConstants {
    public static SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    public static Key SECRET_KEY = Keys.secretKeyFor(ALGORITHM);
    public static final long EXPIRATION_TIME = 1_800_000; // 30 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/registration";
    public static final String SIGN_IN_URL = "/login";

}
