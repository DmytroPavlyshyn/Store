package com.alpha;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private String firstName;
    private String lastName;
    private Integer balance;
    private List<Order> boughtProducts = new ArrayList<>();
    private final int id;
    public Customer(String firstName, String lastName, int balance, int id) {
        if(balance < 0 ){
            throw new RuntimeException("Balance can\'t be negative");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public List<Order> getBoughtProducts() {
        return boughtProducts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", balance=" + balance +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return id == customer.id &&
                Objects.equals(getFirstName(), customer.getFirstName()) &&
                Objects.equals(getLastName(), customer.getLastName()) &&
                Objects.equals(getBalance(), customer.getBalance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getBalance(), id);
    }
}
