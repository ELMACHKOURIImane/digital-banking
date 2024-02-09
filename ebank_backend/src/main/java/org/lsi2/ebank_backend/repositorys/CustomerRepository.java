package org.lsi2.ebank_backend.repositorys;

import org.lsi2.ebank_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.LinkOption;

public interface CustomerRepository extends JpaRepository<Customer , Long> {
}
