export interface DeleteAccountType {
  result: string;
  resultCd: string;
  resultMsg: string;
}

export interface VersionType {
  result: {
    version: number;
    buildNumber: number;
  };
  resultCd: string;
  resultMsg: string;
}
