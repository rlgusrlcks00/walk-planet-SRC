import React, { memo, useState } from 'react';
import {
  Box,
  Container,
  Typography,
  Avatar,
  Paper,
  Grid,
  Button,
} from '@mui/material';
import { useQuery, useQueryClient } from 'react-query';
import MainAppBar from '@components/MainAppBar';
import { decryptData } from '@utils/crypto';
import useLogout from '@hooks/useLogout';
import LogoutModal from '@components/LogoutModal';
import { getPointInfo } from '@dashboard/api/PointInfoApi';
import { getStepInfo } from '@dashboard/api/StepInfoApi';
import DirectionsRunIcon from '@mui/icons-material/DirectionsRun';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import { UserProfileRequest } from '@profile/types/UserProfileTypes';
import { userProfileInitial } from '@profile/types/UserProfileInitial';
import {
  getUserProfileInfo,
  setUserProfileInfo,
} from '@profile/api/UserProfileApi';
import UserProfileModal from '@profile/page/UserProfileModal';
import EditIcon from '@mui/icons-material/Edit';
import ManIcon from '@mui/icons-material/Man';
import WomanIcon from '@mui/icons-material/Woman';
import WcIcon from '@mui/icons-material/Wc';

const ProfilePage: React.FC = () => {
  const [open, setOpen] = useState(false);
  const [bmi, setBmi] = useState<number | null>(null);
  const {
    openLogoutModal,
    handleLogoutModalOpen,
    handleLogoutModalClose,
    handleLogoutStart,
  } = useLogout();

  const queryClient = useQueryClient();

  const { data: profile, isLoading: profileLoading } = useQuery(
    'userProfile',
    getUserProfileInfo,
    {
      onSuccess: (data) => {
        if (data.result.height && data.result.weight) {
          const bmiValue = data.result.weight / (data.result.height / 100) ** 2;
          setBmi(bmiValue);
        }
      },
    },
  );

  const { data: stepData, isLoading: stepsLoading } = useQuery(
    'steps',
    getStepInfo,
  );

  const { data: pointData, isLoading: pointLoading } = useQuery(
    'points',
    getPointInfo,
  );

  const saveUserProfileApi = async (profileData: UserProfileRequest) => {
    try {
      await setUserProfileInfo(profileData);
      await queryClient.invalidateQueries('userProfile');
    } catch (error) {
      console.error(error);
    }
  };

  const handleUserProfileModalOpen = () => {
    setOpen(true);
  };

  const handleUserProfileModalClose = () => {
    setOpen(false);
  };

  return (
    <Container maxWidth='lg' sx={{ mt: 4, mb: 4 }}>
      <MainAppBar onLogout={handleLogoutModalOpen} />
      <Box sx={{ mt: 4, mb: 4 }}>
        <Paper sx={{ p: 4, borderRadius: 2, boxShadow: 3 }}>
          <Grid container spacing={2} alignItems='center'>
            <Grid item>
              <Avatar
                alt={profile?.result.userRealName}
                src={profile?.result.profileImg}
                sx={{ width: 100, height: 100 }}
              />
            </Grid>
            <Grid item xs>
              <Typography sx={{ fontSize: 20 }}>
                {profile?.result.userRealName ||
                  decryptData(localStorage.getItem('name') || '')}
                {profile?.result.nickname && `(${profile?.result.nickname})`}
              </Typography>
              <Typography variant='body1' color='textSecondary'>
                {profile?.result.userEmail ||
                  decryptData(localStorage.getItem('email') || '')}
              </Typography>
            </Grid>
          </Grid>
          <Box sx={{ mt: 4 }}>
            <Typography variant='h6' sx={{ fontWeight: 'bold' }}>
              내 정보
            </Typography>
            <Box display='flex' alignItems='center'>
              <Typography variant='body1' sx={{ color: '#474554', mr: 1 }}>
                적립 포인트: {pointData?.result.totalPoint || 0}
              </Typography>
              <AttachMoneyIcon />
            </Box>
            <Box display='flex' alignItems='center'>
              <Typography
                variant='body1'
                sx={{ color: '#474554', mr: 1, mt: 1 }}>
                총 걸음 수: {stepData?.result.stepTotalCount || 0}
              </Typography>
              <DirectionsRunIcon sx={{ color: '#0288D1' }} />
            </Box>
            <Typography variant='body1' sx={{ mt: 1 }}>
              생일: {profile?.result.birth || '정보 없음'}
            </Typography>
            <Typography variant='body1' sx={{ mt: 1 }}>
              <Box
                component='span'
                sx={{ display: 'inline-flex', alignItems: 'center' }}>
                성별:
                {profile?.result.gender === 'male' ? (
                  <ManIcon sx={{ color: '#0288D1' }} />
                ) : profile?.result.gender === 'female' ? (
                  <WomanIcon sx={{ color: '#F44336' }} />
                ) : (
                  <WcIcon sx={{ color: '#000' }} />
                )}
              </Box>
            </Typography>
            <Typography variant='h6' sx={{ mt: 4, fontWeight: 'bold' }}>
              건강 정보
            </Typography>
            <Typography variant='body1'>
              키: {profile?.result.height || '정보 없음'}
            </Typography>
            <Typography variant='body1' sx={{ mt: 1 }}>
              몸무게: {profile?.result.weight || '정보 없음'}
            </Typography>
            <Typography variant='body1' sx={{ mt: 1 }}>
              목표 몸무게: {profile?.result.weightGoals || '정보 없음'}
            </Typography>
            <Typography variant='body1' sx={{ mt: 1 }}>
              BMI: {bmi !== null ? bmi.toFixed(2) : '정보 없음'}
            </Typography>
          </Box>
          <Box display='flex' justifyContent='flex-end' sx={{ mt: 2 }}>
            <Button
              variant='contained'
              color='primary'
              onClick={handleUserProfileModalOpen}>
              <EditIcon sx={{ color: '#fff' }} />
            </Button>
          </Box>{' '}
        </Paper>
      </Box>

      <LogoutModal
        open={openLogoutModal}
        handleClose={handleLogoutModalClose}
        handleLogout={handleLogoutStart}
      />

      <UserProfileModal
        open={open}
        handleClose={handleUserProfileModalClose}
        handleSave={saveUserProfileApi}
        initialData={profile || userProfileInitial}
      />
    </Container>
  );
};

export default memo(ProfilePage);
