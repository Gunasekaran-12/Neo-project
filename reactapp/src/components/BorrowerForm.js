import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import * as api from "../utils/api";

const BorrowerForm = () => {
  const [borrower, setBorrower] = useState({ name: "", email: "" });
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    await api.addBorrower(borrower);
    navigate("/borrowers");
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Name</label>
        <input value={borrower.name} onChange={e => setBorrower({ ...borrower, name: e.target.value })} />
      </div>
      <div>
        <label>Email</label>
        <input value={borrower.email} onChange={e => setBorrower({ ...borrower, email: e.target.value })} />
      </div>
      <button type="submit">Save</button>
    </form>
  );
};

export default BorrowerForm;
