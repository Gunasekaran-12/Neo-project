import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import * as api from "../utils/api";

const BorrowerBorrows = () => {
  const { id } = useParams();
  const [borrows, setBorrows] = useState([]);

  useEffect(() => {
    api.getActiveBorrowsByBorrower(id).then(res => setBorrows(res.data));
  }, [id]);

  const handleReturn = async (borrowId) => {
    await api.returnBook(borrowId);
    setBorrows(borrows.filter(b => b.id !== borrowId));
  };

  if (borrows.length === 0) return <p>No active borrows</p>;

  return (
    <ul>
      {borrows.map(b => (
        <li key={b.id}>
          {b.book.title} (Borrowed on {b.borrowDate})
          <button onClick={() => handleReturn(b.id)}>Return</button>
        </li>
      ))}
    </ul>
  );
};

export default BorrowerBorrows;
