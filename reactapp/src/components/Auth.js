import React, { createContext, useContext, useState, useEffect } from 'react';
import api from '../utils/api';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  // Setup axios token on load
  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem('user'));
    const token = localStorage.getItem('token');

    if (storedUser && token) {
      setUser(storedUser);
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    }
  }, []);

  const login = async (username, password) => {
    const response = await api.post('/login', { username, password });
    const { token, role } = response.data;

    const userData = { username, role };

    localStorage.setItem('user', JSON.stringify(userData));
    localStorage.setItem('token', token);

    api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    setUser(userData);
  };

  const logout = () => {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    delete api.defaults.headers.common['Authorization'];
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
