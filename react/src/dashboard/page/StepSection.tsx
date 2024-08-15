import React, { memo } from 'react';
import {
  Box,
  Typography,
  Card,
  CardContent,
  CircularProgress,
  Button,
  CardActions,
} from '@mui/material';
import DirectionsWalkIcon from '@mui/icons-material/DirectionsWalk';
import LocalFireDepartmentIcon from '@mui/icons-material/LocalFireDepartment';
import LinearScaleIcon from '@mui/icons-material/LinearScale';
import Counter from '@components/Counter';

interface StepSectionProps {
  steps: number;
  calories: number;
  distance: number;
  animate: boolean;
  stepGoalsData: any;
  progress: number;
  handleStepGoalsModalOpen: () => void;
  handleSaveButtonClick: () => void;
  canSaveDb: boolean;
  stepsDifference: number;
}

const StepSection: React.FC<StepSectionProps> = ({
  steps,
  calories,
  distance,
  animate,
  stepGoalsData,
  progress,
  handleStepGoalsModalOpen,
  handleSaveButtonClick,
  canSaveDb,
  stepsDifference,
}) => {
  return (
    <>
      <Card
        sx={{
          display: 'flex',
          alignItems: 'center',
          p: 3,
          boxShadow: 3,
          borderRadius: 3,
        }}>
        <CardContent
          sx={{
            flex: 1,
            display: 'flex',
            flexDirection: 'column',
            gap: 2,
          }}>
          <Box display='flex' alignItems='center' gap={2}>
            <DirectionsWalkIcon sx={{ fontSize: 50 }} />
            <Box>
              <Typography variant='h6'>오늘의 걸음</Typography>
              <Typography variant='h4'>
                <Counter value={steps} duration={500} animate={animate} />
              </Typography>
            </Box>
          </Box>
          <Box display='flex' alignItems='center' gap={2}>
            <LocalFireDepartmentIcon sx={{ fontSize: 50, color: '#F44336' }} />
            <Box>
              <Typography variant='h6'>칼로리</Typography>
              <Typography variant='h4'>
                <Counter
                  value={parseFloat(calories.toFixed(2))}
                  duration={500}
                  float={true}
                  animate={animate}
                />
              </Typography>
            </Box>
          </Box>
          <Box display='flex' alignItems='center' gap={2}>
            <LinearScaleIcon sx={{ fontSize: 50, color: '#4CAF50' }} />
            <Box>
              <Typography variant='h6'>거리 (km)</Typography>
              <Typography variant='h4'>
                <Counter
                  value={parseFloat(distance.toFixed(2))}
                  duration={500}
                  float={true}
                  animate={animate}
                />
              </Typography>
            </Box>
          </Box>
        </CardContent>
        <Box sx={{ display: 'flex', flexDirection: 'column' }}>
          <Typography
            variant='caption'
            sx={{ textAlign: 'center', color: '#0288D1' }}>
            {`${stepGoalsData?.result.stepGoalsCount} 걸음 도전!`}
          </Typography>
          <CardContent
            sx={{
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
            }}>
            <Box position='relative' display='inline-flex'>
              <CircularProgress
                variant='determinate'
                value={100}
                size={80}
                thickness={4}
                sx={{ color: '#B0BEC5' }}
              />
              <CircularProgress
                variant='determinate'
                value={progress >= 100 ? 100 : progress}
                size={80}
                thickness={4}
                sx={{
                  color: progress >= 100 ? '#4CAF50' : '#0288D1',
                  position: 'absolute',
                  left: 0,
                }}
              />
              <Box
                top={0}
                left={0}
                bottom={0}
                right={0}
                position='absolute'
                display='flex'
                alignItems='center'
                justifyContent='center'>
                <Typography
                  variant='caption'
                  component='div'
                  color='textSecondary'>
                  <Counter
                    value={
                      Math.round(progress) >= 100 ? 100 : Math.round(progress)
                    }
                    duration={500}
                    animate={animate}
                  />
                  %
                </Typography>
              </Box>
            </Box>
          </CardContent>
          <Button onClick={handleStepGoalsModalOpen}>목표 설정하기</Button>
        </Box>
      </Card>
      <CardActions sx={{ justifyContent: 'center', mt: 2 }}>
        <Button
          variant='contained'
          onClick={handleSaveButtonClick}
          disabled={!canSaveDb}
          sx={{
            cursor: canSaveDb ? 'pointer' : 'not-allowed',
          }}>
          {canSaveDb ? `${stepsDifference} 포인트 적립` : '열심히 걸어주세요.'}
        </Button>
      </CardActions>
    </>
  );
};

export default memo(StepSection);
