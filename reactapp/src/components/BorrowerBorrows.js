import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getActiveBorrowsByBorrower, returnBook } from "../utils/api";

const BorrowerBorrows = () => {
  const { id } = useParams();
  const [borrows, setBorrows] = useState([]);

  useEffect(() => {
    const load = async () => {
      const res = await getActiveBorrowsByBorrower(id);
      setBorrows(res.data || []);
    };
    load();
  }, [id]);

  const handleReturn = async (borrowId) => {
    await returnBook(borrowId);
    setBorrows((prev) => prev.filter((b) => b.id !== borrowId));
  };

  if (borrows.length === 0) {
    return <p>No active borrows</p>;
  }

  return (
    <div>
      <h2>Active Borrows</h2>
      <ul>
        {borrows.map((b) => (
          <li key={b.id}>
            {b.book?.title} ({b.borrowDate})
            <button onClick={() => handleReturn(b.id)}>Return</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BorrowerBorrows;
