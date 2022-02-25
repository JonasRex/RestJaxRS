package facades;

import dtos.PersonDTO;
import entities.Person;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade{

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    

    public static PersonFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public PersonDTO create(PersonDTO pDTO){
        Person person = new Person(pDTO.getfName(), pDTO.getlName(), pDTO.getPhone());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }


    public long getPersonCount(){
        EntityManager em = getEntityManager();
        try{
            return (long)em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
        }finally{  
            em.close();
        }
    }
    

    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getFacadeExample(emf);
        fe.getAllPersons().forEach(System.out::println);
    }

    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        EntityManager em = emf.createEntityManager();
        try {
            Person person = new Person(personDTO.getfName(), personDTO.getlName(), personDTO.getPhone());

            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();

            return new PersonDTO(person);

        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO deletePerson(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Person p = em.find(Person.class, id);
            em.getTransaction().begin();

            if(p != null){
                em.remove(p);
                em.getTransaction().commit();
            } else {
                return null;
            }
            return new PersonDTO(p);
        } finally {
            em.close();
        }
    }


    //TODO: Add Exception
    @Override
    public PersonDTO getPerson(long id) { //throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
//        if (person == null)
//            throw new PersonNotFoundException("The Person entity with ID: "+id+" Was not found");
        return new PersonDTO(person);
    }





    @Override
    public List<PersonDTO> getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> persons = query.getResultList();
            return PersonDTO.getDtos(persons);
        } finally {
            em.close();
        }
    }


    @Override
    public PersonDTO editPerson(PersonDTO p) {
        if (p.getId() == 0)
            throw new IllegalArgumentException("No Parent can be updated when id is missing");

        EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();

            Person person = em.find(Person.class, p.getId());
            person.setFirstName(p.getfName());
            person.setLastName(p.getlName());
            person.setPhone(p.getPhone());
            em.merge(person);

            em.getTransaction().commit();
            return p;

    }
}
