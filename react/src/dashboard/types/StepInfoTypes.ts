export interface StepInfoResponse {
  result: {
    stepId: number;
    stepTotalCount: number;
    stepTodayCount: number;
    regDt: Date;
    modDt: Date;
    userId: number;
  };
  resultCd: string;
  resultMsg: string;
}

export interface StepGoalsResponse {
  result: {
    stepGoalsId: number;
    stepGoalsCount: number;
    regDt: Date;
    modDt: Date;
    userId: number;
  };
  resultCd: string;
  resultMsg: string;
}

export interface SaveStepGoalsResponse {
  result: boolean;
  resultCd: string;
  resultMsg: string;
}
