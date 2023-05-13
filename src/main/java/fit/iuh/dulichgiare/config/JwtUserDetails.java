package fit.iuh.dulichgiare.config;

import java.util.Collection;

import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private static final long serialVersionUID = -2810212241360878982L;

    public final Long id;

    public JwtUserDetails(final Long id, final String username, final String hash,
            final Collection<? extends GrantedAuthority> authorities) {
        super(username, hash, authorities);
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(id);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        JwtUserDetails other = (JwtUserDetails) obj;
        return Objects.equals(id, other.id);
    }

}
