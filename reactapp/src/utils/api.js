import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api';

// ================== Complaint APIs ==================
const ComplaintService = {
  getAllComplaints: () => axios.get(`${BASE_URL}/complaints`),
  getComplaintById: (id) => axios.get(`${BASE_URL}/complaints/${id}`),
  createComplaint: (complaint) => axios.post(`${BASE_URL}/complaints`, complaint),
  updateComplaint: (id, complaint) => axios.put(`${BASE_URL}/complaints/${id}`, complaint),
  deleteComplaint: (id) => axios.delete(`${BASE_URL}/complaints/${id}`),
  getComplaintsByStatus: (status) => axios.get(`${BASE_URL}/complaints/status/${encodeURIComponent(status)}`),
  getComplaintsByCategory: (category) => axios.get(`${BASE_URL}/complaints/category/${encodeURIComponent(category)}`)
};

// ================== Book APIs (for tests) ==================
const BookService = {
  fetchBooks: () => axios.get(`${BASE_URL}/books`),
  getBook: (id) => axios.get(`${BASE_URL}/books/${id}`),
  addBook: (book) => axios.post(`${BASE_URL}/books`, book),
  updateBook: (id, book) => axios.put(`${BASE_URL}/books/${id}`, book),
  deleteBook: (id) => axios.delete(`${BASE_URL}/books/${id}`)
};

// ================== Borrow APIs (for tests) ==================
const BorrowService = {
  fetchBorrowers: () => axios.get(`${BASE_URL}/borrowers`),
  borrowBook: (bookId, borrowerId) => axios.post(`${BASE_URL}/borrow`, { bookId, borrowerId }),
  getActiveBorrowsByBorrower: (borrowerId) => axios.get(`${BASE_URL}/borrowers/${borrowerId}/active-borrows`),
  returnBook: (borrowId) => axios.post(`${BASE_URL}/return/${borrowId}`)
};

// Merge all into one export (so `api.addBook` etc. exist)
const api = {
  ...ComplaintService,
  ...BookService,
  ...BorrowService
};

export default api;
