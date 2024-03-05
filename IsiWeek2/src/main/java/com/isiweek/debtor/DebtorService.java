package com.isiweek.debtor;

import com.isiweek.user.User;
import com.isiweek.user.UserRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DebtorService {

    private final DebtorRepository debtorRepository;
    private final UserRepository userRepository;

    public DebtorService(final DebtorRepository debtorRepository,
            final UserRepository userRepository) {
        this.debtorRepository = debtorRepository;
        this.userRepository = userRepository;
    }

    public List<DebtorDTO> findAll() {
        final List<Debtor> debtors = debtorRepository.findAll(Sort.by("id"));
        return debtors.stream()
                .map(debtor -> mapToDTO(debtor, new DebtorDTO()))
                .toList();
    }

    public DebtorDTO get(final Long id) {
        return debtorRepository.findById(id)
                .map(debtor -> mapToDTO(debtor, new DebtorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DebtorDTO debtorDTO) {
        final Debtor debtor = new Debtor();
        mapToEntity(debtorDTO, debtor);
        return debtorRepository.save(debtor).getId();
    }

    public void update(final Long id, final DebtorDTO debtorDTO) {
        final Debtor debtor = debtorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(debtorDTO, debtor);
        debtorRepository.save(debtor);
    }

    public void delete(final Long id) {
        debtorRepository.deleteById(id);
    }

    private DebtorDTO mapToDTO(final Debtor debtor, final DebtorDTO debtorDTO) {
        debtorDTO.setId(debtor.getId());
        debtorDTO.setName(debtor.getName());
        return debtorDTO;
    }

    private Debtor mapToEntity(final DebtorDTO debtorDTO, final Debtor debtor) {
        debtor.setName(debtorDTO.getName());
        return debtor;
    }

    public boolean nameExists(final String name) {
        return debtorRepository.existsByNameIgnoreCase(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {

        final ReferencedWarning referencedWarning = new ReferencedWarning();

        final Debtor debtor = debtorRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        final Optional<User> debtorUser = userRepository.findFirstByDebtor(debtor);

        if (debtorUser.isPresent()) {
            referencedWarning.setKey("debtor.user.debtor.referenced");
            referencedWarning.addParam(debtorUser.get().getId());
            return referencedWarning;
        }

        return null;
    }

}
