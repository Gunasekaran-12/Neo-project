import React, { useState } from 'react';
import * as api from '../utils/api';

const BookForm = () => {
  const [formData, setFormData] = useState({
    title: '',
    author: '',
    isbn: '',
    publicationYear: ''
  });
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  const validate = () => {
    const newErrors = {};
    if (!formData.title.trim()) newErrors.title = 'Title is required';
    if (!formData.author.trim()) newErrors.author = 'Author is required';
    if (!formData.isbn.trim()) {
      newErrors.isbn = 'ISBN is required';
    } else if (formData.isbn.length !== 13) {
      newErrors.isbn = 'ISBN must be exactly 13 characters';
    }
    if (!formData.publicationYear) {
      newErrors.publicationYear = 'Publication Year is required';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    setIsSubmitting(true);
    try {
      await api.addBook({
        ...formData,
        publicationYear: parseInt(formData.publicationYear)
      });
      setFormData({
        title: '',
        author: '',
        isbn: '',
        publicationYear: ''
      });
      setErrors({});
    } catch (error) {
      setErrors({ submit: error.message });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="book-form">
      <h2>Add New Book</h2>
      
      <div className="form-group">
        <label htmlFor="title-input">Title:</label>
        <input
          id="title-input"
          type="text"
          name="title"
          value={formData.title}
          onChange={handleChange}
          data-testid="title-input"
        />
        {errors.title && <div className="error-message" data-testid="title-error">{errors.title}</div>}
      </div>
      
      <div className="form-group">
        <label htmlFor="author-input">Author:</label>
        <input
          id="author-input"
          type="text"
          name="author"
          value={formData.author}
          onChange={handleChange}
          data-testid="author-input"
        />
        {errors.author && <div className="error-message" data-testid="author-error">{errors.author}</div>}
      </div>
      

      <div className="form-group">
        <label htmlFor="isbn-input">ISBN:</label>
        <input
          id="isbn-input"
          type="text"
          name="isbn"
          value={formData.isbn}
          onChange={handleChange}
          data-testid="isbn-input"
        />
        {errors.isbn && <div className="error-message" data-testid="isbn-error">{errors.isbn}</div>}
      </div>
      
      <div className="form-group">
        <label htmlFor="year-input">Publication Year:</label>
        <input
          id="year-input"
          type="number"
          name="publicationYear"
          value={formData.publicationYear}
          onChange={handleChange}
          data-testid="year-input"
        />
        {errors.publicationYear && (
          <div className="error-message" data-testid="year-error">{errors.publicationYear}</div>
        )}
      </div>
      
      <button 
        type="submit" 
        disabled={isSubmitting}
        data-testid="submit-button"
      >
        {isSubmitting ? 'Saving...' : 'Save'}
      </button>
    </form>
  );
};

export default BookForm;