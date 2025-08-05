import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import BookForm from "../components/BookForm";
import { BrowserRouter } from "react-router-dom";
import * as api from "../utils/api";

jest.mock("../utils/api");

describe("BookForm", () => {
  beforeEach(() => {
    jest.clearAllMocks();
    api.addBook.mockResolvedValue({});
    api.updateBook.mockResolvedValue({});
    api.getBook.mockResolvedValue({ data: {
      title: "T", author: "A", isbn: "1234567890123", publicationYear: 2023 } });
  });

  it("shows validation errors on empty submit", async () => {
    render(<BrowserRouter><BookForm /></BrowserRouter>);
    const saveBtn = screen.getByText(/Save/i);
    fireEvent.click(saveBtn);
    await waitFor(() => {
      expect(screen.getByText(/Title is required/i)).toBeInTheDocument();
    });
    await waitFor(() => {
      expect(screen.getByText(/Author is required/i)).toBeInTheDocument();
    });
    await waitFor(() => {
      expect(screen.getByText(/ISBN is required/i)).toBeInTheDocument();
    });
    await waitFor(() => {
      expect(screen.getByText(/Publication Year is required/i)).toBeInTheDocument();
    });
  });

  it("shows error for invalid ISBN length", async () => {
    render(<BrowserRouter><BookForm /></BrowserRouter>);
    fireEvent.change(screen.getByLabelText(/Title/i), { target: { value: "TestBook" } });
    fireEvent.change(screen.getByLabelText(/Author/i), { target: { value: "Tester" } });
    fireEvent.change(screen.getByLabelText(/ISBN/i), { target: { value: "shortisbn" } });
    fireEvent.change(screen.getByLabelText(/Publication Year/i), { target: { value: "2023" } });
    const saveBtn = screen.getByText(/Save/i);
    fireEvent.click(saveBtn);
    expect(await screen.findByText(/ISBN must be exactly 13 characters/)).toBeInTheDocument();
  });

  it("submits valid form", async () => {
    render(<BrowserRouter><BookForm /></BrowserRouter>);
    fireEvent.change(screen.getByLabelText(/Title/i), { target: { value: "TKAM" } });
    fireEvent.change(screen.getByLabelText(/Author/i), { target: { value: "Lee" } });
    fireEvent.change(screen.getByLabelText(/ISBN/i), { target: { value: "1234567890999" } });
    fireEvent.change(screen.getByLabelText(/Publication Year/i), { target: { value: "2021" } });
    fireEvent.click(screen.getByText(/Save/i));
    await waitFor(() => expect(api.addBook).toHaveBeenCalled());
  });
});
