import { axiosGet } from '@axiosConfig/axiosInstance';
import { StatisticsType } from '@statistics/types/StatisticsType';

export const getStatistics = async (
  startDate: string,
  endDate: string,
): Promise<StatisticsType[]> => {
  const url = '/statistics/get';
  const response = await axiosGet(url, { startDate, endDate });
  return response.result;
};
