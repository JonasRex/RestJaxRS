package facades;

import dtos.PersonDTO;

import java.util.List;

public interface IPersonFacade {
    public PersonDTO addPerson(String fName, String lName, String phone);
    public PersonDTO deletePerson(long id);
    public PersonDTO getPerson(long id);
    public List<PersonDTO> getAllPersons();
    public PersonDTO editPerson(PersonDTO p);
}


