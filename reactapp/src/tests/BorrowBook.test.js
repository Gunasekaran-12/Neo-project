import React from "react";
import { render, screen, waitFor, fireEvent } from "@testing-library/react";
import BorrowBook from "../components/BorrowBook";
import { BrowserRouter } from "react-router-dom";
import * as api from "../utils/api";

jest.mock("../utils/api");

describe("BorrowBook", () => {
  beforeEach(() => {
    jest.clearAllMocks();
    api.fetchBooks.mockResolvedValue({
      data: [
        { id: 1, title: "B1", author: "Auth1" },
        { id: 2, title: "B2", author: "Auth2" }
      ]
    });
    api.fetchBorrowers.mockResolvedValue({
      data: [
        { id: 11, name: "Jane", email: "jane@example.com" },
        { id: 12, name: "Tom", email: "tom@example.com" }
      ]
    });
    api.borrowBook.mockResolvedValue({});
  });

  it("loads dropdowns and allows borrow", async () => {
    render(
      <BrowserRouter>
        <BorrowBook />
      </BrowserRouter>
    );
    await waitFor(() => {
      expect(screen.getByLabelText(/Select Book/i)).toBeInTheDocument();
    });
    await waitFor(() => {
      expect(screen.getByLabelText(/Select Borrower/i)).toBeInTheDocument();
    });
    await waitFor(() => {
      expect(screen.getByText("Borrow")).toBeInTheDocument();
    });
    fireEvent.change(screen.getByLabelText(/Select Book/i), { target: { value: "1" } });
    fireEvent.change(screen.getByLabelText(/Select Borrower/i), { target: { value: "12" } });
    fireEvent.click(screen.getByText("Borrow"));
    await waitFor(() => expect(api.borrowBook).toHaveBeenCalled());
  });

  it("shows error when nothing selected", async () => {
    render(
      <BrowserRouter>
        <BorrowBook />
      </BrowserRouter>
    );
    await screen.findByLabelText(/Select Book/i);
    fireEvent.click(screen.getByText("Borrow"));
    expect(screen.getByText(/Please select both/)).toBeInTheDocument();
  });
});
