import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from './Auth';

const ProtectedRoute = ({ roles = [] }) => {
  const { user } = useAuth();

  if (!user) return <Navigate to="/login" />;
  if (roles.length > 0 && !roles.includes(user.role)) {
    return <Navigate to="/unauthorized" />;
  }

  return <Outlet />;
};

export default ProtectedRoute;
