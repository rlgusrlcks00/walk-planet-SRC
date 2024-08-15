import { LoginResponse } from '@login/types/LoginTypes';
import { axiosGet } from '@axiosConfig/axiosInstance';

export const doLogin = async (
  userEmail: string,
  userPwd: string,
): Promise<LoginResponse> => {
  const url = '/open/login/user';
  return await axiosGet(url, { userEmail, userPwd });
};
