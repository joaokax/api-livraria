package com.project.javaapi.services;

import com.project.javaapi.model.Person;
import com.project.javaapi.model.RoleModel;
import com.project.javaapi.repository.PersonRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return new User(person.getUsername(), person.getPassword(), true, true, true,true, getAuthority(person));
    }

    private Collection<? extends GrantedAuthority> getAuthority(Person person) {
        List<RoleModel> roles = person.getRoles();
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

}
