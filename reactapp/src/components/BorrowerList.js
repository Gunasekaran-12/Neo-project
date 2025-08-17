import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { fetchBorrowers } from "../utils/api";

const BorrowerList = () => {
  const [borrowers, setBorrowers] = useState([]);
  const [loading, setLoading] = useState(true);

  const load = async () => {
    setLoading(true);
    const res = await fetchBorrowers();
    setBorrowers(res.data || []);
    setLoading(false);
  };

  useEffect(() => {
    load();
  }, []);

  if (loading) return <p>Loading borrowers...</p>;
  if (!borrowers.length) return <p>No borrowers found</p>;

  return (
    <div>
      <h2>Borrowers</h2>
      <Link to="/borrowers/add">Add New Borrower</Link>
      <table>
        <thead>
          <tr>
            <th>Name</th><th>Email</th><th>Phone</th><th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {borrowers.map((b) => (
            <tr key={b.id}>
              <td>{b.name}</td>
              <td>{b.email}</td>
              <td>{b.phoneNumber}</td>
              <td>
                <Link to={`/borrowers/edit/${b.id}`}>Edit</Link>{" "}
                <Link to={`/borrowers/${b.id}/borrows`}>View Borrows</Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BorrowerList;
