package com.hobbymeetingapp.server.services;

import com.hobbymeetingapp.server.models.Member;
import com.hobbymeetingapp.server.models.MyUserDetails;
import com.hobbymeetingapp.server.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository members;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = members.findByEmail(username);
        if (!member.isPresent())
            throw new UsernameNotFoundException("");
        return member.map(MyUserDetails::new).get();
    }
}
