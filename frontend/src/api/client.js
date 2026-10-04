// Mọi request đi qua đây: tự gắn Bearer token, parse 2 dạng lỗi (BusinessException / validation), 401 -> về Login.
const BASE = import.meta.env.VITE_API_BASE || '/api'
export const tok = { get: () => localStorage.getItem('token'), set: t => localStorage.setItem('token', t), clear: () => { localStorage.removeItem('token'); localStorage.removeItem('user') } }
export async function api(method, url, body) {
  const r = await fetch(BASE + url, { method, headers: { 'Content-Type': 'application/json', ...(tok.get() ? { Authorization: 'Bearer ' + tok.get() } : {}) }, body: body ? JSON.stringify(body) : undefined })
  let d = null; try { d = await r.json() } catch {}
  if (r.status === 401 && !url.startsWith('/auth/')) { tok.clear(); location.hash = '#/login' }
  if (!r.ok) { const e = new Error(d?.message || (r.status === 403 ? 'Bạn không có quyền thực hiện' : `Lỗi ${r.status}`)); e.status = r.status; e.code = d?.code; e.fields = d?.errors; throw e }
  return d
}
