package com.project.springdemo.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)

public class UserJson implements Serializable  {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("nick_name")
    private String nickName;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("Url")
    private String url;

   /* @JsonProperty("Image")
    private String image;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    /*public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }*/

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "UserJson{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", enabled=" + enabled +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
