package App.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

public class customUser extends User {

    public Collection<GrantedAuthority> getGrantedAuthoritiesList() {
        return grantedAuthoritiesList;
    }

    public void setGrantedAuthoritiesList(Collection<GrantedAuthority> grantedAuthoritiesList) {
        this.grantedAuthoritiesList = grantedAuthoritiesList;
    }

    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();

    public customUser(Users user, Collection<GrantedAuthority> grantedAuthoritiesList) {
        super(user.getUsername(), user.getPassword(), grantedAuthoritiesList);

        this.grantedAuthoritiesList = grantedAuthoritiesList;

    }
}
