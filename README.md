# 🚀 Spring Boot Core Wallet API v1.0

ລະບົບຈຳລອງກະເປົາເງິນອິເລັກທຣອນິກ (Core Wallet System) ພັດທະນາດ້ວຍ **Spring Boot (v4.2.0-M2)** ແລະ ເຊື່ອມຕໍ່ຖານຂໍ້ມູນ **PostgreSQL** ແບບມີສະຖຽນລະພາບ ແລະ ຄວາມປອດໄພສູງ ຕາມຫຼັກການ **RESTful APIs** [໑].

---

## 🛠️ ຟີເຈີຫຼັກຂອງລະບົບ (Core Features)

* 🔍 **Check Balance (`GET`)**: ກວດສອບຍອດເງິນຫຼ້າສຸດຂອງຜູ້ໃຊ້ ດຶງຂໍ້ມູນຈິງຈາກ PostgreSQL [໑].
* 💵 **Deposit (`POST`)**: ລະບົບຝາກເງິນ/ເຕີມເງິນເຂົ້າກະເປົາ ພ້ອມບັນທຶກປະຫວັດທຸລະກຳ.
* 💸 **Secure Transfer (`POST`)**: ລະບົບໂອນເງິນລະຫວ່າງບຸກຄົນ ພ້ອມກົນໄກຄວາມປອດໄພ `@Transactional` (ຕັດເງິນຜູ້ໂອນ ແລະ ເພີ່ມເງິນຜູ້ຮັບ ພ້ອມກັນ ຫາກມີບາງຢ່າງຜິດພາດຈະດຶງເງິນຄືນ/Rollback ທັນທີ).
* 📊 **Transaction History (`GET`)**: ດຶງປະຫວັດການເຮັດທຸລະກຳການເງິນ (Ledger History) ທັງໝົດ ໂດຍລຽງຈາກໃໝ່ຫາເກົ່າ [໑].

---

## 🗄️ ໂຄງສ້າງຖານຂໍ້ມູນ (Database Schema)

ໂຄງສ້າງຕາຕະລາງໃນ **PostgreSQL (`wallat_db_java`)** ທີ່ຖືກສ້າງຂຶ້ນອັດຕະໂນມັດຜ່ານ Hibernate ORM:

### 1. ຕາຕະລາງ `wallets`
* `wallet_id` (PK, Serial): ໄອດີກະເປົາເງິນ
* `user_id` (VARCHAR, Unique): ໄອດີຜູ້ໃຊ້ (ເຊື່ອມໂຍງກັບຕາຕະລາງ users)
* `balance` (NUMERIC): ຍອດເງິນຄົງເຫຼືອ (ໃຊ້ NUMERIC ເພື່ອປ້ອງກັນທົດສະນິຍົມຄາດເຄື່ອນໃນລະບົບການເງິນ)
* `currency` (VARCHAR): ສະກຸນເງິນ (Default: 'LAK')

### 2. ຕາຕະລາງ `wallet_transactions` (Ledger)
* `transaction_id` (PK, Serial): ໄອດີປະຫວັດ
* `wallet_id` (FK): ຜູກກັບກະເປົາເງິນ
* `transaction_type` (VARCHAR): ປະເພດທຸລະກຳ (`DEPOSIT`, `TRANSFER_IN`, `TRANSFER_OUT`)
* `amount` (NUMERIC): ຈຳນວນເງິນ
* `description` (TEXT): ລາຍລະອຽດການໂອນ
* `created_at` (TIMESTAMP): ວັນທີ ແລະ ເວລາຈິງທີ່ເກີດທຸລະກຳ

---

## 🔌 ລາຍລະອຽດ API Endpoints (RESTful Documentation)

### 1. ກວດສອບຍອດເງິນ (Check Balance)
* **URL:** `/api/wallet/{userId}`
* **Method:** `GET`
* **Response 200 OK:**
```json
{
    "userId": "userA",
    "balance": 500.0,
    "currency": "LAK",
    "walletId": 5
}
```

### 2. ຝາກເງິນ/ເຕີມເງິນ (Deposit)
* **URL:** `/api/wallet/deposit`
* **Method:** `POST`
* **Headers:** `Content-Type: application/json`
* **Request Body (JSON):**
```json
{
    "userId": "userA",
    "amount": 1000.0
}
```

### 3. ໂອນເງິນແບບປອດໄພ (Secure Transfer)
* **URL:** `/api/wallet/transfer`
* **Method:** `POST`
* **Headers:** `Content-Type: application/json`
* **Request Body (JSON):**
```json
{
    "fromUserId": "userA",
    "toUserId": "userB",
    "amount": 200.0
}
```

### 4. ດຶງປະຫວັດການເງິນ (Transaction History)
* **URL:** `/api/wallet/{userId}/transactions`
* **Method:** `GET`
* **Response 200 OK:**
```json
[
    {
        "walletId": 5,
        "transactionType": "TRANSFER_OUT",
        "amount": 200.00,
        "description": "ໂອນໄປຫາ userB",
        "createdAt": "2026-09-26T17:05:42.240822",
        "transactionId": 2
    }
]
```

---

## 🔒 ລະບົບຄວາມປອດໄພຂອງ Config (Secrets Management)

ໂຄງການນີ້ມີການນຳໃຊ້ **Environment Variables** ພາຍໃຕ້ໄຟລ໌ `.gitignore` ເພື່ອປົກປ້ອງຄວາມລັບຂອງລະບົບ:
* ໄຟລ໌ `application.properties` ຖືກໃສ່ໄວ້ໃນ `.gitignore` ເພື່ອ **ປ້ອງກັນບໍ່ໃຫ້ລະຫັດຜ່ານຖານຂໍ້ມູນຮົ່ວໄຫຼຂຶ້ນ GitHub** [໑].
* ລະຫັດຜ່ານຖານຂໍ້ມູນຖືກເອີ້ນໃຊ້ຜ່ານຕົວແປ `${DB_PASSWORD}` [໑].

---

## 🚀 ວິທີການ Run ໂຄງການ (How to Run)

1. ສ້າງ Database ຫວ່າງເປົ່າໃນ PostgreSQL ຊື່ວ່າ `wallat_db_java`.
2. ຕັ້ງຄ່າ Environment Variable ຢູ່ໃນ IDE ຂອງເຈົ້າ: `DB_PASSWORD = <ລຫັດຜ່ານ_database_ຂອງເຈົ້າ>`.
3. ກົດ Run ໄຟລ໌ `DemoApplication.java` ຜ່ານ IntelliJ IDEA.
4. ຕົວໂຄງການຈະເປີດໃຊ້ງານຢູ່ທີ່ `http://localhost:8080`. ເປີດ **Postman** ເພື່ອຍິງທົດສອບໄດ້ທັນທີ!
