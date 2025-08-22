// src/components/SearchBooks.js
import React, { useState, useEffect } from 'react';
import { Form, Row, Col, Button } from 'react-bootstrap';
import { debounce } from 'lodash';

const initialFilters = {
  title: '',
  author: '',
  isbn: '',
  available: 'all',
};

const BookSearch = ({ onSearch }) => {
  const [filters, setFilters] = useState(initialFilters);

  // Optional: Debounced live search after 500ms of inactivity
  useEffect(() => {
    const delayedSearch = debounce(() => {
      onSearch(filters);
    }, 500);

    delayedSearch();

    return delayedSearch.cancel;
  }, [filters, onSearch]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFilters((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSearch(filters);
  };

  const handleReset = () => {
    const isAlreadyReset = Object.entries(filters).every(
      ([key, val]) => val === initialFilters[key]
    );
    if (!isAlreadyReset) {
      setFilters(initialFilters);
      onSearch(initialFilters);
    }
  };

  return (
    <Form
      onSubmit={handleSubmit}
      className="mb-4 p-3 bg-white shadow-sm border rounded"
    >
      <Row className="g-3">
        <Col md={3}>
          <Form.Group controlId="filterTitle">
            <Form.Label>📚 Title</Form.Label>
            <Form.Control
              type="text"
              name="title"
              value={filters.title}
              onChange={handleChange}
              placeholder="e.g. The Alchemist"
            />
          </Form.Group>
        </Col>

        <Col md={3}>
          <Form.Group controlId="filterAuthor">
            <Form.Label>✍️ Author</Form.Label>
            <Form.Control
              type="text"
              name="author"
              value={filters.author}
              onChange={handleChange}
              placeholder="e.g. Paulo Coelho"
            />
          </Form.Group>
        </Col>

        <Col md={2}>
          <Form.Group controlId="filterISBN">
            <Form.Label>🔢 ISBN</Form.Label>
            <Form.Control
              type="text"
              name="isbn"
              value={filters.isbn}
              onChange={handleChange}
              placeholder="e.g. 9781234567897"
            />
          </Form.Group>
        </Col>
        <Col md={2}>
          <Form.Group controlId="filterAvailable">
            <Form.Label>📦 Availability</Form.Label>
            <Form.Select
              name="available"
              value={filters.available}
              onChange={handleChange}
            >
              <option value="all">All</option>
              <option value="available">Available</option>
              <option value="borrowed">Borrowed</option>
            </Form.Select>
          </Form.Group>
        </Col>

        <Col md={2} className="d-flex align-items-end justify-content-end">
          <div>
            <Button type="submit" variant="primary" className="me-2">
              🔍 Search
            </Button>
            <Button variant="outline-secondary" onClick={handleReset}>
              🔄 Reset
            </Button>
          </div>
        </Col>
      </Row>
    </Form>
  );
};

export default BookSearch;
