package ca.uoit.dmancini.a100157944_lab6;


import java.io.Serializable;

public class Contact  implements Serializable {
    private String firstName, lastName, phone;
    private int _id;

    public Contact(int Id, String firstName, String lastName, String phone){
        this._id = Id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int get_id() {
        return _id;
    }

    @Override
    public String toString(){
        return _id + ", " + lastName + ", " + firstName + ", " + phone;
    }
}