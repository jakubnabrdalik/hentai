package eu.solidcraft.infrastructure.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.springframework.util.StringUtils.hasText;

public class CurrentUserGetter {

    private String getSignedInUserName() {
        String loggedUser = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if(context != null) {
            Authentication authentication = context.getAuthentication();
            if (authentication != null) {
                loggedUser = authentication.getName();
            }
        }
        return loggedUser;
    }

    public String getSignedInUserNameOrAnonymous() {
        String signedInUserName = getSignedInUserName();
        return (hasText(signedInUserName)) ? getSignedInUserName() : "anonymous";
    }
}
