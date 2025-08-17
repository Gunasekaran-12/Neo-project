import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import * as api from "../utils/api";

const BookForm = () => {
  const [book, setBook] = useState({
    title: "",
    author: "",
    isbn: "",
    publicationYear: ""
  });
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      api.getBook(id).then(res => setBook(res.data));
    }
  }, [id]);

  const validate = () => {
    const errs = {};
    if (!book.title) errs.title = "Title is required";
    if (!book.author) errs.author = "Author is required";
    if (!book.isbn) errs.isbn = "ISBN is required";
    else if (book.isbn.length !== 13) errs.isbn = "ISBN must be exactly 13 characters";
    if (!book.publicationYear) errs.publicationYear = "Publication Year is required";
    return errs;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const errs = validate();
    if (Object.keys(errs).length) {
      setErrors(errs);
      return;
    }
    if (id) await api.updateBook(id, book);
    else await api.addBook(book);
    navigate("/books");
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="title">Title</label>
        <input id="title" value={book.title} onChange={e => setBook({ ...book, title: e.target.value })} />
        {errors.title && <p>{errors.title}</p>}
      </div>
      <div>
        <label htmlFor="author">Author</label>
        <input id="author" value={book.author} onChange={e => setBook({ ...book, author: e.target.value })} />
        {errors.author && <p>{errors.author}</p>}
      </div>
      <div>
        <label htmlFor="isbn">ISBN</label>
        <input id="isbn" value={book.isbn} onChange={e => setBook({ ...book, isbn: e.target.value })} />
        {errors.isbn && <p>{errors.isbn}</p>}
      </div>
      <div>
        <label htmlFor="year">Publication Year</label>
        <input id="year" value={book.publicationYear} onChange={e => setBook({ ...book, publicationYear: e.target.value })} />
        {errors.publicationYear && <p>{errors.publicationYear}</p>}
      </div>
      <button type="submit">Save</button>
    </form>
  );
};

export default BookForm;
