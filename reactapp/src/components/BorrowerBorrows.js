import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getActiveBorrowsByBorrower, returnBook } from "../utils/api";

const BorrowerBorrows = () => {
  const { id } = useParams();
  const [borrows, setBorrows] = useState([]);

  const load = async () => {
    const res = await getActiveBorrowsByBorrower(id);
    setBorrows(res.data);
  };

  useEffect(() => {
    load();
  }, [id]);

  const handleReturn = async (borrowId) => {
    await returnBook(borrowId);
    load();
  };

  if (!borrows.length) return <p>No active borrows</p>;

  return (
    <div>
      <h2>Active Borrows</h2>
      <ul>
        {borrows.map((br) => (
          <li key={br.id}>
            {br.book.title} (Borrowed: {br.borrowDate})
            <button onClick={() => handleReturn(br.id)}>Return</button>
          </li>
        ))}
      </ul>
    </div>
  );
};
export default BorrowerBorrows;
