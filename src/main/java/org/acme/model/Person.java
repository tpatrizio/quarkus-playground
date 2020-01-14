package org.acme.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Person
 */
@Entity
@RegisterForReflection
public class Person extends PanacheEntity {

    public String name;
    public LocalDate birth;
    public Status status;
    
    public static List<Person> findAlive(){
        return list("status", Status.Alive);
    }
    
}