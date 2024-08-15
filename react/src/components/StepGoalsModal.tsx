import React, { memo, useState } from 'react';
import { Modal, Box, Typography, Button, TextField } from '@mui/material';

interface StepGoalsModalProps {
  open: boolean;
  stepGoals: number;
  handleClose: () => void;
  handleSave: (newStepGoals: number) => void;
}

const StepGoalsModal: React.FC<StepGoalsModalProps> = ({
  open,
  stepGoals,
  handleClose,
  handleSave,
}) => {
  const [newStepGoals, setNewStepGoals] = useState(stepGoals);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewStepGoals(Number(e.target.value));
  };

  const handleCloseClick = () => {
    setNewStepGoals(0);
    handleClose();
  };

  const handleSaveClick = () => {
    handleSave(newStepGoals);
  };

  return (
    <Modal open={open} onClose={handleCloseClick}>
      <Box
        sx={{
          position: 'absolute' as const,
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 320,
          height: 400,
          bgcolor: '#ffffff',
          border: '2px solid #000',
          boxShadow: 24,
          p: 4,
          borderRadius: 2,
        }}>
        <Typography variant='h6' sx={{ fontSize: 18, mb: 2 }}>
          목표 걸음수를 설정해 주세요.
        </Typography>
        <Typography sx={{ mb: 2 }}>현재 목표 걸음수: {stepGoals}</Typography>
        <TextField
          fullWidth
          type='text'
          label='새 목표 걸음수'
          value={newStepGoals}
          onChange={handleInputChange}
          sx={{ mb: 2 }}
          inputProps={{ inputMode: 'numeric', pattern: '[0-9]*' }}
        />
        <Typography sx={{ fontSize: 12, mb: 2 }}>
          목표 걸음수를 달성하면 기존 걸음수에 더해 목표 걸음수만큼 추가
          포인트를 받을 수 있어요!
        </Typography>
        <Typography sx={{ fontSize: 10, mb: 2, color: 'red' }}>
          목표 걸음수는 하루에 한번만 설정 할 수 있습니다. <br />
          (최초 가입 시 다음 날 부터 설정 가능)
        </Typography>
        <Box display='flex' justifyContent='space-between'>
          <Button variant='contained' color='primary' onClick={handleSaveClick}>
            저장
          </Button>
          <Button
            variant='contained'
            color='secondary'
            onClick={handleCloseClick}>
            취소
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};

export default memo(StepGoalsModal);
