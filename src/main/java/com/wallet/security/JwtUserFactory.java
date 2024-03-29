package com.wallet.security;

import com.wallet.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getEmail(), user.getPassword());
    }

//    private static List<GrantedAuthority> createGrantedAuthorities(RoleEnum role) {
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority(role.toString()));
//        return authorities;
//    }

}
