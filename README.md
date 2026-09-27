# 🚀 Spring Boot & React Decoupled E-Wallet System v2.0

ລະບົບຈຳລອງກະເປົາເງິນອິເລັກທຣອນິກ (Core Wallet System) ທີ່ຖືກອອກແບບດ້ວຍໂຄງສ້າງແຍກສ່ວນ (**Decoupled Architecture / Monorepo**) [໑]. ໂດຍຝັ່ງ Backend ພັດທະນາດ້ວຍ **Spring Boot (RESTful APIs)** ເຊື່ອມຕໍ່ຖານຂໍ້ມູນ **PostgreSQL** ແບບມີສະຖຽນລະພາບ [໑], ແລະ ຝັ່ງ Frontend ພັດທະນາດ້ວຍ **React (Vite) + Tailwind CSS** ລະດັບພຣີມ່ຽມ ພ້ອມລະບົບຄວາມປອດໄພໃນການເຂົ້າລະຫັດຜ່ານ.

---

## 🛠️ ຟີເຈີຫຼັກຂອງລະບົບ (Core Features)

### 1. 🔐 ລະບົບ Login ແທ້ຜ່ານ PostgreSQL (Secure Authentication) — *New!*
ລະບົບເຂົ້າສູ່ລະບົບຈິງ ໂດຍມີການກວດສອບ User ID ແລະ ລະຫັດຜ່ານ ດຶງຂໍ້ມູນຈິງຈາກຖານຂໍ້ມູນ PostgreSQL [໑]. ລະບົບມີຄວາມປອດໄພສູງ ໂດຍການນຳໃຊ້ **BCrypt Password Hashing** ໃນການເຂົ້າລະຫັດຜ່ານ ຫາກກອກລະຫັດຜິດ ລະບົບຈະແຈ້ງເຕືອນທັນທີ. *(ລະຫັດຜ່ານເລີ່ມຕົ້ນສໍາລັບການໃຊ້ງານຄັ້ງທໍາອິດແມ່ນ `123456`)*.

### 2. 🔍 ກວດສອບຍອດເງິນ (Check Balance)
ລະບົບສາມາດກວດສອບຍອດເງິນຫຼ້າສຸດຂອງຜູ້ໃຊ້ທີ່ເຂົ້າສູ່ລະບົບ (Session) ໂດຍດຶງຂໍ້ມູນຈິງຈາກຖານຂໍ້ມູນ PostgreSQL ແບບ Real-time [໑].

### 3. 💵 ຝາກເງິນ/ເຕີມເງິນ (Deposit)
ລະບົບຮອງຮັບການຝາກເງິນເຂົ້າກະເປົາເງິນຂອງຕົນເອງ ຜ່ານການສົ່ງຂໍ້ມູນແບບ Form Parameter ໄປປະມວນຜົນ ພ້ອມບັນທຶກປະຫວັດລົງ Ledger ອັດຕະໂນມັດ.

### 4. 💸 ໂອນເງິນແບບປອດໄພ (Secure Transfer)
ລະບົບໂອນເງິນລະຫວ່າງບຸກຄົນ (Peer-to-Peer) ພ້ອມກົນໄກຄວາມປອດໄພສູງ ຫາກມີຂັ້ນຕອນໃດໜຶ່ງຜິດພາດ ລະບົບຈະດຶງເງິນຄືນ (**Rollback / `@Transactional`**) ທັນທີ ເພື່ອປ້ອງກັນຂໍ້ມູນເສຍຫາຍ.

### 5. 📊 ປະຫວັດການເຮັດທຸລະກຳ (Transaction History)
ດຶງປະຫວັດການເງິນ (Ledger History) ທັງໝົດອອກມາສະແດງໃນຮູບແບບຕາຕະລາງ ໂດຍຮຽງລຳດັບຈາກລາຍການໃໝ່ລົງໄປຫາລາຍການເກົ່າ [໑]. ພ້ອມທັງມີການໄຮໄລ້ແຍກສີແຖວຢ່າງສວຍງາມ (ເງິນເຂົ້າສີຂຽວ 🟢 / ເງິນອອກສີແດງ 🔴).

---

## 📂 ໂຄງສ້າງໂຟນເດີໂຄງການ (Monorepo Folder Structure)
ໂຄງການນີ້ຖືກຈັດລະບຽບແບບ Monorepo ເພື່ອແຍກ Frontend ແລະ Backend ອອກຈາກກັນຢ່າງຊັດເຈນ [໑]:
```text
D:\Wallet API\ (Root Folder)
 ├── wallet-backend/           <-- ☕ ຝັ່ງ Backend (Spring Boot Core API)
 │    ├── src/
 │    └── pom.xml
 ├── wallet-frontend/          <-- ⚛️ ຝັ່ງ Frontend (React SPA + Tailwind CSS)
 │    ├── src/
 │    └── package.json
 └── README.md                 <-- 📄 ໄຟລ໌ອະທິບາຍໂຄງການລວມ
```

---

## 🗄️ ໂຄງສ້າງຖານຂໍ້ມູນ (Database Schema)
ໂຄງສ້າງຕາຕະລາງໃນ PostgreSQL ທີ່ສ້າງ ແລະ ຄວບຄຸມອັດຕະໂນມັດຜ່ານ **Hibernate (JPA)**:
1. **ຕາຕະລາງ Wallets Table (`wallets`):** ເກັບຂໍ້ມູນ `wallet_id`, `user_id`, ຍອດເງິນຄົງເຫຼືອ (`balance` ປະເພດ `BigDecimal` ເພື່ອປ້ອງກັນທົດສະນິຍົມຄາດເຄື່ອນ) ແລະ ລະຫັດຜ່ານທີ່ຖືກ Hash ດ້ວຍ BCrypt (`password`).
2. **ຕາຕະລາງ Wallet Transactions Table (`wallet_transactions`):** ເກັບປະຫວັດລາຍການເງິນເຂົ້າ-ອອກທັງໝົດ ໂດຍບັນທຶກປະເພດທຸລະກຳ (`DEPOSIT`, `TRANSFER_IN`, `TRANSFER_OUT`), ຈຳນວນເງິນ, ລາຍລະອຽດ, ວັນທີ ແລະ ເວລາຈິງ (`created_at`).

---

## 🔌 ເສັ້ນທາງການເຊື່ອມຕໍ່ APIs (Endpoints)

ລະບົບ Backend ເປີດຮອງຮັບ **CORS (`@CrossOrigin`)** ເພື່ອໃຫ້ຝັ່ງ Frontend ເຂົ້າເຖິງຂໍ້ມູນໄດ້:
* 🔐 **POST:** `/api/wallet/login` — ສຳລັບກວດສອບການເຂົ້າສູ່ລະບົບ (ຮັບ JSON Body).
* 🔍 **GET:** `/api/wallet/{userId}` — ສຳລັບກວດສອບຍອດເງິນຫຼ້າສຸດ [໑].
* 💵 **POST:** `/api/wallet/deposit` — ສຳລັບຝາກເງິນ/ເຕີມເງິນ (ຮັບ Form Parameter).
* 💸 **POST:** `/api/wallet/transfer` — ສຳລັບໂອນເງິນລະຫວ່າງບັນຊີ (ຮັບ Form Parameter).
* 📊 **GET:** `/api/wallet/{userId}/transactions` — ສຳລັບດຶງປະຫວັດການເງິນທັງໝົດ [໑].

---

## 🔒 ລະບົບຄວາມປອດໄພຂອງ Config (Secrets Management)
* ໄຟລ໌ຕັ້ງຄ່າຫຼັກ ແລະ ໂຟນເດີຂີ້ເຫຍື້ອ ເຊັ່ນ `node_modules/`, `target/` ແລະ `.idea/` ຖືກໃສ່ໄວ້ໃນ `.gitignore` ຫຼັກຢ່າງຮຽບຮ້ອຍ ເພື່ອປ້ອງກັນບໍ່ໃຫ້ຂໍ້ມູນລະຫັດຜ່ານຖານຂໍ້ມູນ ແລະ ໄຟລ໌ລະບົບຮົ່ວໄຫຼຂຶ້ນ GitHub [໑].
* ລະຫັດຜ່ານຖານຂໍ້ມູນຖືກເອີ້ນໃຊ້ຜ່ານຕົວແປລະບົບ (Environment Variables) ເຮັດໃຫ້ໂຄ້ດປອດໄພ ແລະ ຍ້າຍ Server ໄດ້ງ່າຍ [໑].

---
🎯 ບາດກ້າວພັດທະນາຕໍ່ໄປ
* 1. ພັດທະນາ ລະບົບຄວາມປອດໄພ JWT Token + Spring Security ເພື່ອບລັອກບໍ່ໃຫ້ຄົນນອກຍິງ API ໄດ້? 
* 2. ເພີ່ມ Data Validation (@Min/@NotNull) ເພື່ອປ້ອງກັນການສົ່ງຄ່າເງິນຕິດລົບ ຫຼື ຄ່າວ່າງເປົ່າ?
* 3. ພັດທະນາຟີເຈີຫຼັກໃຫ້ຄົບກ່ອນ ເຊັ່ນ ຟີເຈີຖອນເງິນ (Withdraw)?
