import { all } from 'redux-saga/effects';
import { watchLogin } from '../login/redux/LoginRedux';
// 다른 사가를 추가하세요

export function* rootSaga() {
  yield all([
    watchLogin(),
    // 다른 사가를 추가하세요
  ]);
}
