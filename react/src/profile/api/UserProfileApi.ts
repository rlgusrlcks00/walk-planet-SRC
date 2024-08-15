import { axiosFile, axiosGet } from '@axiosConfig/axiosInstance';
import {
  UserProfileRequest,
  UserProfileResponse,
} from '@profile/types/UserProfileTypes';

export const getUserProfileInfo = async (): Promise<UserProfileResponse> => {
  const url = '/profile/info';
  return await axiosGet(url);
};

export const setUserProfileInfo = async (
  data: UserProfileRequest,
): Promise<UserProfileResponse> => {
  const url = '/profile/save';
  return await axiosFile(url, data);
};
