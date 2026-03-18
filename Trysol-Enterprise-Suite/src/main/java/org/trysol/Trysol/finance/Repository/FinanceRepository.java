package org.trysol.Trysol.finance.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trysol.Trysol.finance.entity.FinanceRecord;

public interface FinanceRepository extends JpaRepository<FinanceRecord,Long> {
}
