import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import * as api from '../utils/api';

const BorrowerBorrows = () => {
  const { id } = useParams();
  const [borrows, setBorrows] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadBorrows = async () => {
      try {
        const response = await api.getActiveBorrowsByBorrower(id);
        setBorrows(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    loadBorrows();
  }, [id]);

  const handleReturn = async (borrowId) => {
    try {
      await api.returnBook(borrowId);
      setBorrows(borrows.filter(borrow => borrow.id !== borrowId));
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <div>Loading borrows...</div>;
  if (error) return <div>Error: [Error - You need to specify the message]</div>;

  return (
    <div className="borrower-borrows">
      {borrows.length === 0 ? (
        <div className="empty-state">No active borrows</div>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Book Title</th>
              <th>Borrow Date</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {borrows.map(borrow => (
              <tr key={borrow.id}>
                <td>{borrow.book.title}</td>
                <td>{borrow.borrowDate}</td>
                <td>
                  <button onClick={() => handleReturn(borrow.id)}>
                    Return
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default BorrowerBorrows;