import React, { useEffect } from 'react';
import { Box, CircularProgress, Typography, CssBaseline } from '@mui/material';

const LoadingScreen = () => {
  useEffect(() => {
    // Hide the status bar
    document.body.style.overflow = 'hidden';

    return () => {
      // Restore the original state
      document.body.style.overflow = 'auto';
    };
  }, []);

  return (
    <Box
      display='flex'
      justifyContent='center'
      alignItems='center'
      height='100vh'
      bgcolor='#F5F5F5'>
      <CssBaseline />
      <Box textAlign='center'>
        <CircularProgress size={60} style={{ color: '#0288D1' }} />
        <Typography variant='h6' style={{ marginTop: 10, color: '#757575' }}>
          로딩 중... 건강한 하루를 시작해 볼까요?
        </Typography>
      </Box>
    </Box>
  );
};

export default LoadingScreen;
