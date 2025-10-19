// Cấu hình backend
// Đổi URL dưới đây nếu backend chạy cổng/host khác
export const API_BASE_URL = localStorage.getItem('API_BASE_URL') || 'http://localhost:7070';

export function setApiBaseUrl(url){
	localStorage.setItem('API_BASE_URL', url);
}


