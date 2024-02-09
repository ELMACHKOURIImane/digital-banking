package org.lsi2.ebank_backend.repositorys;

import org.lsi2.ebank_backend.entities.BanckAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BanckAccount , String> {
}
