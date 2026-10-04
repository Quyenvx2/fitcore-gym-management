import {createContext,useContext,useState} from 'react'
import {api,tok} from './api/client.js'
const Ctx=createContext()
export const useAuth=()=>useContext(Ctx)
export const HOME={NHAN_VIEN:'/admin',PT:'/pt',HOI_VIEN:'/member'}
export function AuthProvider({children}){
  const [user,setUser]=useState(()=>{try{return JSON.parse(localStorage.getItem('user'))}catch{return null}})
  const login=async(tenDangNhap,matKhau)=>{const d=await api('POST','/auth/login',{tenDangNhap,matKhau});tok.set(d.token);const u={name:d.tenDangNhap,role:d.vaiTro};localStorage.setItem('user',JSON.stringify(u));setUser(u);return u}
  const register=async(tenDangNhap,matKhau)=>{await api('POST','/auth/register',{tenDangNhap,matKhau});return login(tenDangNhap,matKhau)}
  const logout=()=>{tok.clear();setUser(null)}
  return <Ctx.Provider value={{user,login,register,logout}}>{children}</Ctx.Provider>
}
