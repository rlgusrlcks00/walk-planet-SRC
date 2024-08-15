import React, { useState, useEffect, memo } from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';
import { UserProfileResponse } from '@profile/types/UserProfileTypes';

interface WalkMateModalProps {
  open: boolean;
  handleClose: () => void;
  steps: number;
  stepGoals: number;
  point: number;
  profile: UserProfileResponse;
}

const WalkMateModal: React.FC<WalkMateModalProps> = ({
  open,
  handleClose,
  steps,
  stepGoals,
  point,
  profile,
}) => {
  const [distance, setDistance] = useState<number>(0);
  const [calories, setCalories] = useState<number>(0);

  useEffect(() => {
    calculateDistanceAndCalories(steps);
  }, [steps]);

  const calculateDistanceAndCalories = (steps: number) => {
    const distanceInKm = (steps * 0.78) / 1000;
    setDistance(distanceInKm);

    const caloriesBurned = distanceInKm * 70;
    setCalories(caloriesBurned);
  };

  const style = {
    position: 'absolute' as const,
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 300,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
    borderRadius: 2,
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Typography variant='h6' component='h2'>
          {profile.result.userRealName}
        </Typography>
        <Typography sx={{ mt: 2 }}>오늘 걸음 수 : {steps}</Typography>
        <Typography sx={{ mt: 2 }}>목표 걸음 수 : {stepGoals}</Typography>
        <Typography sx={{ mt: 2 }}>포인트 : {point}</Typography>
        <Typography sx={{ mt: 2 }}>칼로리 : {calories.toFixed(2)}</Typography>
        <Typography sx={{ mt: 2 }}>
          걸은 거리 : {distance.toFixed(2)}
        </Typography>
        <Box
          sx={{
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            mt: 2,
          }}>
          <Button variant='contained' color='secondary' onClick={handleClose}>
            Close
          </Button>
          <img
            src={`${process.env.PUBLIC_URL}/defaultProfile.png`}
            alt='Profile'
            style={{ width: '90px', height: '125px', marginLeft: 'auto' }} // 이미지 크기 조절
          />
        </Box>
      </Box>
    </Modal>
  );
};

export default memo(WalkMateModal);
