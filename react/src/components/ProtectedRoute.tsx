import React, { ReactElement } from 'react';
import { Navigate } from 'react-router-dom';

interface ProtectedRouteProps {
  element: ReactElement;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ element }) => {
  const token = localStorage.getItem('token');
  return token ? element : <Navigate to='/login' replace />;
};

export default ProtectedRoute;
