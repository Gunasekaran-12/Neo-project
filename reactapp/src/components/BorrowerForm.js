import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { addBorrower, updateBorrower, getBorrower } from "../utils/api";

const BorrowerForm = () => {
  const [form, setForm] = useState({ name: "", email: "", phoneNumber: "" });
  const [errors, setErrors] = useState({});
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      getBorrower(id).then((res) => setForm(res.data));
    }
  }, [id]);

  const validate = () => {
    const e = {};
    if (!form.name) e.name = "Name is required";
    else if (form.name.length > 50) e.name = "Name must be at most 50 characters";

    if (!form.email) e.email = "Email is required";
    else if (!/^\S+@\S+\.\S+$/.test(form.email)) e.email = "Invalid email";

    if (!form.phoneNumber) e.phoneNumber = "Phone is required";
    else if (!/^\d{10}$/.test(form.phoneNumber))
      e.phoneNumber = "Phone must be exactly 10 digits";

    return e;
  };

  const handleSubmit = async (evt) => {
    evt.preventDefault();
    const e = validate();
    if (Object.keys(e).length) {
      setErrors(e);
      return;
    }
    if (id) await updateBorrower(id, form);
    else await addBorrower(form);
    navigate("/borrowers");
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Name</label>
        <input
          value={form.name}
          onChange={(ev) => setForm({ ...form, name: ev.target.value })}
        />
        {errors.name && <p>{errors.name}</p>}
      </div>

      <div>
        <label>Email</label>
        <input
          value={form.email}
          onChange={(ev) => setForm({ ...form, email: ev.target.value })}
        />
        {errors.email && <p>{errors.email}</p>}
      </div>

      <div>
        <label>Phone</label>
        <input
          value={form.phoneNumber}
          onChange={(ev) => setForm({ ...form, phoneNumber: ev.target.value })}
        />
        {errors.phoneNumber && <p>{errors.phoneNumber}</p>}
      </div>

      <button type="submit">Save</button>
    </form>
  );
};

export default BorrowerForm;
