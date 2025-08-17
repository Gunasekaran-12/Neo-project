// components/BorrowBook.js
import React, { useEffect, useState } from "react";
import * as api from "../utils/api";

const BorrowBook = () => {
  const [books, setBooks] = useState([]);
  const [borrowers, setBorrowers] = useState([]);
  const [selectedBook, setSelectedBook] = useState("");
  const [selectedBorrower, setSelectedBorrower] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        const b = await api.fetchBooks();
        setBooks(b.data || []);
        const br = await api.fetchBorrowers();
        setBorrowers(br.data || []);
      } catch (err) {
        console.error("Error loading dropdowns", err);
      }
    };
    load();
  }, []);

  const handleBorrow = async () => {
    if (!selectedBook || !selectedBorrower) {
      setError("Please select both");
      return;
    }
    setError("");
    try {
      await api.borrowBook({
        bookId: parseInt(selectedBook),
        borrowerId: parseInt(selectedBorrower),
      });
    } catch (err) {
      console.error("Failed to borrow book", err);
    }
  };

  return (
    <div>
      <label htmlFor="bookSelect">Select Book</label>
      <select
        id="bookSelect"
        value={selectedBook}
        onChange={(e) => setSelectedBook(e.target.value)}
      >
        <option value="">-- Select --</option>
        {books.map((b) => (
          <option key={b.id} value={b.id}>
            {b.title}
          </option>
        ))}
      </select>

      <label htmlFor="borrowerSelect">Select Borrower</label>
      <select
        id="borrowerSelect"
        value={selectedBorrower}
        onChange={(e) => setSelectedBorrower(e.target.value)}
      >
        <option value="">-- Select --</option>
        {borrowers.map((br) => (
          <option key={br.id} value={br.id}>
            {br.name}
          </option>
        ))}
      </select>

      <button onClick={handleBorrow}>Borrow</button>

      {error && <p>[Error - You need to specify the message]</p>}
    </div>
  );
};

export default BorrowBook;
