package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    // ເພີ່ມ Method ສໍາລັບຄົ້ນຫາ Wallet ດ້ວຍ userId
    Optional<Wallet> findByUserId(String userId);
}
