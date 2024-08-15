import { Box, Paper, Snackbar, Alert, Container, Button } from '@mui/material';
import React, { memo, useEffect, useState } from 'react';
import MainAppBar from '@components/MainAppBar';
import '@walkmate/css/WalkMate.css';
import useLogout from '@hooks/useLogout';
import useSnackBar from '@hooks/useSnackBar';
import LogoutModal from '@components/LogoutModal';
import { UserProfileResponse } from '@profile/types/UserProfileTypes';
import { userProfileInitial } from '@profile/types/UserProfileInitial';
import { getUserProfileInfo } from '@profile/api/UserProfileApi';
import { getStepGoals, getStepInfo } from '@dashboard/api/StepInfoApi';
import { getPointInfo } from '@dashboard/api/PointInfoApi';
import WalkMateModal from '@walkmate/page/WalkMateModal';
import { useQuery } from 'react-query';

const WalkMatePage: React.FC = () => {
  const [steps, setSteps] = useState(0);
  const [distance, setDistance] = useState(0);
  const [calories, setCalories] = useState(0);
  const [messageCount, setMessageCount] = useState(0);
  const [point, setPoint] = useState(0);
  const [rotation, setRotation] = useState(0);
  const [openWalkMateModal, setOpenWalkMateModal] = useState(false);
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

  /////////////////////////////////////////////
  // 사용자 정보를 가져오고 저장하는 함수들
  /////////////////////////////////////////////
  const { data: profile, isLoading: profileLoading } = useQuery(
    'userProfile',
    getUserProfileInfo,
  );

  /////////////////////////////////////////////
  // 포인트 정보를 가져오고 저장하는 함수들
  /////////////////////////////////////////////
  const fetchPoint = async () => {
    try {
      const response = await getPointInfo();
      setPoint(response.result.totalPoint);
    } catch (error) {
      console.error(error);
    }
  };

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

  /////////////////////////////////////////////
  // React Natvie에 메시지를 전송하는 함수 ex) sendDataToApp({ action: "resetSteps" });
  /////////////////////////////////////////////
  const sendDataToApp = (data: any) => {
    if (window.ReactNativeWebView && window.ReactNativeWebView.postMessage) {
      window.ReactNativeWebView.postMessage(JSON.stringify(data));
    } else {
      console.warn('ReactNativeWebView is not defined');
    }
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

  const circulateRotation = () => {
    const stepGoals = stepGoalsData?.result.stepGoalsCount || 1;
    setRotation((steps / stepGoals) * 360);
  };

  const handleWalkMateModalOpen = () => {
    setOpenWalkMateModal(true);
  };

  const handleWalkMateModalClose = () => {
    setOpenWalkMateModal(false);
  };

  /////////////////////////////////////////////
  // 최초의 걸음수를 불러오는 useEffect
  /////////////////////////////////////////////
  useEffect(() => {
    sendDataToApp({ action: 'giveMeSteps' });
    fetchPoint();
  }, []);

  /////////////////////////////////////////////
  // 걸음수가 변경 될 때 마다 저장 버튼 상태와 칼로리/거리 목표 달성 걸음수를 업데이트하는 useEffect
  /////////////////////////////////////////////
  useEffect(() => {
    calculateDistanceAndCalories(steps);
    circulateRotation();
  }, [steps]);

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

      <Box sx={{ mt: 4, mb: 4 }}>
        <Paper
          sx={{
            //backgroundImage: `url(${process.env.PUBLIC_URL}/Walking.png)`,
            //backgroundSize: "cover",
            //backgroundPosition: "center",
            p: 1,
            borderRadius: 2,
            boxShadow: 3,
          }}>
          <Box sx={{ mb: 10 }}>
            <img
              src={`${process.env.PUBLIC_URL}/sun.png`}
              alt='Sun'
              style={{ width: '120px', height: '120px' }} // 이미지 크기 조절
            />
          </Box>
          <Box
            sx={{
              display: 'flex',
              alignItems: 'center',
              flexDirection: 'column',
              position: 'relative',
            }}>
            <img
              src={`${process.env.PUBLIC_URL}/earth.png`}
              alt='Rotating Earth'
              className='earth'
              style={{ transform: `rotate(${rotation}deg)` }}
            />
            <Box
              sx={{
                position: 'absolute',
                top: '5%', // Adjust as necessary
                left: '50%', // Adjust as necessary
                transform: 'translate(-50%, -95%)', // Center the text box
                textAlign: 'center',
                //display: "flex",
              }}>
              <Button
                sx={{
                  width: 120,
                  height: 80,
                  backgroundColor: '#757575',
                  color: '#fff',
                  borderRadius: '20px',
                  position: 'relative',
                  fontSize: 10,
                  textAlign: 'left', // 왼쪽 정렬
                  '&:before': {
                    content: '""',
                    position: 'absolute',
                    width: 0,
                    height: 0,
                    borderTop: '15px solid #757575',
                    borderLeft: '15px solid transparent',
                    borderRight: '15px solid transparent',
                    bottom: '-15px',
                    left: '50%',
                    transform: 'translateX(-50%)',
                  },
                  '&:hover': {
                    backgroundColor: '#757575', // hover 시 색상 변경 방지
                  },
                  '&:active': {
                    backgroundColor: '#757575', // active 시 색상 변경 방지
                  },
                  '&:focus': {
                    backgroundColor: '#757575', // focus 시 색상 변경 방지
                  },
                }}
                onClick={handleWalkMateModalOpen}>
                <div style={{ textAlign: 'left', width: '100%' }}>
                  오늘의 걸음 : {steps}
                  <br />
                  칼로리 : {calories.toFixed(2)}
                  <br />
                  거리 : {distance.toFixed(2)}
                  <br />
                  <span style={{ fontSize: '8px', color: '#ccc' }}>
                    더보기...
                  </span>
                </div>
              </Button>
              <Box sx={{ display: 'flex', mt: 2 }}>
                <Box>
                  <img
                    src={`${process.env.PUBLIC_URL}/defaultPet.png`}
                    alt='Sun'
                    style={{ width: '70px', height: '125px' }} // 이미지 크기 조절
                  />
                </Box>
                <Box>
                  <img
                    src={`${process.env.PUBLIC_URL}/defaultMate.png`}
                    alt='Sun'
                    style={{ width: '90px', height: '125px' }} // 이미지 크기 조절
                  />
                </Box>
              </Box>
            </Box>
          </Box>
          {/*<Box className="progress-container">*/}
          {/*  <Grid container spacing={2} alignItems="center">*/}
          {/*    <Grid item xs={6}>*/}
          {/*      <TextField*/}
          {/*        label="걸음 수"*/}
          {/*        type="number"*/}
          {/*        value={steps}*/}
          {/*        onChange={handleStepsChange}*/}
          {/*        fullWidth*/}
          {/*      />*/}
          {/*    </Grid>*/}
          {/*    <Grid item xs={6}>*/}
          {/*      <Typography variant="h6">{`목표 걸음 수: ${stepGoals}`}</Typography>*/}
          {/*    </Grid>*/}
          {/*    <Grid item xs={12}>*/}
          {/*      <Slider*/}
          {/*        value={steps}*/}
          {/*        onChange={(e, newValue) => setSteps(newValue as number)}*/}
          {/*        aria-labelledby="steps-slider"*/}
          {/*        min={0}*/}
          {/*        max={stepGoals}*/}
          {/*        step={100}*/}
          {/*        valueLabelDisplay="auto"*/}
          {/*      />*/}
          {/*    </Grid>*/}
          {/*  </Grid>*/}
          {/*</Box>*/}
          {/*<Box className="progress-indicator">*/}
          {/*  <Typography variant="body1">{`현재 걸음 수: ${steps}`}</Typography>*/}
          {/*  <Typography variant="body1">{`회전 각도: ${Math.round(*/}
          {/*    rotation*/}
          {/*  )}도`}</Typography>*/}
          {/*</Box>*/}
        </Paper>

        <LogoutModal
          open={openLogoutModal}
          handleClose={handleLogoutModalClose}
          handleLogout={handleLogoutStart}
        />

        <WalkMateModal
          handleClose={handleWalkMateModalClose}
          open={openWalkMateModal}
          steps={steps}
          stepGoals={stepGoalsData?.result.stepGoalsCount || 0}
          point={point}
          profile={profile || userProfileInitial}
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
      </Box>
    </Container>
  );
};

export default memo(WalkMatePage);
