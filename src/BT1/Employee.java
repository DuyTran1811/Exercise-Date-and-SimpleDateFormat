package BT1;

import java.util.Date;

public class Employee {
    private static int nextId = 1;
    private String id;
    private String fullName;
    private String address;
    private Date dateOfBirth;
    private String phoneNumber;
    private float salary;
    private float experience;

    public Employee() {
        setId();
        fullName = "";
        address = "";
        dateOfBirth = new Date();
        phoneNumber = "";
        salary = 0;
        experience = 0;
    }

    public Employee(String id) {
        if (id == null) {
            setId();
        } else {
            this.id = id;
        }
    }

    public Employee(String id, String fullName,
                    String address, Date dateOfBirth,
                    String phoneNumber, float salary, float experience) {
        this(id);
        this.fullName = fullName;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.experience = experience;
    }

    public static void setNextId(int nextId) {
        Employee.nextId = nextId;
    }

    public void setId() {
        this.id = "0" + nextId;
        nextId++;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public float getSalary() {
        return salary;
    }

    public float getExperience() {
        return experience;
    }
}
