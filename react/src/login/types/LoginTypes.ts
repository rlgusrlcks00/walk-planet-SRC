export interface LoginState {
  isLoggedIn: boolean;
  token: string | null;
  loading: boolean;
  error: string | null;
}

export interface LoginResponse {
  result: {
    token: string;
    userEmail: string;
    userId: number;
    userName: string;
    stepCount: number;
    isFirstTimeSetupDone: string;
  };
  resultCd: string;
  resultMsg: string;
}
