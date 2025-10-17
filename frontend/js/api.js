import { API_BASE_URL } from './config.js';

function getHeaders(){
	const headers = { 'Content-Type': 'application/json' };
	const token = localStorage.getItem('accessToken');
	if (token) headers['Authorization'] = `Bearer ${token}`;
	return headers;
}

export async function apiGet(path){
	const res = await fetch(`${API_BASE_URL}${path}`, { headers: getHeaders() });
	if (!res.ok) throw new Error(await res.text() || res.statusText);
	return res.json();
}

export async function apiPost(path, body){
	const res = await fetch(`${API_BASE_URL}${path}`, {
		method: 'POST',
		headers: getHeaders(),
		body: JSON.stringify(body)
	});
	if (!res.ok) throw new Error(await res.text() || res.statusText);
	return res.json().catch(() => ({}));
}

export async function apiPut(path, body){
	const res = await fetch(`${API_BASE_URL}${path}`, {
		method: 'PUT',
		headers: getHeaders(),
		body: JSON.stringify(body ?? {})
	});
	if (!res.ok) throw new Error(await res.text() || res.statusText);
	return res.json().catch(() => ({}));
}


