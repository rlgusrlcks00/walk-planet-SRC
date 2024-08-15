import axios, { AxiosInstance } from 'axios';
import { decryptData, encryptData } from '@utils/crypto';
import Resizer from 'react-image-file-resizer';

const axiosInstance: AxiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_PATH,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

axiosInstance.interceptors.request.use(
  (config) => {
    const encryptedToken = localStorage.getItem('token');
    const token = encryptedToken ? decryptData(encryptedToken) : null;
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error),
);

axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response && error.response.status === 401) {
      // 401 에러 처리 로직 추가
      const originalRequest = error.config;
      console.log('토큰 만료:', error.response.data);
      // 토큰 갱신 로직
      try {
        console.log('토큰 갱신 시도');
        await refreshToken(); // 수정된 부분
        const encryptedToken = localStorage.getItem('token');
        const newToken = encryptedToken ? decryptData(encryptedToken) : null;
        if (newToken) {
          originalRequest.headers.Authorization = `Bearer ${newToken}`;
        }
        return axiosInstance(originalRequest);
      } catch (refreshError) {
        console.log('토큰 갱신 실패:', refreshError);
        // 필요 시 로그아웃 처리 등 추가 작업
        localStorage.removeItem('token');
        localStorage.removeItem('name');
        localStorage.removeItem('email');
        window.location.href = '/chan-web/login'; // 로그인 페이지로 리디렉션

        return Promise.reject(refreshError);
      }
    }
    return Promise.reject(error);
  },
);

// 추가된 부분: refreshToken 함수
const refreshToken = async () => {
  try {
    const response = await axios.post(
      `${process.env.REACT_APP_API_PATH}/open/token/refresh`,
      {},
      { withCredentials: true },
    );
    console.log('리프레시 토큰 갱신 성공:', response.data);
    const { accessToken } = response.data;
    const encryptedToken = encryptData(accessToken); // 적절한 암호화 함수 사용
    localStorage.setItem('token', encryptedToken);
  } catch (error) {
    console.error('리프레시 토큰 갱신 실패:', error);

    throw error;
  }
};

type ReturnType = {
  resultCd: string;
  resultMsg: string;
  result: any;
};

export const axiosGet = (url: string, params?: any): Promise<ReturnType> => {
  return axiosInstance
    .get(url, {
      params: params,
      responseType: 'json',
    })
    .then((response) => response.data)
    .catch((error) => {
      console.error(error);
      return Promise.reject(error.response ? error.response : error);
    });
};

export const axiosPost = (
  url: string,
  params?: any,
  body?: any,
): Promise<ReturnType> => {
  return axiosInstance
    .post(url, body, {
      params,
    })
    .then((response) => response.data)
    .catch((error) => {
      console.error(error);
      return Promise.reject(error.response ? error.response : error);
    });
};

export const axiosFile = (
  url: string,
  params: { [key: string]: any },
): Promise<ReturnType> => {
  const formData = new FormData();

  const resizeAndAppendImage = (file: File, key: string): Promise<void> => {
    return new Promise((resolve, reject) => {
      Resizer.imageFileResizer(
        file,
        300, // 최대 너비
        300, // 최대 높이
        'JPEG', // 형식
        70, // 품질 (0-100)
        0, // 회전 각도
        (uri) => {
          if (typeof uri === 'string') {
            const byteString = atob(uri.split(',')[1]);
            const mimeString = uri.split(',')[0].split(':')[1].split(';')[0];
            const ab = new ArrayBuffer(byteString.length);
            const ia = new Uint8Array(ab);
            for (let i = 0; i < byteString.length; i++) {
              ia[i] = byteString.charCodeAt(i);
            }
            const blob = new Blob([ab], { type: mimeString });
            const resizedFile = new File([blob], file.name, {
              type: mimeString,
            });
            formData.append(key, resizedFile);
            resolve();
          } else {
            reject(new Error('Failed to resize image'));
          }
        },
        'base64',
      );
    });
  };

  const resizePromises: Promise<void>[] = [];

  for (const key in params) {
    if (params[key] instanceof File) {
      resizePromises.push(resizeAndAppendImage(params[key], key));
    } else {
      formData.append(key, params[key]);
    }
  }

  return Promise.all(resizePromises)
    .then(() => {
      axiosInstance.defaults.headers.common['Content-Type'] =
        'multipart/form-data';
      return axiosInstance.post(url, formData);
    })
    .then((response) => response.data)
    .catch((error) => {
      console.error(error);
      return Promise.reject(error.response ? error.response : error);
    });
};
export default axiosInstance;
