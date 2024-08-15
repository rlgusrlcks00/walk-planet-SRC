import { DeleteAccountType, VersionType } from '../type/SettingType';
import { axiosGet, axiosPost } from '@axiosConfig/axiosInstance';

export const deleteAccount = async (): Promise<DeleteAccountType> => {
  const url = '/delete/account';
  return await axiosPost(url);
};

export const getVersion = async (): Promise<VersionType> => {
  const url = '/open/version/get';
  return await axiosGet(url);
};
