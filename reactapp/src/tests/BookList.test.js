import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import BookList from "../components/BookList";
import { BrowserRouter } from "react-router-dom";
import * as api from "../utils/api";

jest.mock("../utils/api");

const booksMock = [
  {
    id: 1,
    title: "Book One",
    author: "John Steinbeck",
    isbn: "1234567890123",
    publicationYear: 2001,
    available: true
  },
  {
    id: 2,
    title: "Book Two",
    author: "Emily Bronte",
    isbn: "9876543210123",
    publicationYear: 1985,
    available: false
  }
];

describe("BookList", () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it("renders books and search/filter UI", async () => {
    api.fetchBooks.mockResolvedValueOnce({ data: booksMock });

    render(
      <BrowserRouter>
        <BookList />
      </BrowserRouter>
    );

    expect(screen.getByText(/Loading books/)).toBeInTheDocument();
    await waitFor(() => {
      expect(screen.getByText("Book One")).toBeInTheDocument();
    });
    await waitFor(() => {
      expect(screen.getByText("Book Two")).toBeInTheDocument();
    });
    await waitFor(() => {
      expect(screen.getByPlaceholderText(/Search by title/)).toBeInTheDocument();
    });
    await waitFor(() => {
      expect(screen.getByText("Add New Book")).toBeInTheDocument();
    });
  });

  it("handles search input", async () => {
    api.fetchBooks.mockResolvedValueOnce({ data: booksMock });
    render(
      <BrowserRouter>
        <BookList />
      </BrowserRouter>
    );
    await screen.findByText("Book One");
    api.fetchBooks.mockResolvedValueOnce({ data: [booksMock[1]] });
    fireEvent.change(screen.getByPlaceholderText(/Search by title/), { target: { value: "Two" } });
    await screen.findByText("Book Two");
  });

  it("handles empty state", async () => {
    api.fetchBooks.mockResolvedValueOnce({ data: [] });
    render(
      <BrowserRouter>
        <BookList />
      </BrowserRouter>
    );
    await screen.findByText(/No books found/i);
  });

  it("calls deleteBook and updates UI", async () => {
    api.fetchBooks.mockResolvedValueOnce({ data: booksMock });
    api.deleteBook.mockResolvedValueOnce({});
    render(
      <BrowserRouter>
        <BookList />
      </BrowserRouter>
    );
    await screen.findByText("Book One");
    const deleteBtns = screen.getAllByText("Delete");
    fireEvent.click(deleteBtns[0]);
    await waitFor(() => expect(api.deleteBook).toHaveBeenCalled());
  });
});
