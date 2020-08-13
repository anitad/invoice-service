package com.invoice.rest.models;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Customer {
    private long id;
    private String firstName;
    private String lastName;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String emailId;
    @NotNull(message = "Address cannot be null")
    private List<Address> addresses;

    public Customer () {}

    public Customer(long id, String firstName, String lastName, String emailId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
