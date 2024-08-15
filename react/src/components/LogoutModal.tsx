import React, { memo } from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';

interface LogoutModalProps {
  open: boolean;
  handleClose: () => void;
  handleLogout: () => void;
}

const LogoutModal: React.FC<LogoutModalProps> = ({
  open,
  handleClose,
  handleLogout,
}) => {
  return (
    <Modal open={open} onClose={handleClose}>
      <Box
        sx={{
          position: 'absolute' as const,
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 300,
          bgcolor: '#ffffff',
          border: '2px solid #000',
          boxShadow: 24,
          p: 4,
        }}>
        <Typography variant='h6' component='h2'>
          적립하지 않은 포인트는 <br /> 사라집니다.
        </Typography>
        <Typography sx={{ mt: 2 }}>로그아웃 하시겠습니까?</Typography>
        <Box mt={2} display='flex' justifyContent='space-between'>
          <Button variant='contained' onClick={handleLogout}>
            로그아웃
          </Button>
          <Button variant='contained' onClick={handleClose}>
            취소
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};

export default memo(LogoutModal);
