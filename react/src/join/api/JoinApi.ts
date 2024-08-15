import { axiosPost } from '@axiosConfig/axiosInstance';
import { joinState } from '@join/types/JoinTypes';

export const doJoin = async (
  userEmail: string,
  userName: string,
  userPwd: string,
): Promise<joinState> => {
  const url = '/open/join/user';
  return await axiosPost(url, { userEmail, userName, userPwd });
};
