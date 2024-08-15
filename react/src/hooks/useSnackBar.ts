import { useState } from 'react';

const useSnackBar = () => {
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [snackbarSeverity, setSnackbarSeverity] = useState<'success' | 'error'>(
    'success',
  );

  /////////////////////////////////////////////
  // Snackbar를 띄우는 함수
  /////////////////////////////////////////////
  const showSnackbar = (message: string, severity: 'success' | 'error') => {
    setSnackbarMessage(message);
    setSnackbarSeverity(severity);
    setSnackbarOpen(true);
  };

  /////////////////////////////////////////////
  // Snackbar를 닫는 함수
  /////////////////////////////////////////////
  const handleSnackbarClose = () => {
    setSnackbarOpen(false);
  };

  return {
    snackbarOpen,
    snackbarMessage,
    snackbarSeverity,
    showSnackbar,
    handleSnackbarClose,
  };
};

export default useSnackBar;
