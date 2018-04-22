package App.Controller;


import App.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private String clientId = "app_user";
    private String clientSecret = "123123123";

    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEA4Sxkug1sm7S1zJa/IzRiK4CTmD9FuR+C80A4vj2Ne4CvLpXN\n" +
            "F7wfxIlFLY9QBSiLRDw4XnUzCquhQg6rJcy1wm3W31lK9Imm55qoBrE3y/6i9+Rk\n" +
            "UtNFzQtPJ0CrZg9W+rzYqj00cIjYd0bp702kICMUv7wryVHX+P+uwkZf6BiX3ABn\n" +
            "ARZNPpTSk1iufXk6G8+ElHpzUcxaElYJd+TMmGKTQGP4gB7BTQOyQUarAnlZLnE9\n" +
            "UVGnpz+Pkf2Kt+i3TO7Db8a+ogoa599kyhGI9R8L/1l8wRRydPagIfQ4cvs0fgAl\n" +
            "sa1I9q627zH2Fbs+i3klTHbO7C+xGgfuTWa3jwIDAQABAoIBAAKSzE/oCV66MP22\n" +
            "l1aiqaCPpqAt0PEtsARxrnRWJcDMlrP0mFR2HPLVEspTgfmdHgJYodhKhJvRnFUx\n" +
            "77jN3yHquXLyBU5IKHUrKlJxQg79yQUGQjNEIN8YCv6K2aqf8EGLWPqRGoFSvw6V\n" +
            "6+SFQaPwYrNSRjnVyp8Y5Plpbn0rjUvSOUtOwpomTFe6YBhmlgyIBjf3E7TJoOxb\n" +
            "+P3ia8ufLyKXpjd6fjAb7XG43pjfM+Xi7H7/fgkTaP7/9fbrTPeeqgSEr4QnPhJX\n" +
            "K5DLLYYSR+0cFaa9aSpOghm+7aX0TJZWc5MuS0srdI9urJguxFkupDw7+Ir46b33\n" +
            "3HjUyhkCgYEA8VCgyDtccUuKlYjdO9tWNjWdkXiQfxMCKXYPVs7ofw4qsplG2pD0\n" +
            "gvrvozyqHKLnEuQ+S26JWe/e7UyYxrT47PGNnskpB7tIAIA7wbxmHmJZSwI8cWLX\n" +
            "0+asvgIR12on1l8NjZgY8IY1qmMashbk8PgccnpoHX4svqrgDMI9+4sCgYEA7uBN\n" +
            "H8l3lTrZiUA9TXRyKBa0Qblgqs6RjT1LXOt+Ssp7SwaL6/1HEVcqbXi2bEhl669t\n" +
            "8ZIfykZvTmSC2vPOrP2Dciyy5MGV73thXKg+9fAlzCJdYeaW4lOczIFIx/P+lvtj\n" +
            "ahWF1AIpKaNAnOjWzhsOSUALAnQCBO5v2RqfBI0CgYB0Wtwrr/Oy7Sm1UHjcE7ZO\n" +
            "DSwcqHWD0DaKp1JeGQYhvUL5Os7fNGWxgi1qGCYuCJ5Cjas70+CGbRKgeP5xUXF1\n" +
            "TkNq/LO79qGRHEOz9pOGswU7FajvQymuEtvYAkd4BseOpeZzD5N25j+aGI8Wr9NU\n" +
            "HU4aJMtu7RfGF75QaJhaYQKBgAytTqp/MJ90zhKgnCA+K8jA27QZOG4YJFVQiW1W\n" +
            "+g7SDBUBNe/CM0FSVOgWXegFN5N4bedCD/nWHxYT83XXNoKruYwXny7QrzpUW+hk\n" +
            "7WJz6i2ZLUeCy0MbmNipi8lAw6QTl0UL+1wy+ShCh61MFgo+GVX91he9PFYyqTDm\n" +
            "XsYJAoGAV0FWkzDTlm3aHp0C4BwrxTaY4od26XGl+TIpDJrOtncpCT+BUmkKtzOn\n" +
            "NFUIBCmH08po8BX5s12lH2jL/ddC3sB32KyLRQu7UjVpGGKBalrTT96DzwC4X+y+\n" +
            "OSohbA1UEzjOg1EBDj4w8hnozRtjQ/LoZWcnvFQWM3Gyw6I0Oz8=\n" +
            "-----END RSA PRIVATE KEY-----";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4Sxkug1sm7S1zJa/IzRi\n" +
            "K4CTmD9FuR+C80A4vj2Ne4CvLpXNF7wfxIlFLY9QBSiLRDw4XnUzCquhQg6rJcy1\n" +
            "wm3W31lK9Imm55qoBrE3y/6i9+RkUtNFzQtPJ0CrZg9W+rzYqj00cIjYd0bp702k\n" +
            "ICMUv7wryVHX+P+uwkZf6BiX3ABnARZNPpTSk1iufXk6G8+ElHpzUcxaElYJd+TM\n" +
            "mGKTQGP4gB7BTQOyQUarAnlZLnE9UVGnpz+Pkf2Kt+i3TO7Db8a+ogoa599kyhGI\n" +
            "9R8L/1l8wRRydPagIfQ4cvs0fgAlsa1I9q627zH2Fbs+i3klTHbO7C+xGgfuTWa3\n" +
            "jwIDAQAB\n" +
            "-----END PUBLIC KEY-----";


    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {
        configurer.authenticationManager(authenticationManager).tokenStore(tokenStore()).accessTokenConverter(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthentiacated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientId).secret(clientSecret).accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000).scopes("read", "write").authorizedGrantTypes("password", "refresh_token");
    }

}