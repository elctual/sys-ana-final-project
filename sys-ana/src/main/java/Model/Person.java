package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author elifa
 */
import java.time.LocalDate;
public class Person {
    private int personId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String nationality;
    
    public Person(int personId, String firstName, String lastName, LocalDate dateOfBirth, String nationality) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }
}
