import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import BorrowerBorrows from "../components/BorrowerBorrows";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import * as api from "../utils/api";
jest.mock("../utils/api");

describe("BorrowerBorrows", () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it("loads active borrows and can return", async () => {
    api.getActiveBorrowsByBorrower.mockResolvedValueOnce({
      data: [
        { id: 77, book: { title: "Kafka" }, borrowDate: "2022-10-10" },
        { id: 78, book: { title: "Brothers K" }, borrowDate: "2021-01-01" }
      ]
    });
    api.returnBook.mockResolvedValue({});
    render(
      <MemoryRouter initialEntries={["/borrowers/31/borrows"]}>
        <Routes>
          <Route path="/borrowers/:id/borrows" element={<BorrowerBorrows />} />
        </Routes>
      </MemoryRouter>
    );
    await screen.findByText("Kafka");
    fireEvent.click(screen.getAllByText(/Return/i)[0]);
    await waitFor(() => expect(api.returnBook).toHaveBeenCalledWith(77));
  });

  it("handles empty state", async () => {
    api.getActiveBorrowsByBorrower.mockResolvedValueOnce({ data: [] });
    render(
      <MemoryRouter initialEntries={["/borrowers/42/borrows"]}>
        <Routes>
          <Route path="/borrowers/:id/borrows" element={<BorrowerBorrows />} />
        </Routes>
      </MemoryRouter>
    );
    await screen.findByText(/No active borrows/);
  });
});
