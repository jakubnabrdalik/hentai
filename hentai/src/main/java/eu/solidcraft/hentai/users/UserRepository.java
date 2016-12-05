package eu.solidcraft.hentai.users;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.google.common.collect.Lists.newArrayList;

@RepositoryRestResource(exported = false)
public interface UserRepository extends UserDetailsService, Repository<User, String> {

    User findOne(String username);

    User save(User user);

    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findOne(username);
        if(user == null) {
            throw new UsernameNotFoundException("No user with username " + username);
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), newArrayList());
    }
}
