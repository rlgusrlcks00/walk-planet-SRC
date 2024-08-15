import React, {useEffect} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ActivityIndicator,
  StatusBar,
  Platform,
} from 'react-native';
import changeNavigationBarColor from 'react-native-navigation-bar-color';

const LoadingScreen = () => {
  useEffect(() => {
    const hideStatusBar = () => {
      StatusBar.setHidden(true);
      if (Platform.OS === 'android') {
        changeNavigationBarColor('black', true, true);
      }
    };

    hideStatusBar();

    return () => {
      StatusBar.setHidden(false);
      if (Platform.OS === 'android') {
        changeNavigationBarColor('white', true, false); // 원래 색상으로 복원
      }
    };
  }, []);

  return (
    <View style={styles.container}>
      <ActivityIndicator size="large" color="#0288D1" />
      <Text style={styles.loadingText}>
        로딩 중... 건강한 하루를 시작해 볼까요?
      </Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5F5F5', // 불투명 배경
  },
  loadingText: {
    marginTop: 10,
    fontSize: 18,
    color: '#757575',
  },
});

export default LoadingScreen;
