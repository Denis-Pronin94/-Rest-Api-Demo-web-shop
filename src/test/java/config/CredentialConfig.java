package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/credential.properties")
public interface CredentialConfig extends Config {
    String login();
    String password();
    String authCookieName();
    String selenoidLogin();
    String selenoidPassword();
}
