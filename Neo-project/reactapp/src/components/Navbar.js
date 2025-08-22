// src/components/Navbar.js
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useTheme } from '../context/ThemeContext';
import { Bars3Icon, XMarkIcon, SunIcon, MoonIcon } from '@heroicons/react/24/outline';

const Navbar = () => {
  const { user, logout } = useAuth();
  const { darkMode, toggleTheme } = useTheme();
  const navigate = useNavigate();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const navLinks = user
    ? [
        { name: 'Dashboard', to: '/dashboard' },
        { name: 'Books', to: '/books' },
        { name: 'Add Book', to: '/books/add' },
        { name: 'Borrowers', to: '/borrowers' },
        { name: 'Add Borrower', to: '/borrowers/add' },
        { name: 'Borrow Book', to: '/borrow' },
        { name: 'Reports', to: '/reports' },
        { name: 'Fines', to: '/fines' },
        { name: 'Search', to: '/search' },
      ]
    : [];

  return (
    <nav className={`navbar ${darkMode ? 'dark' : 'light'}`}>
      <div className="nav-container">
        <Link to="/" className="brand">
          Library System
        </Link>

        <div className="desktop-links">
          {navLinks.map((link) => (
            <Link
              key={link.name}
              to={link.to}
              className="nav-link"
              onClick={() => setMobileMenuOpen(false)}
            >
              {link.name}
            </Link>
          ))}
        </div>

        <div className="right-controls">
          <button
            onClick={toggleTheme}
            className="theme-toggle"
            aria-label={darkMode ? 'Switch to light mode' : 'Switch to dark mode'}
          >
            {darkMode ? (
              <SunIcon className="icon" />
            ) : (
              <MoonIcon className="icon" />
            )}
          </button>

   
          {user ? (
            <>
              <span className="welcome-message">
                Welcome, <strong>{user.name}</strong>
              </span>
              <button onClick={handleLogout} className="logout-btn">
                Logout
              </button>
            </>
          ) : (
            <Link to="/login" className="login-btn">
              Login
            </Link>
          )}
        </div>

        <div className="mobile-controls">
          <button
            onClick={toggleTheme}
            className="theme-toggle"
            aria-label={darkMode ? 'Switch to light mode' : 'Switch to dark mode'}
          >
            {darkMode ? (
              <SunIcon className="icon" />
            ) : (
              <MoonIcon className="icon" />
            )}
          </button>

          <button
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            className="mobile-menu-btn"
            aria-label="Toggle menu"
          >
            {mobileMenuOpen ? (
              <XMarkIcon className="icon" />
            ) : (
              <Bars3Icon className="icon" />
            )}
          </button>
        </div>
      </div>

      {mobileMenuOpen && (
        <div className={`mobile-menu ${darkMode ? 'dark' : 'light'}`}>
          {navLinks.map((link) => (
            <Link
              key={link.name}
              to={link.to}
              onClick={() => setMobileMenuOpen(false)}
              className="mobile-nav-link"
            >
              {link.name}
            </Link>
          ))}
          <div className="mobile-auth-section">
            {user ? (
              <button
                onClick={() => {
                  handleLogout();
                  setMobileMenuOpen(false);
                }}
                className="mobile-logout-btn"
              >
                Logout
              </button>
            ) : (
              <Link
                to="/login"
                onClick={() => setMobileMenuOpen(false)}
                className="mobile-login-btn"
              >
                Login
              </Link>
            )}
          </div>
        </div>
      )}
    </nav>
  );
};

export default Navbar;