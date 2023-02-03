//package com.project.javaapi.data;
//
//import com.project.javaapi.model.Person;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Optional;
//
//public class PersonData implements UserDetails {
//
//    private final Optional<Person> person;
//
//    public PersonData(Optional<Person> person) {
//        this.person = person;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return person.get().roles;
//    }
//
////    @Override
////    public Collection<? extends GrantedAuthority> getAuthorities() {
////        return new ArrayList<>();
////    }
//
//    @Override
//    public String getPassword() {
//        return person.get().password;
////        return person.get().getPassword();
//    }
//
////    @Override
////    public String getPassword() {
////        return person.orElse(new Person()).getPassword();
////    }
//
//    @Override
//    public String getUsername() {
//        return person.get().username;
////        return person.get().getUsername();
//    }
//
////    @Override
////    public String getUsername() {
////        return person.orElse(new Person()).getUsername();
////    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
