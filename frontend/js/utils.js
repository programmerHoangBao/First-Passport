export function $(sel, root=document){ return root.querySelector(sel); }
export function $all(sel, root=document){ return Array.from(root.querySelectorAll(sel)); }

export function showMessage(container, type, text){
	container.innerHTML = `<div class="${type}">${escapeHtml(text)}</div>`;
}

export function escapeHtml(str=''){
	return str.replace(/[&<>"]|'/g, (c)=>({
		'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;','\'':'&#39;'
	}[c]));
}

export function formatDate(dateStr){
	if(!dateStr) return '';
	try { return new Date(dateStr).toLocaleString(); } catch { return dateStr; }
}


