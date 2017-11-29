package com.jeffersonlupinacci.contas.multitennant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
    
     @Override
     public String resolveCurrentTenantIdentifier() {
         return SuporteDeContexto.getCurrentTenantIdentifier();
     }
    
     @Override
     public boolean validateExistingCurrentSessions() {
         return true;
     }
}