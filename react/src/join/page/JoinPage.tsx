import React, { memo, useEffect, useState } from 'react';
import {
  Box,
  Button,
  Container,
  TextField,
  Typography,
  CssBaseline,
  Avatar,
  Dialog,
  DialogTitle,
  DialogActions,
  DialogContent,
  Snackbar,
  Alert,
} from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { doJoin } from '@join/api/JoinApi';
import useSnackBar from '@hooks/useSnackBar';
import TermsDialog from './TermDialog'; // 약관 모달 import

export interface JoinDialogProps {
  open: boolean;
  onClose: () => void;
}

interface ValidationResult {
  valid: boolean;
  message: string;
}

const JoinPage: React.FC<JoinDialogProps> = ({ open, onClose }) => {
  const [userEmail, setUserEmail] = useState('');
  const [userName, setUserName] = useState('');
  const [userPwd, setUserPwd] = useState('');
  const [isUserEmailError, setIsUserEmailError] =
    useState<ValidationResult | null>(null);
  const [isUserNameError, setIsUserNameError] = useState(false);
  const [isUserPwdError, setIsUserPwdError] = useState<ValidationResult | null>(
    null,
  );
  const [termsOpen, setTermsOpen] = useState(false);
  const [termsAgreed, setTermsAgreed] = useState(false);

  const {
    snackbarOpen,
    snackbarMessage,
    snackbarSeverity,
    showSnackbar,
    handleSnackbarClose,
  } = useSnackBar();

  const handleJoinApi = async () => {
    try {
      const response = await doJoin(userEmail, userName, userPwd);
      if (response.result === 'success') {
        handleClose();
        showSnackbar('회원가입이 완료되었습니다.', 'success');
      } else if (response.result === 'Email already exists') {
        showSnackbar('이미 존재하는 이메일입니다. 다시 시도해주세요.', 'error');
      }
      console.log(response);
    } catch (error) {
      showSnackbar('회원가입에 실패했습니다. 다시 시도해주세요.', 'error');
      console.error(error);
    }
  };

  const handleJoin = () => {
    if (
      isUserEmailError?.valid === true &&
      isUserPwdError?.valid === true &&
      userName !== '' &&
      termsAgreed
    ) {
      handleJoinApi();
    } else {
      showSnackbar('입력하신 정보를 다시 확인해주세요.', 'error');
    }
  };

  const validateEmail = (email: string): ValidationResult => {
    const emailCheck = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailCheck.test(email)) {
      return { valid: false, message: '유효하지 않은 이메일 형식입니다.' };
    }
    return { valid: true, message: '유효한 형식 이메일입니다.' };
  };

  const validatePassword = (password: string): ValidationResult => {
    const lengthCheck = /^.{8,20}$/;
    const invalidCharCheck = /[\\'"\s]/;
    if (!lengthCheck.test(password)) {
      return {
        valid: false,
        message: '비밀번호는 최소 8자 이상, 최대 20자 이하로 설정해야 합니다.',
      };
    }
    if (invalidCharCheck.test(password)) {
      return {
        valid: false,
        message: '비밀번호에 사용할 수 없는 문자가 포함되어 있습니다.',
      };
    }
    return { valid: true, message: '유효한 비밀번호입니다.' };
  };

  const handleClose = () => {
    setUserEmail('');
    setUserName('');
    setUserPwd('');
    setIsUserEmailError(null);
    setIsUserPwdError(null);
    setTermsAgreed(false);
    onClose();
  };

  useEffect(() => {
    if (userPwd) {
      setIsUserPwdError(validatePassword(userPwd));
    }
  }, [userPwd]);

  useEffect(() => {
    if (userEmail) {
      setIsUserEmailError(validateEmail(userEmail));
    }
  }, [userEmail]);

  const handleTermsOpen = () => {
    setTermsOpen(true);
  };

  const handleTermsClose = (agreed: boolean) => {
    setTermsAgreed(agreed);
    setTermsOpen(false);
  };

  return (
    <>
      <Dialog open={open} onClose={handleClose}>
        <DialogContent>
          <Box
            sx={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
            }}>
            <Avatar>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component='h1' variant='h5'>
              회원가입
            </Typography>
            <Box component='form' noValidate sx={{ mt: 1, width: '100%' }}>
              <Box sx={{ mt: 1 }}>
                <TextField
                  fullWidth
                  autoComplete='none'
                  autoFocus
                  label='이메일 (example@example.com 형식)'
                  type='email'
                  onChange={(e) => setUserEmail(e.target.value)}
                />
                {isUserEmailError?.valid === false && (
                  <Typography sx={{ color: '#F44336' }}>
                    {isUserEmailError.message}
                  </Typography>
                )}
                {isUserEmailError?.valid === true && (
                  <Typography sx={{ color: '#4CAF50' }}>
                    {isUserEmailError.message}
                  </Typography>
                )}
              </Box>
              <Box sx={{ mt: 1 }}>
                <TextField
                  fullWidth
                  autoComplete='none'
                  label='이름'
                  onChange={(e) => setUserName(e.target.value)}
                />
              </Box>
              <Box sx={{ mt: 1 }}>
                <TextField
                  fullWidth
                  autoComplete='none'
                  label='비밀번호 (8자리 이상 20자리 이하)'
                  type='password'
                  onChange={(e) => setUserPwd(e.target.value)}
                />
                {isUserPwdError?.valid === false && (
                  <Typography sx={{ color: '#F44336' }}>
                    {isUserPwdError.message}
                  </Typography>
                )}
                {isUserPwdError?.valid === true && (
                  <Typography sx={{ color: '#4CAF50' }}>
                    {isUserPwdError.message}
                  </Typography>
                )}
              </Box>
              <Box sx={{ mt: 1 }}>
                <Button onClick={handleTermsOpen}>이용 약관 보기</Button>
                {termsAgreed && (
                  <Typography sx={{ color: '#4CAF50' }}>
                    약관에 동의하셨습니다.
                  </Typography>
                )}
              </Box>
            </Box>
          </Box>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>취소</Button>
          <Button
            onClick={handleJoin}
            disabled={
              !(
                isUserPwdError?.valid &&
                isUserEmailError?.valid &&
                userName !== '' &&
                termsAgreed
              )
            }>
            가입하기
          </Button>
        </DialogActions>
      </Dialog>
      <TermsDialog open={termsOpen} onClose={handleTermsClose} />
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
    </>
  );
};

export default memo(JoinPage);
