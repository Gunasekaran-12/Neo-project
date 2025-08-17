import React, { useEffect, useState } from "react";
import { fetchBooks, fetchBorrowers, borrowBook } from "../utils/api";

const BorrowBook = () => {
  const [books, setBooks] = useState([]);
  const [borrowers, setBorrowers] = useState([]);
  const [selectedBook, setSelectedBook] = useState("");
  const [selectedBorrower, setSelectedBorrower] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    const loadData = async () => {
      const booksRes = await fetchBooks();
      setBooks(booksRes.data);
      const borrowersRes = await fetchBorrowers();
      setBorrowers(borrowersRes.data);
    };
    loadData();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!selectedBook || !selectedBorrower) {
      setError("Please select both");
      return;
    }
    setError("");
    await borrowBook(selectedBook, selectedBorrower);
  };

  return (
    <form onSubmit={handleSubmit}>
      <label htmlFor="book">Select Book</label>
      <select
        id="book"
        value={selectedBook}
        onChange={(e) => setSelectedBook(e.target.value)}
      >
        <option value="">-- Select a book --</option>
        {books.map((book) => (
          <option key={book.id} value={book.id}>
            {book.title}
          </option>
        ))}
      </select>

      <label htmlFor="borrower">Select Borrower</label>
      <select
        id="borrower"
        value={selectedBorrower}
        onChange={(e) => setSelectedBorrower(e.target.value)}
      >
        <option value="">-- Select a borrower --</option>
        {borrowers.map((b) => (
          <option key={b.id} value={b.id}>
            {b.name}
          </option>
        ))}
      </select>
//hlo
      <button type="submit">Borrow</button>
      {error && <p>[Error - You need to specify the message]</p>}
    </form>
  );
};

export default BorrowBook;
