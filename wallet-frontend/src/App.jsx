import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  // ລະບົບ Login State
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [usernameInput, setUsernameInput] = useState('');
  const [activeUser, setActiveUser] = useState('');

  // ລະບົບ Wallet State
  const [balance, setBalance] = useState(0);
  const [transactions, setTransactions] = useState([]);
  const [depositAmount, setDepositAmount] = useState('');
  const [toUserId, setToUserId] = useState('');
  const [transferAmount, setTransferAmount] = useState('');

  // ລະບົບແຈ້ງເຕືອນ Status
  const [message, setMessage] = useState('');
  const [isError, setIsError] = useState(false);

  // 1. ຟັງຊັນດຶງຂໍ້ມູນຈາກ Backend Spring Boot
  const fetchWalletData = async (userId) => {
    try {
      const balanceRes = await axios.get(`http://localhost:8080/api/wallet/${userId}`);
      setBalance(balanceRes.data.balance);

      const txRes = await axios.get(`http://localhost:8080/api/wallet/${userId}/transactions`);
      setTransactions(txRes.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    if (isLoggedIn && activeUser) {
      fetchWalletData(activeUser);
    }
  }, [isLoggedIn, activeUser]);

  // 2. ຟັງຊັນ Handle Login (Mock)
  const handleLogin = (e) => {
    e.preventDefault();
    if (usernameInput === 'userA' || usernameInput === 'userB') {
      setActiveUser(usernameInput);
      setIsLoggedIn(true);
      setMessage('');
    } else {
      alert("ບໍ່ພົບຜູ້ໃຊ້ນີ້! ກະລຸນາປ້ອນ: userA ຫຼື userB");
    }
  };

  // 3. ຟັງຊັນຝາກ微ນ (Deposit)
  const handleDeposit = async (e) => {
    e.preventDefault();
    try {
      const params = new URLSearchParams();
      params.append('userId', activeUser);
      params.append('amount', depositAmount);

      const response = await axios.post('http://localhost:8080/api/wallet/deposit', params);
      setIsError(response.data.includes("ຜິດພາດ"));
      setMessage(response.data);
      setDepositAmount('');
      fetchWalletData(activeUser);
    } catch (error) {
      setIsError(true);
      setMessage("ຜິດພາດ: ບໍ່ສາມາດເຊື່ອມຕໍ່ Backend ໄດ້");
    }
  };

  // 4. ຟັງຊັນໂອນເງິນ (Transfer)
  const handleTransfer = async (e) => {
    e.preventDefault();
    try {
      const params = new URLSearchParams();
      params.append('fromUserId', activeUser);
      params.append('toUserId', toUserId);
      params.append('amount', transferAmount);

      const response = await axios.post('http://localhost:8080/api/wallet/transfer', params);
      setIsError(response.data.includes("ຜິດພາດ"));
      setMessage(response.data);
      setToUserId('');
      setTransferAmount('');
      fetchWalletData(activeUser);
    } catch (error) {
      setIsError(true);
      setMessage("ຜິດພາດ: ບໍ່ສາມາດໂອນເງິນໄດ້");
    }
  };

  // 🚪 ຟັງຊັນ Log Out
  const handleLogout = () => {
    setIsLoggedIn(false);
    setActiveUser('');
    setUsernameInput('');
    setBalance(0);
    setTransactions([]);
    setMessage('');
  };
  // ----------------------------------------------------
  // A. ໜ້າຕາເວັບຕອນຍັງບໍ່ທັນ Login (Sign In Page)
  // ----------------------------------------------------
  if (!isLoggedIn) {
    return (
        <div className="min-h-screen bg-slate-100 flex items-center justify-center p-6">
          <div className="bg-white p-8 rounded-2xl shadow-xl w-full max-w-md border border-slate-200">
            <div className="text-center mb-8">
              <h1 className="text-4xl font-extrabold text-indigo-600 mb-2">💸 Lao E-Wallet</h1>
              <p className="text-slate-500 font-medium">ກະລຸນາເຂົ້າສູ່ລະບົບເພື່ອຈັດການກະເປົາເງິນຂອງທ່ານ</p>
            </div>
            <form onSubmit={handleLogin} className="space-y-5">
              <div>
                <label className="block text-sm font-bold text-slate-700 mb-2">User ID (ບັນຊີຜູ້ໃຊ້)</label>
                <input
                    type="text"
                    value={usernameInput}
                    onChange={(e) => setUsernameInput(e.target.value)}
                    placeholder="ป້ອນ: userA ຫຼື userB"
                    className="w-full px-4 py-3 rounded-xl border border-slate-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 font-semibold"
                    required
                />
              </div>
              <button type="submit" className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-3 rounded-xl transition duration-200 shadow-md">
                ເຂົ້າສູ່ລະບົບ 🚀
              </button>
            </form>
          </div>
        </div>
    );
  }

  // ----------------------------------------------------
  // B. ໜ້າຕາເວັບຫຼັງຈາກ Login ແລ້ວ (Dashboard UI Tailwind)
  // ----------------------------------------------------
  return (
      <div className="min-h-screen bg-slate-50 font-sans">
        {/* Navbar ສ່ວນຫົວ */}
        <nav className="bg-white border-b border-slate-200 sticky top-0 z-50">
          <div className="max-w-6xl mx-auto px-4 py-4 flex justify-between items-center">
            <h2 className="text-2xl font-black text-indigo-600 flex items-center gap-2">💰 Core Wallet Dashboard</h2>
            <div className="flex items-center gap-4">
            <span className="bg-indigo-50 text-indigo-700 font-bold px-4 py-2 rounded-xl text-sm border border-indigo-100">
              👤 ຜູ້ໃຊ້: <span className="underline">{activeUser === 'userA' ? 'ຄຳ (User A)' : 'ສອນ (User B)'}</span>
            </span>
              <button onClick={handleLogout} className="bg-rose-50 hover:bg-rose-100 text-rose-600 font-bold px-4 py-2 rounded-xl text-sm border border-rose-200 transition duration-200">
                🚪 ອອກຈາກລະບົບ
              </button>
            </div>
          </div>
        </nav>

        <div className="max-w-6xl mx-auto p-4 md:p-6 grid grid-cols-1 lg:grid-cols-12 gap-6">

          {/* ຝັ່ງຊ້າຍ: ຍອດເງິນ ແລະ ຟອມເຮັດທຸລະກຳ */}
          <div className="lg:col-span-5 space-y-6">

            {/* Card ສະແດງຍອດເງິນ */}
            <div className="bg-gradient-to-br from-emerald-500 to-teal-600 text-white p-6 rounded-2xl shadow-lg relative overflow-hidden">
              <div className="absolute -right-6 -bottom-6 text-white opacity-10 text-9xl font-bold">₭</div>
              <p className="text-emerald-100 text-sm font-semibold uppercase tracking-wider">ຍອດເງິນຄົງເຫຼືອທັງໝົດ</p>
              <h1 className="text-4xl font-black mt-2 tracking-tight">
                {balance.toLocaleString()} <span className="text-xl font-medium">LAK</span>
              </h1>
            </div>

            {/* ກ່ອງສະແດງຜົນການ Alert */}
            {message && (
                <div className={`p-4 rounded-xl font-bold text-center border shadow-sm transition ${isError ? 'bg-rose-50 border-rose-200 text-rose-700' : 'bg-emerald-50 border-emerald-200 text-emerald-700'}`}>
                  {message}
                </div>
            )}

            {/* ຟອມຝາກເງິນ */}
            <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
              <h4 className="text-lg font-bold text-slate-800 mb-4 flex items-center gap-2 text-emerald-600">💵 ຝາກເງິນເຂົ້າບັນຊີ</h4>
              <form onSubmit={handleDeposit} className="flex gap-3">
                <input
                    type="number"
                    value={depositAmount}
                    onChange={(e) => setDepositAmount(e.target.value)}
                    placeholder="ຈຳນວນເງິນ (ກີບ)"
                    required
                    className="flex-1 px-4 py-2 rounded-xl border border-slate-300 focus:outline-none focus:ring-2 focus:ring-emerald-500 font-semibold"
                />
                <button type="submit" className="bg-emerald-600 hover:bg-emerald-700 text-white font-bold px-6 py-2 rounded-xl transition shadow-sm">
                  ຝາກເງິນ
                </button>
              </form>
            </div>

            {/* ຟອມໂອນເງິນ */}
            <div className="bg-white p-5 rounded-2xl border border-slate-200 shadow-sm">
              <h4 className="text-lg font-bold text-slate-800 mb-4 flex items-center gap-2 text-indigo-600">💸 ໂອນເງິນແບບປອດໄພ</h4>
              <form onSubmit={handleTransfer} className="space-y-3">
                <input
                    type="text"
                    value={toUserId}
                    onChange={(e) => setToUserId(e.target.value)}
                    placeholder="ໂອນໄປຫາ User ID ຜູ້ຮັບ (ເຊັ່ນ: userB)"
                    required
                    className="w-full px-4 py-2 rounded-xl border border-slate-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 font-semibold"
                />
                <div className="flex gap-3">
                  <input
                      type="number"
                      value={transferAmount}
                      onChange={(e) => setTransferAmount(e.target.value)}
                      placeholder="ຈຳນວນເງິນ (ກີບ)"
                      required
                      className="flex-1 px-4 py-2 rounded-xl border border-slate-300 focus:outline-none focus:ring-2 focus:ring-indigo-500 font-semibold"
                  />
                  <button type="submit" className="bg-indigo-600 hover:bg-indigo-700 text-white font-bold px-6 py-2 rounded-xl transition shadow-sm">
                    ໂອນເງິນ
                  </button>
                </div>
              </form>
            </div>
          </div>

          {/* ຝັ່ງຂວາ: ຕາຕະລາງປະຫວັດທຸລະກຳ */}
          <div className="lg:col-span-7">
            <div className="bg-white rounded-2xl border border-slate-200 shadow-sm overflow-hidden">
              <div className="bg-slate-800 text-white p-4 font-bold text-lg flex items-center gap-2">📜 ປະຫວັດການເຮັດທຸລະກຳ (Ledger)</div>
              <div className="p-2 overflow-x-auto">
                <table className="w-full text-left border-collapse">
                  <thead>
                  <tr className="border-b border-slate-200 text-slate-400 text-sm">
                    <th className="p-3">ປະເພດ</th>
                    <th className="p-3">ຈຳນວນເງິນ</th>
                    <th className="p-3">ລາຍລະອຽດ</th>
                  </tr>
                  </thead>
                  <tbody className="divide-y divide-slate-100">
                  {transactions.map((tx, index) => {
                    const isIncome = tx.transactionType === 'DEPOSIT' || tx.transactionType === 'TRANSFER_IN';
                    return (
                        <tr key={index} className={`transition ${isIncome ? 'bg-emerald-50/60 hover:bg-emerald-100/70' : 'bg-rose-50/60 hover:bg-rose-100/70'}`}>
                          <td className="p-3">
                          <span className={`inline-block px-2.5 py-1 rounded-lg text-xs font-black text-white ${isIncome ? 'bg-emerald-600' : 'bg-rose-600'}`}>
                            {tx.transactionType}
                          </span>
                          </td>
                          <td className={`p-3 font-extrabold text-sm ${isIncome ? 'text-emerald-700' : 'text-rose-700'}`}>
                            {isIncome ? '+' : '-'}{tx.amount.toLocaleString()} ₭
                          </td>
                          <td className="p-3 text-slate-600 font-medium text-sm">{tx.description}</td>
                        </tr>
                    );
                  })}
                  {transactions.length === 0 && (
                      <tr>
                        <td colSpan="3" className="p-8 text-center text-slate-400 font-medium italic">ຍັງບໍ່ມີປະຫວັດທຸລະກຳໃນລະບົບ</td>
                      </tr>
                  )}
                  </tbody>
                </table>
              </div>
            </div>
          </div>

        </div>
      </div>
  );
}

export default App;
