import React, { useState, memo, useEffect } from 'react';
import useLogout from '@hooks/useLogout';
import useSnackBar from '@hooks/useSnackBar';
import { useQueryClient } from 'react-query';
import MainAppBar from '@components/MainAppBar';
import {
  Alert,
  Box,
  Button,
  Container,
  Snackbar,
  Typography,
  Grid,
} from '@mui/material';
import SettingsIcon from '@mui/icons-material/Settings';
import LogoutModal from '@components/LogoutModal';
import DeleteAccountModal from '@setting/page/DeleteAccountModal';
import { deleteAccount } from '@setting/api/SettingApi';
import { DeleteAccountType } from '@setting/type/SettingType';
import TermListDialog from '@setting/page/TermListDialog';
import VersionModal from '@setting/page/VersionModal';

const SettingPage = () => {
  const [openDeleteModal, setOpenDeleteModal] = useState(false);
  const [openTermDialog, setOpenTermDialog] = useState(false);
  const [openVersionModal, setOpenVersionModal] = useState(false);
  const [version, setVersion] = useState(0);
  const [buildNumber, setBuildNumber] = useState(0);
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

  const handleDeleteModalOpen = () => setOpenDeleteModal(true);
  const handleDeleteModalClose = () => setOpenDeleteModal(false);
  const handleTermDialogOpen = () => setOpenTermDialog(true);
  const handleTermDialogClose = () => setOpenTermDialog(false);
  const handleVersionModalOpen = () => setOpenVersionModal(true);
  const handleVersionModalClose = () => setOpenVersionModal(false);

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
  // React Native에서 보낸 메시지를 필터링해서 처리하는 함수 (리시버 같은 느낌)
  /////////////////////////////////////////////
  const handleMessage = (event: MessageEvent) => {
    try {
      const data = JSON.parse(event.data);
      console.log('Received message:', data);
      if (data.version !== undefined) {
        setVersion(data.version);
        console.log(version);
      }
      if (data.buildNumber !== undefined) {
        setBuildNumber(data.buildNumber);
        console.log(buildNumber);
      }
    } catch (error) {
      console.error('Failed to parse message data:', error);
    }
  };

  const handleDeleteAccount = async () => {
    await deleteAccount().then((res: DeleteAccountType) => {
      if (res.result === 'success') {
        showSnackbar('탈퇴되었습니다', 'success');
        handleLogoutStart();
      } else {
        showSnackbar('탈퇴에 실패했습니다', 'error');
      }
    });
  };

  useEffect(() => {
    sendDataToApp({ action: 'giveMeVersion' });
  }, []);

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

  return (
    <Container maxWidth='lg' sx={{ mt: 4, mb: 4 }}>
      <MainAppBar onLogout={handleLogoutModalOpen} />
      <Box
        display='flex'
        flexDirection='column'
        alignItems='center'
        justifyContent='center'
        sx={{
          mt: 4,
          mb: 4,
          p: 2,
          backgroundColor: '#fff',
          borderRadius: 2,
          boxShadow: 3,
        }}>
        <Box display='flex' alignItems='center' justifyContent='center'>
          <Typography variant='h6'>설정</Typography>
          <SettingsIcon />
        </Box>

        <Grid container spacing={2} sx={{ mt: 4 }}>
          <Grid item xs={12}>
            <Button
              fullWidth
              variant='outlined'
              onClick={handleVersionModalOpen}>
              버전 정보
            </Button>
          </Grid>
          <Grid item xs={12}>
            <Button fullWidth variant='outlined' onClick={handleTermDialogOpen}>
              이용 약관
            </Button>
          </Grid>
          <Grid item xs={12}>
            <Button
              fullWidth
              variant='outlined'
              onClick={handleDeleteModalOpen}>
              회원 탈퇴
            </Button>
          </Grid>
        </Grid>
      </Box>
      <LogoutModal
        open={openLogoutModal}
        handleClose={handleLogoutModalClose}
        handleLogout={handleLogoutStart}
      />
      <VersionModal
        open={openVersionModal}
        onClose={handleVersionModalClose}
        version={version}
        buildNumber={buildNumber}
      />
      <TermListDialog open={openTermDialog} onClose={handleTermDialogClose} />
      <DeleteAccountModal
        open={openDeleteModal}
        onClose={handleDeleteModalClose}
        onConfirm={handleDeleteAccount}
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

export default memo(SettingPage);
