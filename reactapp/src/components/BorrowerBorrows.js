import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import * as api from "../utils/api";

const BorrowerBorrows = () => {
  const { id } = useParams();
  const [borrows, setBorrows] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadBorrows = async () => {
      try {
        const res = await api.getActiveBorrowsByBorrower(id);
        setBorrows(res.data);
      } finally {
        setLoading(false);
      }
    };
    loadBorrows();
  }, [id]);

  const handleReturn = async (borrowId) => {
    await api.returnBook(borrowId);
    setBorrows((prev) => prev.filter((b) => b.id !== borrowId));
  };

  if (loading) {
    return <p>Loading...</p>;
  }

  if (!borrows.length) {
    return <p>No active borrows</p>;
  }

  return (
    <div>
      <h2>Active Borrows</h2>
      <ul>
        {borrows.map((b) => (
          <li key={b.id}>
            {b.book.title} - {b.borrowDate}
            <button onClick={() => handleReturn(b.id)}>Return</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BorrowerBorrows;
