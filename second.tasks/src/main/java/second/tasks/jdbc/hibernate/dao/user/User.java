package second.tasks.jdbc.hibernate.dao.user;


import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users")
@FilterDefs({
        @FilterDef(name = "userNameFilter", parameters = {@ParamDef(name = "name", type = "string")}),
        @FilterDef(name = "userSurnameFilter", parameters = {@ParamDef(name = "surname", type = "string")}),
        @FilterDef(name = "userNicknameFilter", parameters = {@ParamDef(name = "nickname", type = "string")}),
        @FilterDef(name = "userPasswordFilter", parameters = {@ParamDef(name = "password", type = "string")}),
})
@Filters({
        @Filter(name = "userNameFilter", condition = ":name = name"),
        @Filter(name = "userSurnameFilter", condition = ":surname = surname"),
        @Filter(name = "userNicknameFilter", condition = ":nickname = nickname"),
        @Filter(name = "userPasswordFilter", condition = ":password = password"),
})
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String surname;
    private String nickname;
    private String password;
    private LocalDate registration_date;

    public User() {

    }

    public User(String name, String surname, String nickname, String password, LocalDate localDate) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.password = password;
        this.registration_date = localDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(nickname, user.nickname) &&
                Objects.equals(registration_date, user.registration_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, nickname, registration_date);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", localDate=" + registration_date +
                '}';
    }
}
