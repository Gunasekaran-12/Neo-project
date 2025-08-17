import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import * as api from "../utils/api";

const BookList = () => {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");

  const loadBooks = async (query = "") => {
    setLoading(true);
    const res = await api.fetchBooks(query);
    setBooks(res.data);
    setLoading(false);
  };

  useEffect(() => {
    loadBooks(search);
  }, [search]);

  const handleDelete = async (id) => {
    await api.deleteBook(id);
    loadBooks(search);
  };

  if (loading) return <p>Loading books...</p>;

  return (
    <div>
      <input
        placeholder="Search by title"
        value={search}
        onChange={e => setSearch(e.target.value)}
      />
      <Link to="/books/new">Add New Book</Link>
      {books.length === 0 ? (
        <p>No books found</p>
      ) : (
        <ul>
          {books.map(b => (
            <li key={b.id}>
              {b.title} - {b.author} ({b.publicationYear})
              <button onClick={() => handleDelete(b.id)}>Delete</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default BookList;
