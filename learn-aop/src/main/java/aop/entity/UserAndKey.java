package aop.entity;

import java.io.Serializable;

public class UserAndKey implements Serializable {
    private static final long serialVersionUID = -7788905696730486566L;
    User user;
    String sm4Key;

    public UserAndKey() {
    }

    public UserAndKey(User user, String sm4Key) {
        this.user = user;
        this.sm4Key = sm4Key;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSm4Key() {
        return sm4Key;
    }

    public void setSm4Key(String sm4Key) {
        this.sm4Key = sm4Key;
    }
}
