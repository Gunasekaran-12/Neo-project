import React, { useState } from 'react';
import BookForm from './components/BookForm';
import BookList from './components/BookList';
import BorrowerForm from './components/BorrowerForm';
import BorrowerList from './components/BorrowerList';
import BorrowBook from './components/BorrowBook';
import './App.css';

function App() {
const [activeTab, setActiveTab] = useState('books');
  const [refreshKey, setRefreshKey] = useState(0);

  const handleBookAdded = () => {
    setRefreshKey(prev => prev + 1);
  };

  const handleBorrowerAdded = () => {
    setRefreshKey(prev => prev + 1);
  };

  const handleBorrowSuccess = () => {
    setRefreshKey(prev => prev + 1);
  };

  return (
    <div className="app">
      <header>
        <h1>Library Management System</h1>
        <nav>
          <button 
            className={activeTab === 'books' ? 'active' : ''} 
            onClick={() => setActiveTab('books')}
          >
            Books
          </button>
          <button 
            className={activeTab === 'borrowers' ? 'active' : ''} 
            onClick={() => setActiveTab('borrowers')}
          >
            Borrowers
          </button>
          <button 
            className={activeTab === 'borrow' ? 'active' : ''} 
            onClick={() => setActiveTab('borrow')}
          >
            Borrow Book
          </button>
        </nav>
      </header>

      <main>
        {activeTab === 'books' && (
          <div className="books-section">
            <BookForm onBookAdded={handleBookAdded} />
            <BookList key={refreshKey} onBookDeleted={handleBookAdded} />
          </div>
        )}

        {activeTab === 'borrowers' && (
          <div className="borrowers-section">
            <BorrowerForm onBorrowerAdded={handleBorrowerAdded} />
            <BorrowerList key={refreshKey} />
          </div>
        )}

        {activeTab === 'borrow' && (
          <div className="borrow-section">
            <BorrowBook onBorrowSuccess={handleBorrowSuccess} />
          </div>
        )}
      </main>
    </div>
  );
}

export default App;