package pl.lodz.p.it.ssbd2020.ssbd03.config;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.security.Security;

import static pl.lodz.p.it.ssbd2020.ssbd03.security.Constants.*;


/**
 * Klasa konfiguracyjna usługi REST
 */
@ApplicationPath(value = "/api")
public class ApplicationConfig extends Application {
    public ApplicationConfig() {
        Security.addProvider(new BouncyCastleProvider());
    }
}
