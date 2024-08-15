import {
  UserProfileRequest,
  UserProfileResponse,
} from '@profile/types/UserProfileTypes';

export const userProfileInitial: UserProfileResponse = {
  result: {
    userId: 0,
    username: '',
    userRealName: '',
    userEmail: '',
    regDt: new Date(),
    modDt: new Date(),
    birth: '',
    gender: '',
    profileImg: '',
    nickname: '',
    height: 0,
    weight: 0,
    weightGoals: 0,
    isFirstTimeSetupDone: '',
  },
  resultCd: '',
  resultMsg: '',
};

export const userProfileUpdateInitial: UserProfileRequest = {
  birth: '',
  gender: '',
  nickname: '',
  height: 0,
  weight: 0,
  weightGoals: 0,
  profileImg: null,
};
