package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    // ຄົ້ນຫາປະຫວັດທຸລະກຳທັງໝົດຂອງ Wallet ນັ້ນໆ ໂດຍລຽງຈາກໃໝ່ຫາເກົ່າ
    List<WalletTransaction> findByWalletIdOrderByCreatedAtDesc(Long walletId);
}
