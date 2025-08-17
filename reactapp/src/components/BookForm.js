import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { addBook, updateBook, getBook } from "../utils/api";

const BookForm = () => {
  const [form, setForm] = useState({ title: "", author: "", isbn: "", publicationYear: "" });
  const [errors, setErrors] = useState({});
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      getBook(id).then((res) => setForm(res.data));
    }
  }, [id]);

  const validate = () => {
    let errs = {};
    if (!form.title) errs.title = "Title is required";
    if (!form.author) errs.author = "Author is required";
    if (!form.isbn) errs.isbn = "ISBN is required";
    else if (form.isbn.length !== 13) errs.isbn = "ISBN must be exactly 13 characters";
    if (!form.publicationYear) errs.publicationYear = "Publication Year is required";
    return errs;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const errs = validate();
    if (Object.keys(errs).length) {
      setErrors(errs);
      return;
    }
    if (id) await updateBook(id, form);
    else await addBook(form);
    navigate("/books");
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Title</label>
        <input value={form.title} onChange={(e) => setForm({ ...form, title: e.target.value })} />
        {errors.title && <p>{errors.title}</p>}
      </div>
      <div>
        <label>Author</label>
        <input value={form.author} onChange={(e) => setForm({ ...form, author: e.target.value })} />
        {errors.author && <p>{errors.author}</p>}
      </div>
      <div>
        <label>ISBN</label>
        <input value={form.isbn} onChange={(e) => setForm({ ...form, isbn: e.target.value })} />
        {errors.isbn && <p>{errors.isbn}</p>}
      </div>
      <div>
        <label>Publication Year</label>
        <input
          value={form.publicationYear}
          onChange={(e) => setForm({ ...form, publicationYear: e.target.value })}
        />
        {errors.publicationYear && <p>{errors.publicationYear}</p>}
      </div>
      <button type="submit">Save</button>
    </form>
  );
};
export default BookForm;
