import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as api from "../utils/api"; // ✅ matches your test mock path

function BookForm() {
  const [form, setForm] = useState({
    title: "",
    author: "",
    isbn: "",
    publicationYear: "",
  });

  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  // ✅ Validation logic to satisfy tests
  const validate = () => {
    const newErrors = {};

    if (!form.title) newErrors.title = "Title is required";
    if (!form.author) newErrors.author = "Author is required";

    if (!form.isbn) {
      newErrors.isbn = "ISBN is required";
    } else if (form.isbn.length !== 13) {
      newErrors.isbn = "ISBN must be exactly 13 characters";
    }

    if (!form.publicationYear) {
      newErrors.publicationYear = "Publication Year is required";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (validate()) {
      try {
        await api.addBook(form); // ✅ matches test expectation
        navigate("/"); // redirect after saving
      } catch (err) {
        console.error("Error adding book:", err);
      }
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="title">Title</label>
        <input
          id="title"
          name="title"
          value={form.title}
          onChange={handleChange}
        />
        {errors.title && <p>{errors.title}</p>}
      </div>

      <div>
        <label htmlFor="author">Author</label>
        <input
          id="author"
          name="author"
          value={form.author}
          onChange={handleChange}
        />
        {errors.author && <p>{errors.author}</p>}
      </div>

      <div>
        <label htmlFor="isbn">ISBN</label>
        <input
          id="isbn"
          name="isbn"
          value={form.isbn}
          onChange={handleChange}
        />
        {errors.isbn && <p>{errors.isbn}</p>}
      </div>

 
<div>
<label htmlFor="publicationYear">Publication Year</label>
<input
id="publicationYear"
name="publicationYear"
value={form.publicationYear}
onChange={handleChange}
/>
{errors.publicationYear && <p>{errors.publicationYear}</p>}
</div>

<button type="submit">Save</button>
</form>
);
}

export default BookForm;