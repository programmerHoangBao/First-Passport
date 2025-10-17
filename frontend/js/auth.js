import { apiPost } from './api.js';

export function getToken(){ return localStorage.getItem('accessToken'); }
export function getRole(){ return localStorage.getItem('role'); }
export function isLoggedIn(){ return Boolean(getToken()); }

export function saveAuth(resp){
	// cố gắng bắt token theo các tên phổ biến
	const token = resp?.accessToken || resp?.token || resp?.jwt || null;
	const role = resp?.role || resp?.authorities?.[0]?.authority || resp?.authorities?.[0]?.name || null;
	if (token) localStorage.setItem('accessToken', token);
	if (role) localStorage.setItem('role', normalizeRole(role));
}

export function normalizeRole(role){
	if (!role) return null;
	return String(role).replace(/^ROLE_/,'').toUpperCase();
}

export function logout(){
	localStorage.removeItem('accessToken');
	localStorage.removeItem('role');
}

export async function login(username, password){
	const res = await apiPost('/api/login', { username, password });
	saveAuth(res);
	return getRole();
}

export function requireRole(roles){
	const token = getToken();
	const role = getRole();
	const allowed = Array.isArray(roles) ? roles.map(normalizeRole) : [normalizeRole(roles)];
	if (!token || !role || !allowed.includes(normalizeRole(role))) {
		window.location.href = '../pages/login.html';
		throw new Error('Unauthorized');
	}
}


