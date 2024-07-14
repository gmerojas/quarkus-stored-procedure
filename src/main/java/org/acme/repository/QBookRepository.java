package org.acme.repository;

import io.quarkiverse.groovy.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.QBook;

@ApplicationScoped
public class QBookRepository implements PanacheRepository<QBook> {
}
