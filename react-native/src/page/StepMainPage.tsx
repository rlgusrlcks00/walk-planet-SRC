import React, {useEffect, useRef, useState} from 'react';
import {
  Alert,
  PermissionsAndroid,
  Platform,
  SafeAreaView,
  StyleSheet,
  NativeEventEmitter,
  NativeModules,
  Modal,
} from 'react-native';
import {WebView} from 'react-native-webview';
import LoadingScreen from './LoadingScreen';
import MyModule from '../type/MyNativeModule';
import DeviceInfo from 'react-native-device-info';

const {StepCounter} = NativeModules;
const stepCounterEmitter = new NativeEventEmitter(StepCounter);

// 센서 권한을 요청하는 함수
const requestSensorPermission = async () => {
  if (Platform.OS === 'android') {
    try {
      const granted = await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.ACTIVITY_RECOGNITION,
        {
          title: 'Sensor Permission',
          message:
            'This app needs access to your body sensors to measure steps.',
          buttonNeutral: 'Ask Me Later',
          buttonNegative: 'Cancel',
          buttonPositive: 'OK',
        },
      );
      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
        console.log('Sensor permission granted');
      } else {
        console.log('Sensor permission denied');
        Alert.alert(
          'Permission denied',
          'Cannot measure steps without sensor permission.',
        );
      }
    } catch (err) {
      console.warn(err);
    }
  }
};

/////////////////////////////////////////////
// StepMainPage 컴포넌트(여기가 메인)
/////////////////////////////////////////////
interface StepMainPageProps {
  url: string;
}

const StepMainPage: React.FC<StepMainPageProps> = ({url}) => {
  const [steps, setSteps] = useState<number>(0);
  const webviewRef = useRef<WebView>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [stopSending, setStopSending] = useState<boolean>(false);
  const version = DeviceInfo.getVersion(); // 앱 버전 정보 가져오기
  const buildNumber = DeviceInfo.getBuildNumber(); // 빌드 번호 가져오기

  // 리액트 WebView로 메시지를 전송하는 함수
  const sendMessageToWebView = (jsonMessage: any) => {
    if (webviewRef.current) {
      webviewRef.current.postMessage(JSON.stringify(jsonMessage));
      console.log('Sent message function:', JSON.stringify(jsonMessage));
    }
  };

  const handleMessage = (event: any) => {
    const data = JSON.parse(event.nativeEvent.data);
    console.log('Received message:', data);
    if (data.action === 'stopSending') {
      setStopSending(true);
      setLoading(false);
      console.log('Stopping message sending');
    } else if (data.action === 'giveMeSteps') {
      sendMessageToWebView({steps: steps});
    } else if (data.action === 'logout') {
      setLoading(true);
      sendMessageToWebView({action: 'logoutOK', logoutSteps: steps});
    } else if (data.action === 'logoutDone') {
      MyModule.sendResetStepBroadcast();
    } else if (data.todaySteps !== undefined) {
      setSteps(data.todaySteps);
      setStopSending(false);
      setLoading(true);
      MyModule.sendTodayStepsBroadcast(data.todaySteps);
      sendMessageToWebView({action: 'login'});
      console.log('Received today steps:', data.todaySteps);
    } else if (data.action === 'giveMeVersion') {
      sendMessageToWebView({
        version: version,
        buildNumber: buildNumber,
      });
      console.log('Sent version:', version, buildNumber);
    }
  };

  useEffect(() => {
    const init = async () => {
      try {
        await StepCounter.startStepCounter();
      } catch (error) {
        console.error(error);
      }
      await requestSensorPermission();
    };

    init();

    const subscription = stepCounterEmitter.addListener(
      'StepCountUpdated',
      (event: {stepCount: number}) => {
        const newStepCount = event.stepCount;
        setSteps(newStepCount);
        sendMessageToWebView({steps: newStepCount});
      },
    );

    return () => {
      subscription.remove();
      StepCounter.stopStepCounter();
    };
  }, []);

  useEffect(() => {
    let intervalId: NodeJS.Timeout;
    if (!stopSending) {
      intervalId = setInterval(() => {
        sendMessageToWebView({steps: steps});
      }, 200);
    }
    return () => {
      clearInterval(intervalId);
    };
  }, [steps, stopSending]);

  return (
    <SafeAreaView style={styles.container}>
      <WebView
        ref={webviewRef}
        source={{uri: 'https://kichani.com/chan-web/dashboard'}} // 리액트 웹 애플리케이션 URL을 prop으로 설정
        onMessage={handleMessage}
        onLoad={() => {
          const message = JSON.stringify({steps});
          webviewRef.current?.postMessage(message);
          console.log('Sent message on load:', message);
        }}
        style={styles.webview}
        onError={syntheticEvent => {
          const {nativeEvent} = syntheticEvent;
          setLoading(false);
          console.warn('WebView error: ', nativeEvent);
        }}
        startInLoadingState={true} // 로딩 인디케이터 표시
        mixedContentMode="compatibility" // HTTP 및 HTTPS 컨텐츠 혼합 허용
      />
      <Modal visible={loading} transparent>
        <LoadingScreen />
      </Modal>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  webview: {
    flex: 1,
    width: '100%',
  },
  footer: {
    padding: 10,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  stepText: {
    fontSize: 18,
  },
});

export default StepMainPage;
