package facades;

import dtos.PersonDTO;
import entities.Person;

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
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
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
            long personCount = (long)em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return personCount;
        }finally{  
            em.close();
        }
    }
    

    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getFacadeExample(emf);
        fe.getAllPersons().forEach(dto->System.out.println(dto));
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) {
        return null;
    }

    @Override
    public PersonDTO deletePerson(long id) {
        return null;
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
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = query.getResultList();
        return PersonDTO.getDtos(persons);
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) {
        return null;
    }
}
