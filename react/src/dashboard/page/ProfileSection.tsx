import React, { memo } from 'react';
import { Box, Typography, Avatar, Button } from '@mui/material';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import DirectionsRunIcon from '@mui/icons-material/DirectionsRun';
import InfoIcon from '@mui/icons-material/Info';
import { useNavigate } from 'react-router';
import { decryptData } from '@utils/crypto';
import Counter from '@components/Counter';

interface ProfileSectionProps {
  profile: any;
  pointData: any;
  stepsData: any;
  animate: boolean;
}

const ProfileSection: React.FC<ProfileSectionProps> = ({
  profile,
  pointData,
  stepsData,
  animate,
}) => {
  const navigate = useNavigate();

  return (
    <Box>
      <Box
        display='flex'
        alignItems='center'
        justifyContent='center'
        onClick={() => {
          navigate('/profile');
        }}
        sx={{
          mt: 4,
          mb: 4,
          p: 2,
          backgroundColor: '#fff',
          borderRadius: 2,
          boxShadow: 3,
        }}>
        <Avatar
          alt={profile?.result.userRealName}
          src={profile?.result.profileImg}
          sx={{ width: 56, height: 56, mr: 2 }}
        />
        <Box>
          <Typography variant='h6' sx={{ color: '#474554' }}>
            {decryptData(localStorage.getItem('name') || '')} 님
          </Typography>
          <Box>
            <Box display='flex' alignItems='center'>
              <Typography variant='body1' sx={{ color: '#474554' }}>
                적립 포인트: {pointData?.result.totalPoint}
              </Typography>
              <AttachMoneyIcon />
            </Box>
            <Box display='flex' alignItems='center'>
              <Typography variant='body1' sx={{ color: '#474554', mr: 1 }}>
                총 걸음 수:{' '}
                {stepsData && (
                  <Counter
                    value={
                      stepsData.result.stepTotalCount +
                      stepsData.result.stepTodayCount
                    }
                    duration={500}
                    animate={animate}
                  />
                )}
              </Typography>
              <DirectionsRunIcon sx={{ color: '#0288D1' }} />
            </Box>
          </Box>
        </Box>
      </Box>
      {profile?.result.isFirstTimeSetupDone === 'N' ? (
        <Box
          display='flex'
          alignItems='center'
          justifyContent='center'
          sx={{
            mb: 4,
          }}>
          <Button
            sx={{
              borderRadius: 20,
              padding: '10px 30px',
              fontSize: 12,
              fontWeight: 600,
              textTransform: 'none',
            }}
            onClick={() => {
              navigate('/profile');
            }}>
            <InfoIcon sx={{ color: '#fff', mr: 1 }} />
            추가 정보를 입력해 주세요!
          </Button>
        </Box>
      ) : null}
    </Box>
  );
};

export default memo(ProfileSection);
