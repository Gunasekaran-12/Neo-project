import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form } from 'react-bootstrap';
import { getFines, payFine } from '../utils/api';

const FineManagement = () => {
  const [fines, setFines] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showPaymentModal, setShowPaymentModal] = useState(false);
  const [selectedFine, setSelectedFine] = useState(null);
  const [paymentAmount, setPaymentAmount] = useState(0);

  useEffect(() => {
    fetchFines();
  }, []);

  const fetchFines = async () => {
    setLoading(true);
    try {
      const response = await getFines();
      setFines(response.data);
    } catch (error) {
      console.error('Error fetching fines:', error);
    } finally {
      setLoading(false);
    }
  };

  const handlePayment = (fine) => {
    setSelectedFine(fine);
    setPaymentAmount(fine.amountDue);
    setShowPaymentModal(true);
  };

  const processPayment = async () => {
    try {
      await payFine(selectedFine.id, {
        amount: paymentAmount,
        paymentDate: new Date().toISOString()
      });
      fetchFines();
      setShowPaymentModal(false);
    } catch (error) {
      console.error('Error processing payment:', error);
    }
  };

  return (
    <div>
      <h2>Fine Management</h2>
      {loading ? (
        <p>Loading fines...</p>
      ) : (
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Borrow ID</th>
              <th>Borrower</th>
              <th>Book</th>
              <th>Due Date</th>
              <th>Days Overdue</th>
              <th>Fine Rate</th>
              <th>Amount Due</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {fines.map(fine => (
              <tr key={fine.id}>
                <td>{fine.borrowRecord.id}</td>
                <td>{fine.borrowRecord.borrower.name}</td>
                <td>{fine.borrowRecord.book.title}</td>
                <td>{new Date(fine.borrowRecord.dueDate).toLocaleDateString()}</td>
                <td>{fine.daysOverdue}</td>
                <td>${fine.dailyRate.toFixed(2)}/day</td>
                <td>${fine.amountDue.toFixed(2)}</td>
                <td>
                  <span className={`badge ${fine.paid ? 'bg-success' : 'bg-danger'}`}>
                    {fine.paid ? 'Paid' : 'Unpaid'}
                  </span>
                </td>
               

                <td>
                  {!fine.paid && (
                    <Button
                      variant="warning"
                      size="sm"
                      onClick={() => handlePayment(fine)}
                    >
                      Record Payment
                    </Button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
      <Modal show={showPaymentModal} onHide={() => setShowPaymentModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Record Payment</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Borrower</Form.Label>
              <Form.Control
                type="text"
                value={selectedFine?.borrowRecord.borrower.name || ''}
                readOnly
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Book</Form.Label>
              <Form.Control
                type="text"
                value={selectedFine?.borrowRecord.book.title || ''}
                readOnly
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Amount Due</Form.Label>
              <Form.Control
                type="text"
                value={`$${selectedFine?.amountDue.toFixed(2) || '0.00'}`}
                readOnly
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Payment Amount</Form.Label>
              <Form.Control
                type="number"
                min="0"
                max={selectedFine?.amountDue || 0}
                step="0.01"
                value={paymentAmount}
                onChange={(e) => setPaymentAmount(parseFloat(e.target.value))}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowPaymentModal(false)}>
            Cancel
          </Button>
          <Button variant="primary" onClick={processPayment}>
            Confirm Payment
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default FineManagement;