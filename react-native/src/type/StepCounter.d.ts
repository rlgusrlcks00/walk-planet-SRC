declare module 'react-native' {
  interface StepCounterModule {
    startStepCounter(): Promise<void>;
    stopStepCounter(): Promise<void>;
  }

  const StepCounter: StepCounterModule;
}
