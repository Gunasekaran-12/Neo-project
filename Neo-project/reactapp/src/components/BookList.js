import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import * as api from '../utils/api';

const BookList = () => {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const loadBooks = async () => {
      try {
        const response = await api.fetchBooks();
        setBooks(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    loadBooks();
  }, []);

  const handleDelete = async (id) => {
    try {
      await api.deleteBook(id);
      setBooks(books.filter(book => book.id !== id));
    } catch (err) {
      setError(err.message);
    }
  };

  const filteredBooks = books.filter(book => 
    book.title.toLowerCase().includes(searchTerm.toLowerCase()) || 
    book.author.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) return <div>Loading books...</div>;
  if (error) return <div>Error: [Error - You need to specify the message]</div>;

  return (
    <div className="book-list">
      <div className="controls">
        <input
          type="text"
          placeholder="Search by title or author"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <Link to="/books/new" className="btn btn-primary">
          Add New Book
        </Link>
      </div>

      {filteredBooks.length === 0 ? (
        <div className="empty-state">No books found</div>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Title</th>
              <th>Author</th>
              <th>ISBN</th>
              <th>Year</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filteredBooks.map(book => (
              <tr key={book.id}>
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.isbn}</td>
                <td>{book.publicationYear}</td>
                <td>{book.available ? 'Available' : 'Borrowed'}</td>
                <td>
                  <button onClick={() => handleDelete(book.id)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default BookList;