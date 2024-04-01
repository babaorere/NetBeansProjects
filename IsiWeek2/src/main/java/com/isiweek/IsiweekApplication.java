package com.isiweek;

import com.isiweek.role.RoleService;
import com.isiweek.status.StatusService;
import jakarta.annotation.PostConstruct;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

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

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        engine.addDialect(new LayoutDialect());
        return engine;
    }

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(0);
        resolver.setCheckExistence(true);
        return resolver;
    }

}
