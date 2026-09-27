package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // ເພີ່ມ Import ຕົວນີ້
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/wallet") // ເຂົ້າໃຊ້ງານຜ່ານ http://localhost:8080/wallet
public class WalletWebController {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository transactionRepository;

    @Autowired
    private WalletController walletController; // ເອີ້ນໃຊ້ Logic ເກົ່າທີ່ທ່ານຂຽນໄວ້

    // 1. ໜ້າຫຼັກ: ສະແດງຂໍ້ມູນ Dashboard ຂອງ User
    @GetMapping
    public String showDashboard(@RequestParam(defaultValue = "userA") String userId, Model model) {
        // ດຶງຂໍ້ມູນ Wallet ໂດຍໃຊ້ Logic ເກົ່າຂອງທ່ານ
        Wallet wallet = walletController.getBalance(userId);

        // ດຶງປະຫວັດການເຮັດທຸລະກຳ
        List<WalletTransaction> transactions = transactionRepository.findByWalletIdOrderByCreatedAtDesc(wallet.getWalletId());

        // ສົ່ງຂໍ້ມູນໄປຫາ Thymeleaf HTML
        model.addAttribute("userId", userId);
        model.addAttribute("balance", wallet.getBalance());
        model.addAttribute("transactions", transactions);

        return "wallet-dashboard"; // ຈະໄປເອີ້ນໄຟລ໌ wallet-dashboard.html
    }

    // 2. ຮັບຂໍ້ມູນການຝາກເງິນຈາກຟອມ HTML (ປ່ຽນເປັນ @RequestParam ເພື່ອຄວາມຊົວແນ່ນອນ)
    @PostMapping("/web-deposit")
    public String webDeposit(@RequestParam("userId") String userId,
                             @RequestParam("amount") double amount,
                             RedirectAttributes redirectAttributes) {

        // ສ້າງ Object ໂດຍສົ່ງຄ່າເຂົ້າໄປໃນ Constructor ໂດຍກົງຕາມທີ່ Class ຕ້ອງການ
        DepositRequest request = new DepositRequest(userId, amount);

        // ເອີ້ນໃຊ້ API ຝາກເງິນເພື່ອປະມວນຜົນ ແລະ ບັນທຶກລົງ PostgreSQL
        String result = walletController.depositMoney(request);

        // ໃຊ້ FlashAttribute ເພື່ອສົ່ງຂໍ້ຄວາມ Alert ໄປຫາໜ້າ HTML ໂດຍກົງ
        redirectAttributes.addFlashAttribute("messageAlert", result);

        return "redirect:/wallet?userId=" + userId;
    }

    // 3. ຮັບຂໍ້ມູນການໂອນເງິນຈາກຟອມ HTML (ປ່ຽນເປັນ @RequestParam ເພື່ອຄວາມຊົວແນ່ນອນ)
    @PostMapping("/web-transfer")
    public String webTransfer(@RequestParam("fromUserId") String fromUserId,
                              @RequestParam("toUserId") String toUserId,
                              @RequestParam("amount") double amount,
                              RedirectAttributes redirectAttributes) {

        // ສ້າງ Object ໂດຍສົ່ງຄ່າເຂົ້າໄປໃນ Constructor
        TransferRequest request = new TransferRequest(fromUserId, toUserId, amount);

        // ເອີ້ນໃຊ້ API ໂອນເງິນ
        String result = walletController.transferMoney(request);

        // ໃຊ້ FlashAttribute ເພື່ອສົ່ງຂໍ້ຄວາມ Alert ໄປຫາໜ້າ HTML ໂດຍກົງ
        redirectAttributes.addFlashAttribute("messageAlert", result);

        return "redirect:/wallet?userId=" + fromUserId;
    }
}
