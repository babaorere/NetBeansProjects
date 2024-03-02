package com.isiweek.lender;

import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.user.User;
import com.isiweek.user.UserRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class LenderService {

    private final LenderRepository lenderRepository;
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    public LenderService(final LenderRepository lenderRepository,
            final PersonRepository personRepository,
            final UserRepository userRepository) {
        this.lenderRepository = lenderRepository;
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    public List<LenderDTO> findAll() {
        final List<Lender> lenders = lenderRepository.findAll(Sort.by("id"));
        return lenders.stream()
                .map(lender -> mapToDTO(lender, new LenderDTO()))
                .toList();
    }

    public LenderDTO get(final Long id) {
        return lenderRepository.findById(id)
                .map(lender -> mapToDTO(lender, new LenderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LenderDTO lenderDTO) {
        final Lender lender = new Lender();
        mapToEntity(lenderDTO, lender);
        return lenderRepository.save(lender).getId();
    }

    public void update(final Long id, final LenderDTO lenderDTO) {
        final Lender lender = lenderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(lenderDTO, lender);
        lenderRepository.save(lender);
    }

    public void delete(final Long id) {
        lenderRepository.deleteById(id);
    }

    private LenderDTO mapToDTO(final Lender lender, final LenderDTO lenderDTO) {
        lenderDTO.setId(lender.getId());
        lenderDTO.setMaxLoanAmount(lender.getMaxLoanAmount());
        lenderDTO.setCreditScore(lender.getCreditScore());
        lenderDTO.setDateCreated(lender.getDateCreated());
        lenderDTO.setLastUpdated(lender.getLastUpdated());
        lenderDTO.setObservations(lender.getObservations());
        lenderDTO.setPerson(lender.getPerson() == null ? null : lender.getPerson().getId());
        return lenderDTO;
    }

    private Lender mapToEntity(final LenderDTO lenderDTO, final Lender lender) {
        lender.setMaxLoanAmount(lenderDTO.getMaxLoanAmount());
        lender.setCreditScore(lenderDTO.getCreditScore());
        lender.setDateCreated(lenderDTO.getDateCreated());
        lender.setLastUpdated(lenderDTO.getLastUpdated());
        lender.setObservations(lenderDTO.getObservations());
        final Person person = lenderDTO.getPerson() == null ? null : personRepository.findById(lenderDTO.getPerson())
                .orElseThrow(() -> new NotFoundException("person not found"));
        lender.setPerson(person);
        return lender;
    }

    public boolean personExists(final Long id) {
        return lenderRepository.existsByPersonId(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();

        final Lender lender = lenderRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        final Optional<User> lenderUser = userRepository.findFirstByLender(lender);

        if (lenderUser.isPresent()) {
            referencedWarning.setKey("lender.user.lender.referenced");
            referencedWarning.addParam(lenderUser.get().getId());
            return referencedWarning;
        }

        return null;
    }

}
