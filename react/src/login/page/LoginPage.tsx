import React, { memo, useEffect, useState, useCallback } from 'react';
import {
  Box,
  Button,
  Container,
  TextField,
  Typography,
  CssBaseline,
  Avatar,
  Snackbar,
  Alert,
  Tooltip,
  IconButton,
} from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import HelpOutlineIcon from '@mui/icons-material/HelpOutline';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import { doLogin } from '@login/api/LoginApi';
import JoinPage from '@join/page/JoinPage';
import { useNavigate } from 'react-router';
import { encryptData } from '@utils/crypto';
import useSnackBar from '@hooks/useSnackBar';

const LoginPage: React.FC = () => {
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');
  const [isJoin, setIsJoin] = useState(false);
  const [tooltipOpen, setTooltipOpen] = useState(false);
  const navigate = useNavigate();
  const {
    snackbarOpen,
    snackbarMessage,
    snackbarSeverity,
    showSnackbar,
    handleSnackbarClose,
  } = useSnackBar();

  const handleJoinOpen = () => {
    setIsJoin(true);
  };

  const handleJoinClose = () => {
    setIsJoin(false);
  };

  const handleLogin = async () => {
    try {
      const response = await doLogin(userId, password).then((res) => {
        localStorage.setItem('token', encryptData(res.result.token));
        localStorage.setItem('name', encryptData(res.result.userName));
        localStorage.setItem('email', encryptData(res.result.userEmail));
        localStorage.setItem('isSetup', res.result.isFirstTimeSetupDone);

        handleSendTodayStepsCount(res.result.stepCount);

        if (password === '') {
          navigate('/dashboard');
        }
      });
      console.log(response);
    } catch (error) {
      showSnackbar('정보가 일치하지 않습니다. 다시 시도해주세요.', 'error');
    }
  };

  const handleSendTodayStepsCount = (todayStepsCount: number) => {
    const message = JSON.stringify({ todaySteps: todayStepsCount });
    if (window.ReactNativeWebView && window.ReactNativeWebView.postMessage) {
      window.ReactNativeWebView.postMessage(message);
      console.log('Sent today steps count message:', message);
    }
  };

  useEffect(() => {
    const stopMessage = JSON.stringify({ action: 'stopSending' });
    if (window.ReactNativeWebView && window.ReactNativeWebView.postMessage) {
      window.ReactNativeWebView.postMessage(stopMessage);
      console.log('Sent stop message:', stopMessage);
    }
  }, []);

  useEffect(() => {
    const handleMessage = (event: MessageEvent) => {
      try {
        const data = JSON.parse(event.data);
        console.log('Received message:', data);
        if (data.action === 'login') {
          navigate('/dashboard');
        }
      } catch (error) {
        console.error('Failed to parse message data:', error);
      }
    };

    const listener: EventListener = (event) =>
      handleMessage(event as MessageEvent);

    document.addEventListener('message', listener);

    return () => {
      document.removeEventListener('message', listener);
    };
  }, [navigate]);

  const handleCopy = useCallback(() => {
    showSnackbar('이메일 주소가 복사되었습니다.', 'success');
  }, [showSnackbar]);

  return (
    <Container component='main' maxWidth='xs'>
      <CssBaseline />
      <Box
        sx={{
          marginTop: 8,
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}>
        <Avatar sx={{ m: 1 }}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component='h1' variant='h5'>
          WALK MATE
        </Typography>
        <Box component='form' noValidate sx={{ mt: 1, width: '100%' }}>
          <TextField
            margin='normal'
            fullWidth
            autoComplete='off'
            label='이메일'
            type='email'
            onChange={(e) => setUserId(e.target.value)}
            sx={{ mb: 2 }}
          />
          <TextField
            margin='normal'
            fullWidth
            autoComplete='off'
            label='비밀번호'
            type='password'
            onChange={(e) => setPassword(e.target.value)}
          />
          <Button
            fullWidth
            variant='contained'
            sx={{ mt: 3, mb: 2 }}
            onClick={() => {
              handleLogin();
              console.log('Login');
            }}>
            로그인
          </Button>
          <Button
            fullWidth
            variant='contained'
            sx={{ mt: 3, mb: 2 }}
            onClick={() => handleJoinOpen()}>
            회원가입
          </Button>
        </Box>
        <Box
          sx={{
            mt: 1,
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
          }}>
          <Tooltip
            title={
              <>
                이메일로 비밀번호 재설정을 요청하세요.
                <br />
                <br />
                <CopyToClipboard
                  text='rlgusrlcks00@gmail.com'
                  onCopy={handleCopy}>
                  <Button size='small' variant='outlined' color='primary'>
                    이메일 복사
                  </Button>
                </CopyToClipboard>
              </>
            }
            open={tooltipOpen}
            onClose={() => setTooltipOpen(false)}
            onOpen={() => setTooltipOpen(true)}>
            <IconButton size='small' onClick={() => setTooltipOpen(true)}>
              <Typography variant='body2'>계정 정보를 까먹었어요</Typography>
              <HelpOutlineIcon fontSize='small' />
            </IconButton>
          </Tooltip>
        </Box>
        <Box sx={{ mt: 4, display: 'flex', justifyContent: 'center' }}>
          <img
            src={`${process.env.PUBLIC_URL}/WALKMATE_1024.png`}
            alt='WalkMate Logo'
            style={{ maxWidth: '100%', borderRadius: '8px' }}
          />
        </Box>
        <JoinPage open={isJoin} onClose={handleJoinClose} />
      </Box>
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

export default memo(LoginPage);
