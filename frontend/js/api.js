import { API_BASE_URL } from './config.js';

function getHeaders(path){
	const headers = { 'Content-Type': 'application/json' };
	// Không đính kèm Authorization cho các endpoint public (login/register)
	const isPublic = /^\s*\/api\/(login|register)(\b|\/|\?)/i.test(path);
	if (!isPublic) {
		const token = localStorage.getItem('accessToken');
		if (token) headers['Authorization'] = `Bearer ${token}`;
	}
	return headers;
}

export async function apiGet(path){
	const res = await fetch(`${API_BASE_URL}${path}`, { headers: getHeaders(path) });
	if (!res.ok) throw new Error(await res.text() || res.statusText);
	return res.json();
}

export async function apiPost(path, body){
	const res = await fetch(`${API_BASE_URL}${path}`, {
		method: 'POST',
		headers: getHeaders(path),
		body: JSON.stringify(body)
	});
	if (!res.ok) throw new Error(await res.text() || res.statusText);
	return res.json().catch(() => ({}));
}

export async function apiPut(path, body){
	const res = await fetch(`${API_BASE_URL}${path}`, {
		method: 'PUT',
		headers: getHeaders(path),
		body: JSON.stringify(body ?? {})
	});
	if (!res.ok) throw new Error(await res.text() || res.statusText);
	return res.json().catch(() => ({}));
}


