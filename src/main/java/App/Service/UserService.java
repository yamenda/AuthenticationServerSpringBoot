package App.Service;

import App.Repository.UserRepository;
import App.Model.Users;
import App.Model.customUser;
import App.Util.IterableConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public Users insert(Users user) {
        return this.userRepository.save(user);
    }

    public List<Users> getAll() {
        return IterableConverter.toList(this.userRepository.findAll());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<Users> userList = IterableConverter.toList(userRepository.findAll());

        Users result = null;
        for(Users user : userList) {
            if(user.getUsername().equals(username)){
                result = user;
            }
        }
        Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_SYSTEMADMIN");
        grantedAuthoritiesList.add(grantedAuthority);

        customUser res = new customUser(result, grantedAuthoritiesList);

        return res;
    }
}
