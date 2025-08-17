import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import * as api from "../utils/api";

const BorrowerList = () => {
  const [borrowers, setBorrowers] = useState([]);

  useEffect(() => {
    api.fetchBorrowers().then(res => setBorrowers(res.data));
  }, []);

  return (
    <div>
      <Link to="/borrowers/new">Add Borrower</Link>
      {borrowers.length === 0 ? (
        <p>No borrowers found</p>
      ) : (
        <ul>
          {borrowers.map(b => (
            <li key={b.id}>
              {b.name} ({b.email})
              <Link to={`/borrowers/${b.id}/borrows`}>View Borrows</Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default BorrowerList;
