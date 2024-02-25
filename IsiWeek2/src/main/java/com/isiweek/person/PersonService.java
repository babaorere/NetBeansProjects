package com.isiweek.person;

import com.isiweek.lender.Lender;
import com.isiweek.lender.LenderRepository;
import com.isiweek.user.User;
import com.isiweek.user.UserRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final LenderRepository lenderRepository;

    public PersonService(final PersonRepository personRepository,
            final UserRepository userRepository, final LenderRepository lenderRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.lenderRepository = lenderRepository;
    }

    public List<PersonDTO> findAll() {
        final List<Person> persons = personRepository.findAll(Sort.by("id"));
        return persons.stream()
                .map(person -> mapToDTO(person, new PersonDTO()))
                .toList();
    }

    public PersonDTO get(final Long id) {
        return personRepository.findById(id)
                .map(person -> mapToDTO(person, new PersonDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PersonDTO personDTO) {
        final Person person = new Person();
        mapToEntity(personDTO, person);
        return personRepository.save(person).getId();
    }

    public void update(final Long id, final PersonDTO personDTO) {
        final Person person = personRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(personDTO, person);
        personRepository.save(person);
    }

    public void delete(final Long id) {
        personRepository.deleteById(id);
    }

    private PersonDTO mapToDTO(final Person person, final PersonDTO personDTO) {
        personDTO.setId(person.getId());
        personDTO.setDateOfBirth(person.getDateOfBirth());
        personDTO.setGender(person.getGender());
        personDTO.setDateCreated(person.getDateCreated());
        personDTO.setLastUpdated(person.getLastUpdated());
        personDTO.setIdDoc(person.getIdDoc());
        personDTO.setPhoneNumber1(person.getPhoneNumber1());
        personDTO.setPhoneNumber2(person.getPhoneNumber2());
        personDTO.setEmail(person.getEmail());
        personDTO.setCity(person.getCity());
        personDTO.setFirstName(person.getFirstName());
        personDTO.setLastName(person.getLastName());
        personDTO.setState(person.getState());
        personDTO.setAddress(person.getAddress());
        personDTO.setObservations(person.getObservations());
        personDTO.setOccupation(person.getOccupation());
        personDTO.setPhoto(person.getPhoto());
        personDTO.setUser(person.getUser() == null ? null : person.getUser().getId());
        return personDTO;
    }

    private Person mapToEntity(final PersonDTO personDTO, final Person person) {
        person.setDateOfBirth(personDTO.getDateOfBirth());
        person.setGender(personDTO.getGender());
        person.setDateCreated(personDTO.getDateCreated());
        person.setLastUpdated(personDTO.getLastUpdated());
        person.setIdDoc(personDTO.getIdDoc());
        person.setPhoneNumber1(personDTO.getPhoneNumber1());
        person.setPhoneNumber2(personDTO.getPhoneNumber2());
        person.setEmail(personDTO.getEmail());
        person.setCity(personDTO.getCity());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setState(personDTO.getState());
        person.setAddress(personDTO.getAddress());
        person.setObservations(personDTO.getObservations());
        person.setOccupation(personDTO.getOccupation());
        person.setPhoto(personDTO.getPhoto());
        final User user = personDTO.getUser() == null ? null : userRepository.findById(personDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        person.setUser(user);
        return person;
    }

    public boolean idDocExists(final String idDoc) {
        return personRepository.existsByIdDocIgnoreCase(idDoc);
    }

    public boolean emailExists(final String email) {
        return personRepository.existsByEmailIgnoreCase(email);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {

        final ReferencedWarning referencedWarning = new ReferencedWarning();

        final Person person = personRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        final Lender personLender = lenderRepository.findFirstByPerson(person);
        if (personLender != null) {
            referencedWarning.setKey("person.lender.person.referenced");
            referencedWarning.addParam(personLender.getId());
            return referencedWarning;
        }

        return null;
    }

}
