//package org.example.taskmanagementsystem.model;
//
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.List;
//
//@Entity
//@Table(name = "authors")
//@Data
//public class Authors {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "last_name")
//    private String lastName;
//
//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "password")
//    private String password;
//
//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<Task> tasks;
//
//}
