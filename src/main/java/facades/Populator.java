/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import entities.Person;

import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;


public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = PersonFacade.getFacadeExample(emf);
        pf.create(new PersonDTO(new Person("Anders", "Andersen", "15151515")));
        pf.create(new PersonDTO(new Person("Bo", "Boesen", "25252525")));
        pf.create(new PersonDTO(new Person("Carl", "Carlsen", "35353535")));

    }


    
    public static void main(String[] args) {
        populate();
    }

}
