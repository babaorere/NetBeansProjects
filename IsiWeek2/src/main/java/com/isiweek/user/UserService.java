package com.isiweek.user;

import com.isiweek.lender.Lender;
import com.isiweek.lender.LenderRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.role.Role;
import com.isiweek.role.RoleRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final LenderRepository lenderRepository;
    private final PersonRepository personRepository;

    public UserService(final UserRepository userRepository, final RoleRepository roleRepository,
            final LenderRepository lenderRepository, final PersonRepository personRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.lenderRepository = lenderRepository;
        this.personRepository = personRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole() == null ? null : user.getRole().getId());
        userDTO.setLender(user.getLender() == null ? null : user.getLender().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        final Role role = userDTO.getRole() == null ? null : roleRepository.findById(userDTO.getRole())
                .orElseThrow(() -> new NotFoundException("role not found"));
        user.setRole(role);
        final Lender lender = userDTO.getLender() == null ? null : lenderRepository.findById(userDTO.getLender())
                .orElseThrow(() -> new NotFoundException("lender not found"));
        user.setLender(lender);
        return user;
    }

    public boolean nameExists(final String name) {
        return userRepository.existsByUsernameIgnoreCase(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Person userPerson = personRepository.findFirstByUser(user);
        if (userPerson != null) {
            referencedWarning.setKey("user.person.user.referenced");
            referencedWarning.addParam(userPerson.getId());
            return referencedWarning;
        }
        return null;
    }

}
