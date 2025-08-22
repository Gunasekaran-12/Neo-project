import React, { useState } from 'react';
import { Button, Table, Form, Row, Col, Card, Spinner, Alert } from 'react-bootstrap';
import { CSVLink } from 'react-csv';
import { generateReportData } from '../utils/api';

const getValue = (obj, path) => {
  return path.split('.').reduce((o, i) => (o ? o[i] : ''), obj);
};

const Reports = () => {
  const [reportType, setReportType] = useState('overdue');
  const [reportData, setReportData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [dateRange, setDateRange] = useState({
    start: new Date().toISOString().split('T')[0],
    end: new Date().toISOString().split('T')[0]
  });

  const headers = {
    overdue: [
      { label: 'Borrow ID', key: 'id' },
      { label: 'Book Title', key: 'book.title' },
      { label: 'Borrower', key: 'borrower.name' },
      { label: 'Due Date', key: 'dueDate' },
      { label: 'Days Overdue', key: 'daysOverdue' },
      { label: 'Fine Amount', key: 'fineAmount' }
    ],
    borrowing: [
      { label: 'Borrow ID', key: 'id' },
      { label: 'Book Title', key: 'book.title' },
      { label: 'Borrower Name', key: 'borrower.name' },
      { label: 'Borrow Date', key: 'borrowDate' },
      { label: 'Due Date', key: 'dueDate' },
      { label: 'Status', key: 'status' }
    ],
    popular: [
      { label: 'Book Title', key: 'title' },
      { label: 'Author', key: 'author' },
      { label: 'Times Borrowed', key: 'borrowCount' }
    ]
  };

  const handleGenerateReport = async () => {
    setLoading(true);
    setError('');
    setReportData([]);

    try {
      const response = await generateReportData(reportType, dateRange);
      setReportData(response.data);
    } catch (err) {
      console.error(err);
      setError('Failed to generate report. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4">📊 Generate Reports</h2>

      <Card className="mb-4 shadow-sm">
        <Card.Body>
          <Row className="mb-3">
            <Col md={4}>
              <Form.Group>
                <Form.Label>Report Type</Form.Label>
                <Form.Select
                  value={reportType}
                  onChange={(e) => setReportType(e.target.value)}
                >
                  <option value="overdue">Overdue Books</option>
                  <option value="borrowing">Borrowing Activity</option>
                  <option value="popular">Most Popular Books</option>
                </Form.Select>
              </Form.Group>
            </Col>
            <Col md={4}>
              <Form.Group>
                <Form.Label>Start Date</Form.Label>
                <Form.Control
                  type="date"
                  value={dateRange.start}
                  onChange={(e) =>
                    setDateRange({ ...dateRange, start: e.target.value })
                  }
                />
              </Form.Group>
            </Col>
            <Col md={4}>
              <Form.Group>
                <Form.Label>End Date</Form.Label>
                <Form.Control
                  type="date"
                  value={dateRange.end}
                  onChange={(e) =>
                    setDateRange({ ...dateRange, end: e.target.value })
                  }
                />
              </Form.Group>
            </Col>
          </Row>
          <div className="text-end">
            <Button
              variant="primary"
              onClick={handleGenerateReport}
              disabled={loading}
            >
              {loading ? (
                <>
                  <Spinner animation="border" size="sm" className="me-2" />
                  Generating...
                </>
              ) : (
                'Generate Report'
              )}
            </Button>
          </div>
        </Card.Body>
      </Card>

      {error && <Alert variant="danger">[Error - You need to specify the message]</Alert>}
      {reportData.length > 0 ? (
        <>
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h5 className="mb-0">Report Results</h5>
            <CSVLink
              data={reportData}
              headers={headers[reportType]}
              filename={`${reportType}_report.csv`}
              className="btn btn-success"
            >
              Export to CSV
            </CSVLink>
          </div>

 
          <Table striped bordered hover responsive>
            <thead>
              <tr>
                {headers[reportType].map((header) => (
                  <th key={header.key}>{header.label}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {reportData.map((item) => (
                <tr key={item.id || item.title}>
                  {headers[reportType].map((header) => (
                    <td key={header.key}>{getValue(item, header.key)}</td>
                  ))}
                </tr>
              ))}
            </tbody>
          </Table>
        </>
      ) : (
        !loading && (
          <div className="text-muted text-center py-4">
            No report data to display. Please generate a report.
          </div>
        )
      )}
    </div>
  );
};

export default Reports;