package com.project.springdemo.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "bp_user")
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bp_user_seq")
    @SequenceGenerator(name = "bp_user_seq", sequenceName = "bp_user_id_seq", allocationSize = 1)

    @Column(name = "id")
    private Long id;

    @Column(name = "username" , length=45 , unique = true)
    private String username;

    @Column(name = "password" , length=15 )
    private String password;

    @Column(name = "first_name" , length=45 )
    private String firstName;

    @Column(name = "last_name" , length=45 )
    private String lastName;

    @Column(name = "nick_name" , length = 15)
    private String nickName;

    @Column(name = "url")
    private String url;

    @Lob
    @Type(type="org.hibernate.type.ImageType")
    @Column(name = "image")
    private byte[] image;

    @Column(name = "enabled")
    private boolean enabled=false;

    @Column(name = "business")
    private boolean business=false;

    @Column(name = "tos")
    private boolean tos=false;

    @Column(name = "token")
    private String token;

    @Column(name = "verify_code")
    private String verifyCode;

    /*@OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    @JsonIgnore
    private List<Request> requests;*/



    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    public List<UserAuthority> userAuthorityList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isBusiness() {
        return business;
    }

    public void setBusiness(boolean business) {
        this.business = business;
    }

   /* public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }*/

    /*public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }*/

    public List<UserAuthority> getUserAuthorityList() {
        return userAuthorityList;
    }

    public void setUserAuthorityList(List<UserAuthority> userAuthorityList) {
        this.userAuthorityList = userAuthorityList;
    }

    public boolean isTos() {
        return tos;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public void setTos(boolean tos) {
        this.tos = tos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*List<GrantedAuthority> authorities=new ArrayList<>();
        for (UserAuthority userAuthority:userAuthorityList){
            authorities.addAll(userAuthority.getAuthority());
        }
        return authorities;*/
        return userAuthorityList.stream().map(UserAuthority::getAuthority).collect(Collectors.toList());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}
