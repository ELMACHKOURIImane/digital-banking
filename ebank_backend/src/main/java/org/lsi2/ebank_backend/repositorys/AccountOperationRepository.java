package org.lsi2.ebank_backend.repositorys;

import org.lsi2.ebank_backend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation ,Long > {

    List<AccountOperation> findByBanckAccountId(String accountId) ;
    Page<AccountOperation> findByBanckAccountId(String accountId , Pageable pageable) ;
}
