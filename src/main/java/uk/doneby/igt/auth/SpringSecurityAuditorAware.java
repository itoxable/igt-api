package uk.doneby.igt.auth;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import uk.doneby.igt.model.User;

public class SpringSecurityAuditorAware implements AuditorAware<Long> {

	  public Long getCurrentAuditor() {

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated()) {
	      return 0L;
	    }

	    return ((User) authentication.getPrincipal()).getId();
	  }
	}