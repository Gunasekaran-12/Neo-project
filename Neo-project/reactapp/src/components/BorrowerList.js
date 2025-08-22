import React, { useState, useEffect } from 'react';
import { fetchBorrowers } from '../utils/api';
import BorrowerBorrows from './BorrowerBorrows';

const BorrowerList = () => {
  const [borrowers, setBorrowers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedBorrowerId, setSelectedBorrowerId] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const loadBorrowers = async () => {
      try {
        const data = await fetchBorrowers();
        setBorrowers(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    loadBorrowers();
  }, []);

  const filteredBorrowers = borrowers.filter(borrower => 
    borrower.name.toLowerCase().includes(searchTerm.toLowerCase()) || 
    borrower.email.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) return <div>Loading borrowers...</div>;
  if (error) return <div>Error: [Error - You need to specify the message]</div>;

  return (
    <div className="borrower-list">
      <div className="controls">
        <input
          type="text"
          placeholder="Search borrowers..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <div className="borrower-container">
        <div className="borrower-table">
          {filteredBorrowers.length === 0 ? (
            <div className="empty-state">No borrowers found</div>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredBorrowers.map(borrower => (
                  <tr 
                    key={borrower.id} 
                    className={selectedBorrowerId === borrower.id ? 'selected' : ''}
                    onClick={() => setSelectedBorrowerId(borrower.id)}
                  >
                    <td>{borrower.name}</td>
                    <td>{borrower.email}</td>
                    <td>{borrower.phone || '-'}</td>
                    <td>
                      <button onClick={() => setSelectedBorrowerId(borrower.id)}>
                        View Borrows
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>

        <div className="borrower-details">
          <BorrowerBorrows borrowerId={selectedBorrowerId} />
        </div>
      </div>
    </div>
  );
};

export default BorrowerList;