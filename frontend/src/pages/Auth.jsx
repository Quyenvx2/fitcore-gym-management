import {useEffect,useRef,useState} from 'react'
import {useAuth} from '../auth.jsx'
import {toast,errMsg} from '../components/ui.jsx'
export default function Auth(){
  const {login,register}=useAuth(),[reg,setReg]=useState(false),[u,setU]=useState(''),[p,setP]=useState(''),[b,setB]=useState(false),[errors,setErrors]=useState({})
  const usernameRef=useRef(null)
  useEffect(()=>{ usernameRef.current?.focus() },[reg])
  const go=async e=>{
    e.preventDefault()
    const next={}
    if(!/^[A-Za-z0-9_]+$/.test(u))next.u='Tên đăng nhập chỉ được chứa chữ cái không dấu, số và dấu gạch dưới.'
    else if(u.length<3||u.length>50)next.u='Tên đăng nhập phải từ 3 đến 50 ký tự.'
    if(p.length<6)next.p='Mật khẩu phải có ít nhất 6 ký tự.'
    setErrors(next)
    if(Object.keys(next).length)return
    setB(true)
    try{await (reg?register:login)(u,p)}catch(x){toast(errMsg(x),1)}
    setB(false)
  }
  return <div className="lg"><div className="hero"><div className="logo brand-logo auth-brand"><img src="/img/fitcore-logo.png" alt="FitCore"/><span>Fit<b>Core</b></span></div><h1>Tập luyện <em>thông minh</em>,<br/>quản lý <em>trọn vẹn</em>.</h1><p>Đăng ký gói, đặt lớp, đặt PT và theo dõi tiến độ cơ thể trong một nơi duy nhất.</p><div className="kp"><div><b>1.2k+</b><span>Hội viên</span></div><div><b>24</b><span>Huấn luyện viên</span></div><div><b>18</b><span>Lớp mỗi tuần</span></div></div></div>
  <div className="fm"><form onSubmit={go}><h2>{reg?'Tạo tài khoản':'Đăng nhập'}</h2><p className="sub">{reg?'Tài khoản mới luôn là Hội viên':'Chào mừng trở lại'}</p>
   <div className={errors.u?'field-invalid':''}><label>Tên đăng nhập (3–50 ký tự)</label><input ref={usernameRef} autoComplete={reg?'username':'username'} value={u} onChange={e=>{setU(e.target.value);setErrors(x=>({...x,u:''}))}} maxLength={50} required/>{errors.u&&<small className="field-error">{errors.u}</small>}</div>
   <div className={errors.p?'field-invalid':''}><label>Mật khẩu (≥ 6 ký tự)</label><input type="password" value={p} onChange={e=>{setP(e.target.value);setErrors(x=>({...x,p:''}))}} minLength={6} required/>{errors.p&&<small className="field-error">{errors.p}</small>}</div>
   <button className="btn" style={{width:'100%',marginTop:18}} disabled={b}>{b?'Đang xử lý…':reg?'Đăng ký':'Đăng nhập'}</button>
   <p className="sub" style={{marginTop:16,textAlign:'center'}}>{reg?'Đã có tài khoản?':'Chưa có tài khoản?'} <a href="#" style={{color:'var(--ac)'}} onClick={e=>{e.preventDefault();setReg(!reg)}}>{reg?'Đăng nhập':'Đăng ký'}</a></p></form></div></div>
}
