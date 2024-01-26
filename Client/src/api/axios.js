import axios from "axios";
import { BASE_URL } from "./constance";

// axios 인스턴스
const axiosInstance = axios.create({
  baseURL: BASE_URL,
});

// axios 인스턴스 API
export async function axiosGet(url) {
  try {
    const response = await axiosInstance.get(url);
    return response;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

export async function axiosPost(url, data) {
  try {
    const response = await axiosInstance.post(url, data);
    return response;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

export async function axiosPut(url, data) {
  try {
    const response = await axiosInstance.put(url, data);
    return response;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

export async function axiosPatch(url, data) {
  try {
    const response = await axiosInstance.patch(url, data);
    return response;
  } catch (error) {
    console.log(error);
    throw error;
  }
}

// axios 요청 시 인터셉터
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// axios 응답 시 인터셉터
axiosInstance.interceptors.response.use(
  (response) => {
    const token = response.headers["token"];

    if (token) {
      localStorage.setItem("token", token);
    }

    return response;
  },
  (error) => {
    return Promise.reject(error);
  },
);
