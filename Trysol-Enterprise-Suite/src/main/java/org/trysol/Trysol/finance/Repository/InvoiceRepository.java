package org.trysol.Trysol.finance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trysol.Trysol.finance.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
