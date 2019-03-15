package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;

import java.util.Date;

public class UserLight {
    public UserLight(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setStatus(user.getStatus());
        this.setCreationDate(user.getCreationDate());
        this.setBirthday(user.getBirthday());
    }

    private Long id;

    private String username;

    private UserStatus status;

    private Date creationDate;

    private Date birthday;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date createDate) {
        this.creationDate = createDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
