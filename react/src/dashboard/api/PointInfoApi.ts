import { axiosGet, axiosPost } from '@axiosConfig/axiosInstance';
import { PointInfoResponse } from '@dashboard/types/PointInfoTypes';

export const getPointInfo = async (): Promise<PointInfoResponse> => {
  const url = '/point/info';
  return await axiosGet(url);
};

export const setPointInfo = async (point: number) => {
  const url = '/point/save';
  return await axiosPost(url, { point });
};
