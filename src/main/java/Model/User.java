package Model;

import java.awt.*;

public class User {

    String userName;
    String password;
    String firstName;
    String lastName;
    String birthday;
    String address;
    String email;
    Image profilePicture;

    public User(String userName, String password, String firstName, String lastName, String birthday, String address, String email, Image profilePicture) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.profilePicture = profilePicture;
    }


    //<editor-fold desc="Getters">
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    //</editor-fold>


    //<editor-fold desc="Setters">
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    //</editor-fold>
}
