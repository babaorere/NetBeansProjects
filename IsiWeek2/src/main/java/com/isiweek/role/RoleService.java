package com.isiweek.role;

import com.isiweek.user.User;
import com.isiweek.user.UserRepository;
import com.isiweek.exceptions.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleService(final RoleRepository roleRepository, final UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("id"));
        return roles.stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public RoleDTO get(final Long id) {
        return roleRepository.findById(id)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getId();
    }

    public void update(final Long id, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final Long id) {
        roleRepository.deleteById(id);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setName(roleDTO.getName());
        return role;
    }

    public boolean nameExists(final String name) {
        return roleRepository.existsByName(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        final Optional<User> roleUser = userRepository.findFirstByRole(role);

        if (roleUser.isPresent()) {
            referencedWarning.setKey("role.user.role.referenced");
            referencedWarning.addParam(roleUser.get().getId());
            return referencedWarning;
        }

        return null;
    }

    public void persistAll() {
        List<RoleEnum> allRoles = Arrays.asList(RoleEnum.values());

        for (RoleEnum roleEnum : allRoles) {
            persistRole(roleEnum);
        }
    }

    private void persistRole(RoleEnum roleEnum) {
        String roleName = roleEnum.name();

        Optional<Role> existingRoleOptional = roleRepository.findByName(roleName);

        if (existingRoleOptional.isEmpty()) {
            Role newRole = new Role();
            newRole.setName(roleName);
            roleRepository.save(newRole);
        }
    }

}
