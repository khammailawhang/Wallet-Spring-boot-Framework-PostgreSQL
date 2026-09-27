package com.example.demo; // ແຖວທີ 1

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository transactionRepository;

    // 1. API ກວດສອບຍອດເງິນ
    @GetMapping("/{userId}")
    public Wallet getBalance(@PathVariable String userId) {
        return walletRepository.findByUserId(userId)
                .orElseGet(() -> {
                    BigDecimal initialBalance = BigDecimal.ZERO;
                    if ("userA".equals(userId)) initialBalance = BigDecimal.valueOf(500.0);
                    if ("userB".equals(userId)) initialBalance = BigDecimal.valueOf(100.0);
                    return walletRepository.save(new Wallet(userId, initialBalance));
                });
    }

    // 2. API ໂອນເງິນ (ລຶບ @RequestBody ອອກແລ້ວເພື່ອໃຫ້ຮອງຮັບ HTML Form)
    @PostMapping("/transfer")
    @Transactional
    public String transferMoney(TransferRequest request) {
        Wallet fromWallet = walletRepository.findByUserId(request.getFromUserId()).orElse(null);
        Wallet toWallet = walletRepository.findByUserId(request.getToUserId()).orElse(null);

        if (fromWallet == null || toWallet == null) {
            return "ຜິດພາດ: ບໍ່ພົບຂໍ້ມູນຜູ້ໃຊ້!";
        }

        BigDecimal transferAmount = BigDecimal.valueOf(request.getAmount());

        if (fromWallet.getBalance().compareTo(transferAmount) < 0) {
            return "ຜິດພາດ: ຍອດເງິນໃນກະເປົາບໍ່ພໍ!";
        }

        fromWallet.setBalance(fromWallet.getBalance().subtract(transferAmount));
        toWallet.setBalance(toWallet.getBalance().add(transferAmount));

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        transactionRepository.save(new WalletTransaction(
                fromWallet.getWalletId(), "TRANSFER_OUT", transferAmount, "ໂອນໄປຫາ " + request.getToUserId()
        ));

        transactionRepository.save(new WalletTransaction(
                toWallet.getWalletId(), "TRANSFER_IN", transferAmount, "ຮັບເງິນໂອນຈາກ " + request.getFromUserId()
        ));

        return "ໂອນເງິນສຳເລັດແລ້ວ! ໂອນໃຫ້ " + request.getToUserId() + " ຈຳນວນ " + request.getAmount() + " ກີບ";
    }

    // 3. API ດຶງປະຫວັດການເຮັດທຸລະກຳ
    @GetMapping("/{userId}/transactions")
    public List<WalletTransaction> getTransactionHistory(@PathVariable String userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("ບໍ່ພົບກະເປົາເງິນຂອງຜູ້ໃຊ້ນີ້"));

        return transactionRepository.findByWalletIdOrderByCreatedAtDesc(wallet.getWalletId());
    }

    // 4. API ຝາກເງິນ (ລຶບ @RequestBody ອອກແລ້ວເພື່ອໃຫ້ຮອງຮັບ HTML Form)
    @PostMapping("/deposit")
    @Transactional
    public String depositMoney(DepositRequest request) {
        Wallet wallet = walletRepository.findByUserId(request.getUserId()).orElse(null);

        if (wallet == null) {
            return "ຜິດພາດ: ບໍ່ພົບຂໍ້ມູນຜູ້ໃຊ້!";
        }

        BigDecimal depositAmount = BigDecimal.valueOf(request.getAmount());

        if (depositAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return "ຜິດພາດ: ຈຳນວນເງິນຝາກຕ້ອງຫຼາຍກວ່າ 0 ກີບ!";
        }

        wallet.setBalance(wallet.getBalance().add(depositAmount));
        walletRepository.save(wallet);

        transactionRepository.save(new WalletTransaction(
                wallet.getWalletId(), "DEPOSIT", depositAmount, "ຝາກເງິນເຂົ້າກະເປົາອັດຕະໂນມັດ"
        ));

        return "ຝາກເງິນສຳເລັດແລ້ວ! ເພີ່ມເງິນເຂົ້າກະເປົາ " + request.getUserId() + " ຈຳນວນ " + request.getAmount() + " ກີບ (ຮຽບຮ້ອຍ)";
    }
}
