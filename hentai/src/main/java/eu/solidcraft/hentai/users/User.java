package eu.solidcraft.hentai.users;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "users")
public class User {
    @Id
    private String username;

    private String password;

    private int bonusPoints = 0;

    User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getBonusPoints() {
        return bonusPoints;
    }

    public void addBonusPoints(Integer bonusPoints) {
        this.bonusPoints += bonusPoints;
    }
}
