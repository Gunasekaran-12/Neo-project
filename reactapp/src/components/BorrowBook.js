import React, { useEffect, useState } from "react";
import * as api from "../utils/api";

const BorrowBook = () => {
  const [books, setBooks] = useState([]);
  const [borrowers, setBorrowers] = useState([]);
  const [selectedBook, setSelectedBook] = useState("");
  const [selectedBorrower, setSelectedBorrower] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    api.fetchBooks().then(res => setBooks(res.data));
    api.fetchBorrowers().then(res => setBorrowers(res.data));
  }, []);

  const handleBorrow = async () => {
    if (!selectedBook || !selectedBorrower) {
      setError("Please select both book and borrower");
      return;
    }
    await api.borrowBook(selectedBook, selectedBorrower);
    setError("");
  };

  return (
    <div>
      <label>Select Book</label>
      <select value={selectedBook} onChange={e => setSelectedBook(e.target.value)}>
        <option value="">--Choose--</option>
        {books.map(b => (
          <option key={b.id} value={b.id}>{b.title}</option>
        ))}
      </select>

      <label>Select Borrower</label>
      <select value={selectedBorrower} onChange={e => setSelectedBorrower(e.target.value)}>
        <option value="">--Choose--</option>
        {borrowers.map(br => (
          <option key={br.id} value={br.id}>{br.name}</option>
        ))}
      </select>

      <button onClick={handleBorrow}>Borrow</button>
      {error && <p>[Error - You need to specify the message]</p>}
    </div>
  );
};

export default BorrowBook;
