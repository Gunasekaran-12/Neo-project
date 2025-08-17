import axios from "axios";

const API_URL = "http://localhost:8081/api";

// Books
export const fetchBooks = () => axios.get(`${API_URL}/books`);
export const getBook = (id) => axios.get(`${API_URL}/books/${id}`);
export const addBook = (book) => axios.post(`${API_URL}/books`, book);
export const updateBook = (id, book) => axios.put(`${API_URL}/books/${id}`, book);
export const deleteBook = (id) => axios.delete(`${API_URL}/books/${id}`);

// Borrowers
export const fetchBorrowers = () => axios.get(`${API_URL}/borrowers`);
export const getBorrower = (id) => axios.get(`${API_URL}/borrowers/${id}`);
export const addBorrower = (b) => axios.post(`${API_URL}/borrowers`, b);
export const updateBorrower = (id, b) => axios.put(`${API_URL}/borrowers/${id}`, b);

// Borrows
export const borrowBook = (data) => axios.post(`${API_URL}/borrows/borrow`, data);
export const returnBook = (borrowId) => axios.post(`${API_URL}/borrows/return/${borrowId}`);
export const getActiveBorrowsByBorrower = (id) =>
  axios.get(`${API_URL}/borrows/borrower/${id}`);
