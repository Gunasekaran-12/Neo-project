import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { fetchBooks, deleteBook } from "../utils/api";

const BookList = () => {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");

  const load = async () => {
    setLoading(true);
    const res = await fetchBooks(search ? { title: search } : {});
    setBooks(res.data);
    setLoading(false);
  };

  useEffect(() => {
    load();
  }, [search]);

  const handleDelete = async (id) => {
    await deleteBook(id);
    load();
  };

  if (loading) return <p>Loading books...</p>;
  if (!books.length) return <p>No books found</p>;

  return (
    <div>
      <input
        placeholder="Search by title"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />
      <Link to="/books/add">Add New Book</Link>
      <table>
        <thead>
          <tr><th>Title</th><th>Author</th><th>Actions</th></tr>
        </thead>
        <tbody>
          {books.map((b) => (
            <tr key={b.id}>
              <td>{b.title}</td>
              <td>{b.author}</td>
              <td>
                <Link to={`/books/edit/${b.id}`}>Edit</Link>
                <button onClick={() => handleDelete(b.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
export default BookList;
