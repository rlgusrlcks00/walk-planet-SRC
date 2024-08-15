import {NativeModules} from 'react-native';

const {MyModule} = NativeModules;

const sendResetStepBroadcast = () => {
  MyModule.sendResetStepBroadcast();
};

const sendTodayStepsBroadcast = (todaySteps: number) => {
  MyModule.sendTodayStepsBroadcast(todaySteps);
};

export default {
  sendResetStepBroadcast,
  sendTodayStepsBroadcast,
};
