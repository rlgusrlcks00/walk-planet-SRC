import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router';
import { setStepInfo } from '@dashboard/api/StepInfoApi';

const useLogout = () => {
  const [openLogoutModal, setOpenLogoutModal] = useState(false);
  const navigate = useNavigate();

  const sendDataToApp = (data: any) => {
    if (window.ReactNativeWebView && window.ReactNativeWebView.postMessage) {
      window.ReactNativeWebView.postMessage(JSON.stringify(data));
    } else {
      console.warn('ReactNativeWebView is not defined');
    }
  };

  /////////////////////////////////////////////
  // 로그아웃 버튼을 눌렀을 때 React Native에 메시지를 전송하는 함수
  /////////////////////////////////////////////
  const handleLogoutStart = () => {
    sendDataToApp({ action: 'logout' });
  };

  /////////////////////////////////////////////
  // 로그아웃 모달창을 여는 함수
  /////////////////////////////////////////////
  const handleLogoutModalOpen = () => {
    setOpenLogoutModal(true);
  };

  /////////////////////////////////////////////
  // 로그아웃 모달창을 닫는 함수
  /////////////////////////////////////////////
  const handleLogoutModalClose = () => {
    setOpenLogoutModal(false);
  };

  const saveStepsApi = async (step: number) => {
    try {
      await setStepInfo(step);
    } catch (error) {
      console.error(error);
    }
  };

  /////////////////////////////////////////////
  // 최종 로그아웃 처리 함수
  /////////////////////////////////////////////
  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('name');
    localStorage.removeItem('email');
    navigate('/login');
  };

  const handleMessage = (event: MessageEvent) => {
    try {
      const data = JSON.parse(event.data);
      if (data.action === 'logoutOK') {
        if (data.logoutSteps !== undefined) {
          saveStepsApi(data.logoutSteps).then(() => {
            sendDataToApp({ action: 'logoutDone' });
            handleLogout();
          });
        }
      }
    } catch (error) {
      console.error('Failed to parse message data:', error);
    }
  };

  useEffect(() => {
    const listener: EventListener = (event) =>
      handleMessage(event as MessageEvent);
    document.addEventListener('message', listener);
    return () => {
      document.removeEventListener('message', listener);
    };
  }, []);

  return {
    openLogoutModal,
    handleLogoutModalOpen,
    handleLogoutModalClose,
    handleLogoutStart,
  };
};

export default useLogout;
