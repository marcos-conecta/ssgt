package br.com.oxy.ssgt.domain.entities.user;

public class UserFactory {

    private User user;

    public User createUserNameEmailPassword(String name, String email, String password) {
        this.user = new User(null, name, email, password);
        return this.user;
    }
}
