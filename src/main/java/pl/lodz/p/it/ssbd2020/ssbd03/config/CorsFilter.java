package pl.lodz.p.it.ssbd2020.ssbd03.config;

import javax.servlet.http.HttpServletResponse;

/**
 * Filtr kontenera pozwający na dostęp do REST api z localhost:3000
 * (serwer deweloperski react.js)
 */
public class CorsFilter {

    public static void addCors(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader(
                "Access-Control-Allow-Credentials", "true");
        response.setHeader(
                "Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");
        response.setHeader(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD");
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
    }
}
