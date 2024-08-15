import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { call, put, takeLatest } from 'redux-saga/effects';
import { doLogin } from '@login/api/LoginApi';
import { AxiosResponse } from 'axios';
import { LoginState } from '@login/types/LoginTypes';

// 초기 상태
const initialState: LoginState = {
  isLoggedIn: false,
  token: null,
  loading: false,
  error: null,
};

// Redux Slice 설정
const loginSlice = createSlice({
  name: 'login',
  initialState,
  reducers: {
    loginRequest: (
      state,
      action: PayloadAction<{ username: string; password: string }>,
    ) => {
      state.loading = true;
      state.error = null;
    },
    loginSuccess: (state, action: PayloadAction<string>) => {
      state.isLoggedIn = true;
      state.token = action.payload;
      state.loading = false;
      state.error = null;
    },
    loginFailure: (state, action: PayloadAction<string>) => {
      state.loading = false;
      state.error = action.payload;
    },
    logout: (state) => {
      state.isLoggedIn = false;
      state.token = null;
    },
  },
});

export const { loginRequest, loginSuccess, loginFailure, logout } =
  loginSlice.actions;
export default loginSlice.reducer;

// Redux-Saga 설정
function* handleLogin(
  action: PayloadAction<{ username: string; password: string }>,
) {
  const response: AxiosResponse<{ token: string }> = yield call(
    doLogin,
    action.payload.username,
    action.payload.password,
  );
  yield put(loginSuccess(response.data.token));
}

function* handleLoginFailure(action: ReturnType<typeof loginRequest>) {
  yield put(loginFailure('Login failed'));
}

export function* watchLogin() {
  yield takeLatest(loginRequest.type, handleLogin);
  yield takeLatest(loginFailure.type, handleLoginFailure);
}
