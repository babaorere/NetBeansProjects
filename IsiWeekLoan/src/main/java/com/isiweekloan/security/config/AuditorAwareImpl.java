package com.isiweekloan.security.config;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.AuditorAware;

/**
 *
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    // Default constructor
    public AuditorAwareImpl() {
        // TODO: Add constructor logic here.
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        System.out.println("\ncom.isiweekloan.security.config.AuditorAwareImpl getCurrentAuditor Not supported yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
