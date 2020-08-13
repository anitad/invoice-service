package com.invoice.repository.impl;

import com.invoice.entities.EmailSentEntity;
import com.invoice.repository.EmailSentRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component("emailSentRepository")
@Repository
public class EmailSentRepositoryImpl extends AbstractGenericRepositoryImpl<EmailSentEntity> implements EmailSentRepository {
    @Override
    public Class<EmailSentEntity> getEntityClass() {
        return EmailSentEntity.class;
    }
}
