import React, { useEffect, useState } from "react";
import * as api from "../utils/api";

const BorrowBook = () => {
  const [books, setBooks] = useState([]);
  const [borrowers, setBorrowers] = useState([]);
  const [selectedBook, setSelectedBook] = useState("");
  const [selectedBorrower, setSelectedBorrower] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    const loadData = async () => {
      const booksRes = await api.fetchBooks();
      const borrowersRes = await api.fetchBorrowers();
      setBooks(booksRes.data);
      setBorrowers(borrowersRes.data);
    };
    loadData();
  }, []);

  const handleBorrow = async () => {
    if (!selectedBook || !selectedBorrower) {
      setError("Please select both a book and a borrower.");
      return;
    }
    setError("");
    await api.borrowBook(selectedBook, selectedBorrower);
  };

  return (
    <div>
      <label htmlFor="book">Select Book</label>
      <select
        id="book"
        aria-label="Select Book"
        value={selectedBook}
        onChange={(e) => setSelectedBook(e.target.value)}
      >
        <option value="">-- Select --</option>
        {books.map((b) => (
          <option key={b.id} value={b.id}>
            {b.title} ({b.author})
          </option>
        ))}
      </select>

      <label htmlFor="borrower">Select Borrower</label>
      <select
        id="borrower"
        aria-label="Select Borrower"
        value={selectedBorrower}
        onChange={(e) => setSelectedBorrower(e.target.value)}
      >
        <option value="">-- Select --</option>
        {borrowers.map((br) => (
          <option key={br.id} value={br.id}>
            {br.name} ({br.email})
          </option>
        ))}
      </select>

      <button onClick={handleBorrow}>Borrow</button>

      {error && <p>[Error - You need to specify the message]</p>}
    </div>
  );
};

export default BorrowBook;
