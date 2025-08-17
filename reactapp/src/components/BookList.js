// components/BookList.js
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import * as api from "../utils/api";

const BookList = () => {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");

  const loadBooks = async () => {
    setLoading(true);
    try {
      const res = await api.fetchBooks();
      setBooks(res.data);
    } catch (err) {
      console.error("Failed to fetch books", err);
      setBooks([]);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadBooks();
  }, []);

  const handleDelete = async (id) => {
    try {
      await api.deleteBook(id);
      // update UI immediately
      setBooks((prev) => prev.filter((b) => b.id !== id));
    } catch (err) {
      console.error("Failed to delete book", err);
    }
  };

  const filtered = books.filter((b) =>
    b.title.toLowerCase().includes(search.toLowerCase())
  );

  if (loading) return <p>Loading books...</p>;
  if (!loading && books.length === 0) return <p>No books found</p>;

  return (
    <div>
      <div>
        <input
          placeholder="Search by title"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
        <Link to="/books/new">Add New Book</Link>
      </div>
      <ul>
        {filtered.map((book) => (
          <li key={book.id}>
            <span>{book.title}</span>{" "}
            <button onClick={() => handleDelete(book.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BookList;
