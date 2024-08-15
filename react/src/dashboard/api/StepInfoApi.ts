import { axiosGet, axiosPost } from '@axiosConfig/axiosInstance';
import {
  SaveStepGoalsResponse,
  StepGoalsResponse,
  StepInfoResponse,
} from '@dashboard/types/StepInfoTypes';

export const getStepInfo = async (): Promise<StepInfoResponse> => {
  const url = '/step/info';
  return await axiosGet(url);
};

export const setStepInfo = async (stepCount: number) => {
  const url = '/step/save';
  return await axiosPost(url, { stepCount });
};

export const getStepGoals = async (): Promise<StepGoalsResponse> => {
  const url = '/step/goals';
  return await axiosGet(url);
};

export const setStepGoalsCount = async (
  stepGoalsCount: number,
): Promise<SaveStepGoalsResponse> => {
  const url = '/step/savegoals';
  return await axiosPost(url, { stepGoalsCount });
};
