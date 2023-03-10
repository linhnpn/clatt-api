package container.code.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import container.code.data.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserPrinciple implements UserDetails {
    private Integer id;
    private String username;

    private String fullname;
    private String email;
    private Instant expiredDate;
    @JsonIgnore
    private String password;
    private String profilePicture;
    private String gender;
    public static UserPrinciple build(Account user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return UserPrinciple.builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .profilePicture(user.getProfilePicture())
                .grantedAuthorities(authorities)
                .gender(user.getGender())
                .build();
    }


    @JsonIgnore
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
