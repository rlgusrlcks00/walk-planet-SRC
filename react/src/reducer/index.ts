import { combineReducers } from '@reduxjs/toolkit';
import login from '../login/redux/LoginRedux';

const rootReducer = combineReducers({
  login,
  // 다른 리듀서를 추가하세요
});

export type RootState = ReturnType<typeof rootReducer>;
export default rootReducer;
