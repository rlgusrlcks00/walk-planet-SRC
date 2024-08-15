import CryptoJS from 'crypto-js';

const SECRET_KEY = 'your-secret-key'; // 여기에 실제 비밀 키를 사용하세요. 보안을 위해 환경 변수로 관리하는 것이 좋습니다.

/**
 * 데이터를 암호화합니다.
 * @param {string} data - 암호화할 데이터
 * @returns {string} 암호화된 데이터
 */
export const encryptData = (data: string): string => {
  return CryptoJS.AES.encrypt(data, SECRET_KEY).toString();
};

/**
 * 데이터를 복호화합니다.
 * @param {string} encryptedData - 복호화할 암호화된 데이터
 * @returns {string} 복호화된 데이터
 */
export const decryptData = (encryptedData: string): string => {
  const bytes = CryptoJS.AES.decrypt(encryptedData, SECRET_KEY);
  return bytes.toString(CryptoJS.enc.Utf8);
};
