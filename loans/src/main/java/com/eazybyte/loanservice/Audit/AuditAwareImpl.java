package com.eazybyte.loanservice.Audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * Returns the current auditor of the application
     * @return the current auditor
     */

    @Override
    public Optional<String> getCurrentAuditor() {
        // In a real application, you might fetch the current user from the security context.
        // Example Return the current user or a static value for auditing
        return Optional.of("LOAN_MS");
    }
}
