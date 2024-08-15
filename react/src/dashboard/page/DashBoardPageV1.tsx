import React, { useEffect, useState, useCallback, memo } from 'react';
import {
  Container,
  Box,
  Typography,
  Grid,
  Paper,
  Button,
  Card,
  CardActions,
  Avatar,
  CardContent,
  CircularProgress,
  Snackbar,
  Alert,
  Skeleton,
} from '@mui/material';
import DirectionsWalkIcon from '@mui/icons-material/DirectionsWalk';
import LocalFireDepartmentIcon from '@mui/icons-material/LocalFireDepartment';
import LinearScaleIcon from '@mui/icons-material/LinearScale';
import MainAppBar from '@components/MainAppBar';
import { decryptData } from '@utils/crypto';
import {
  getStepGoals,
  getStepInfo,
  setStepGoalsCount,
  setStepInfo,
} from '@dashboard/api/StepInfoApi';
import DirectionsRunIcon from '@mui/icons-material/DirectionsRun';
import LogoutModal from '@components/LogoutModal';
import { useNavigate } from 'react-router';
import { getPointInfo, setPointInfo } from '@dashboard/api/PointInfoApi';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import StepGoalsModal from '@components/StepGoalsModal';
import useLogout from '@hooks/useLogout';
import { UserProfileResponse } from '@profile/types/UserProfileTypes';
import { userProfileInitial } from '@profile/types/UserProfileInitial';
import { getUserProfileInfo } from '@profile/api/UserProfileApi';
import InfoIcon from '@mui/icons-material/Info';
import useSnackBar from '@hooks/useSnackBar';
import { useQuery, useQueryClient } from 'react-query';
import Counter from '@components/Counter';

const DashboardPageV1: React.FC = () => {
  const [distance, setDistance] = useState(0);
  const [steps, setSteps] = useState(0);
  const [calories, setCalories] = useState(0);
  const [dbSteps, setDbSteps] = useState(0);
  const [messageCount, setMessageCount] = useState(0);
  const [canSaveDb, setCanSaveDb] = useState(false);
  const [stepGoals, setStepGoals] = useState(0);
  const [animate, setAnimate] = useState(true);
  const [progress, setProgress] = useState(0);
  const [openStepGoalsModal, setOpenStepGoalsModal] = useState(false);
  const navigate = useNavigate();
  const {
    openLogoutModal,
    handleLogoutModalOpen,
    handleLogoutModalClose,
    handleLogoutStart,
  } = useLogout();
  const {
    snackbarOpen,
    snackbarMessage,
    snackbarSeverity,
    showSnackbar,
    handleSnackbarClose,
  } = useSnackBar();
  const queryClient = useQueryClient();

  /////////////////////////////////////////////
  // 사용자 정보를 가져오고 저장하는 함수들
  /////////////////////////////////////////////
  const { data: profile, isLoading: profileLoading } = useQuery(
    'userProfile',
    getUserProfileInfo,
  );

  /////////////////////////////////////////////
  // 걸음수 정보를 가져오고 저장하는 함수들
  /////////////////////////////////////////////
  const { data: stepsData, isLoading: stepsLoading } = useQuery(
    'stepsData',
    getStepInfo,
    {
      onSuccess: (data) => {
        sendDataToApp({ action: 'giveMeSteps' });
        setDbSteps(data.result.stepTodayCount);
        manageAnimate();
      },
    },
  );

  /////////////////////////////////////////////
  // 포인트 정보를 가져오고 저장하는 함수들
  /////////////////////////////////////////////
  const { data: pointData, isLoading: pointLoading } = useQuery(
    'pointData',
    getPointInfo,
  );

  /////////////////////////////////////////////
  // 목표 걸음수 정보를 가져오는 함수
  /////////////////////////////////////////////
  const { data: stepGoalsData, isLoading: stepGoalsLoading } = useQuery(
    'stepGoalsData',
    getStepGoals,
  );

  /////////////////////////////////////////////
  // 걸음수에 따른 거리와 칼로리 계산 함수
  /////////////////////////////////////////////
  const calculateDistanceAndCalories = (steps: number) => {
    const distanceInKm = (steps * 0.78) / 1000;
    setDistance(distanceInKm);

    const caloriesBurned = distanceInKm * 70;
    setCalories(caloriesBurned);
  };

  const manageAnimate = () => {
    setAnimate(true);
    setTimeout(() => {
      setAnimate(false);
    }, 3000);
  };

  /////////////////////////////////////////////
  // 걸음수 정보를 저장하는 API 함수
  /////////////////////////////////////////////
  const saveStepsApi = async (step: number) => {
    try {
      await setStepInfo(step);
      setCanSaveDb(false);
    } catch (error) {
      console.error(error);
    }
  };

  /////////////////////////////////////////////
  // 포인트 정보를 저장하는 API 함수
  /////////////////////////////////////////////
  const savePointApi = async (point: number) => {
    try {
      await setPointInfo(point);
      setCanSaveDb(false);
      showSnackbar('포인트가 저장되었습니다.', 'success');
    } catch (error) {
      console.error(error);
      showSnackbar('포인트 저장에 실패했습니다.', 'error');
    }
  };

  /////////////////////////////////////////////
  // 목표 걸음수 정보를 저장하는 API 함수
  /////////////////////////////////////////////
  const saveStepGoalsApi = async (stepGoals: number) => {
    try {
      const response = await setStepGoalsCount(stepGoals);
      if (response.result) {
        setStepGoals(stepGoals);
        showSnackbar('목표 걸음 수가 저장되었습니다.', 'success');
      } else {
        showSnackbar('오늘 목표 걸음 수를 설정할 수 없습니다.', 'error');
      }
    } catch (error) {
      console.error(error);
    }
  };

  /////////////////////////////////////////////
  // React Native에 메시지를 전송하는 함수 ex) sendDataToApp({ action: "resetSteps" });
  /////////////////////////////////////////////
  const sendDataToApp = (data: any) => {
    if (window.ReactNativeWebView && window.ReactNativeWebView.postMessage) {
      window.ReactNativeWebView.postMessage(JSON.stringify(data));
    } else {
      console.warn('ReactNativeWebView is not defined');
    }
  };

  /////////////////////////////////////////////
  // 걸음수 정보를 버튼으로 눌러서 저장하는 함수
  /////////////////////////////////////////////
  const handleSaveButtonClick = async () => {
    if (!canSaveDb) return;

    setCanSaveDb(false);
    sendDataToApp({ action: 'resetSteps' });
    try {
      await savePointApi(steps - dbSteps);
      await saveStepsApi(steps);
      await queryClient.invalidateQueries('stepsData');
      await queryClient.invalidateQueries('pointData');
    } catch (error) {
      console.error(error);
    }
  };

  /////////////////////////////////////////////
  // 목표 설정 모달창에서 저장 버튼을 눌렀을 때 처리하는 함수
  /////////////////////////////////////////////
  const handleStepGoalsSave = async (newStepGoals: number) => {
    await saveStepGoalsApi(newStepGoals);
    await queryClient.invalidateQueries('stepGoalsData');
    setOpenStepGoalsModal(false);
  };

  /////////////////////////////////////////////
  // 목표 설정 모달창을 여는 함수
  /////////////////////////////////////////////
  const handleStepGoalsModalOpen = () => {
    setOpenStepGoalsModal(true);
  };

  /////////////////////////////////////////////
  // 목표 설정 모달창을 닫는 함수
  /////////////////////////////////////////////
  const handleStepGoalsModalClose = () => {
    setOpenStepGoalsModal(false);
  };

  /////////////////////////////////////////////
  // React Native에서 보낸 메시지를 필터링해서 처리하는 함수 (리시버 같은 느낌)
  /////////////////////////////////////////////
  const handleMessage = (event: MessageEvent) => {
    try {
      const data = JSON.parse(event.data);
      console.log('Received message:', data);
      if (data.steps !== undefined) {
        setSteps(data.steps);
        setMessageCount((prevCount) => prevCount + 1);
      }
    } catch (error) {
      console.error('Failed to parse message data:', error);
    }
  };

  /////////////////////////////////////////////
  // 걸음수가 변경 될 때 마다 저장 버튼 상태와 칼로리/거리 목표 달성 걸음수를 업데이트하는 useEffect
  /////////////////////////////////////////////
  useEffect(() => {
    steps > dbSteps ? setCanSaveDb(true) : setCanSaveDb(false);
    calculateDistanceAndCalories(steps);
    const stepGoals = stepGoalsData?.result.stepGoalsCount || 0;
    if (stepGoals > 0) {
      setProgress((steps / stepGoals) * 100);
    } else {
      setProgress(0);
    }
  }, [steps, stepsData, stepGoalsData]);

  /////////////////////////////////////////////
  // React Native WebView와 통신하기 위한 장치 (handleMessage여기서 메시지를 따오는 조건들이 있다.)
  /////////////////////////////////////////////
  useEffect(() => {
    const listener: EventListener = (event) =>
      handleMessage(event as MessageEvent);
    document.addEventListener('message', listener);
    return () => {
      document.removeEventListener('message', listener);
    };
  }, []);

  /////////////////////////////////////////////
  // 앱 실행 시 걸음수를 업데이트 하기 위해 0.2초마다 메시지를 보내는데 업데이트 했다고 응답하는 useEffect
  /////////////////////////////////////////////
  useEffect(() => {
    if (messageCount >= 20) {
      // 메시지를 20번 수신하면 전송 중지 요청
      const stopMessage = JSON.stringify({ action: 'stopSending' });
      if (window.ReactNativeWebView && window.ReactNativeWebView.postMessage) {
        window.ReactNativeWebView.postMessage(stopMessage);
        setMessageCount(0);
        console.log('Sent stop message:', stopMessage);
      }
    }
  }, [messageCount]);

  return (
    <Container maxWidth='lg' sx={{ mt: 4, mb: 4 }}>
      <MainAppBar onLogout={handleLogoutModalOpen} />
      <Box
        display='flex'
        alignItems='center'
        justifyContent='center'
        sx={{
          mt: 4,
          mb: 4,
          p: 2,
          backgroundColor: '#fff',
          borderRadius: 2,
          boxShadow: 1,
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
                      steps -
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
      {profile?.result.isFirstTimeSetupDone === 'Y' ? null : (
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
            {' '}
            <InfoIcon
              sx={{
                color: '#fff',
                mr: 1,
              }}
            />
            추가 정보를 입력해 주세요!
          </Button>
        </Box>
      )}
      <Grid container justifyContent='center'>
        <Grid item xs={12} md={8}>
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
                <LocalFireDepartmentIcon
                  sx={{ fontSize: 50, color: '#F44336' }}
                />
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
                    sx={{ color: '#B0BEC5' }} // 회색 배경
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
                          Math.round(progress) >= 100
                            ? 100
                            : Math.round(progress)
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
              {canSaveDb
                ? `${steps - dbSteps} 포인트 적립`
                : '열심히 걸어주세요.'}
            </Button>
          </CardActions>
        </Grid>
      </Grid>

      <LogoutModal
        open={openLogoutModal}
        handleClose={handleLogoutModalClose}
        handleLogout={handleLogoutStart}
      />

      <StepGoalsModal
        open={openStepGoalsModal}
        stepGoals={stepGoals}
        handleClose={handleStepGoalsModalClose}
        handleSave={handleStepGoalsSave}
      />

      <Snackbar
        open={snackbarOpen}
        autoHideDuration={2500}
        onClose={handleSnackbarClose}>
        <Alert
          onClose={handleSnackbarClose}
          severity={snackbarSeverity}
          sx={{ width: '100%' }}>
          {snackbarMessage}
        </Alert>
      </Snackbar>
    </Container>
  );
};

export default memo(DashboardPageV1);
