package com.isiweek;

import com.isiweek.role.RoleService;
import com.isiweek.status.StatusService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.isiweek")
public class IsiweekApplication {

    private final RoleService roleService;
    private final StatusService statusService;

    @Autowired
    public IsiweekApplication(final RoleService inRoleService, final StatusService inStatusService) {
        this.roleService = inRoleService;
        this.statusService = inStatusService;
    }

    public static void main(final String[] args) {
        SpringApplication.run(IsiweekApplication.class, args);
    }

    @PostConstruct
    public void persistAllRoles() {
        roleService.persistAll();
    }

    @PostConstruct
    public void persistAllStatus() {
        statusService.persistAll();
    }
}
