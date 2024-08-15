export interface UserProfileResponse {
  result: {
    userId: number;
    username: string;
    userRealName: string;
    userEmail: string;
    regDt: Date;
    modDt: Date;
    birth: string;
    gender: string;
    profileImg: string;
    nickname: string;
    height: number;
    weight: number;
    weightGoals: number;
    isFirstTimeSetupDone: string;
  };
  resultCd: string;
  resultMsg: string;
}

export interface UserProfileRequest {
  birth: string;
  gender: string;
  nickname: string;
  height: number;
  weight: number;
  weightGoals: number;
  profileImg: File | null;
}
