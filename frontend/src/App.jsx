import {Routes,Route,Navigate,NavLink,Outlet,useNavigate,useLocation} from 'react-router-dom'
import {useEffect} from 'react'
import {useAuth,HOME} from './auth.jsx'
import {api} from './api/client.js'
import Auth from './pages/Auth.jsx'
import * as M from './pages/Member.jsx'
import * as P from './pages/Pt.jsx'
import * as A from './pages/Admin.jsx'


const NAV={
  HOI_VIEN:[
    ['/member','Tổng quan',1],
    ['/member/goi','Gói tập'],
    ['/member/lop','Lớp học'],
    ['/member/thoi-khoa-bieu','Thời khóa biểu'],
    ['/member/pt','PT'],
    ['/member/check-in','Check-in'],
    ['/member/chi-so','Chỉ số'],
    ['/member/profile','Hồ sơ']
  ],
  PT:[
    ['/pt','Tổng quan',1],
    ['/pt/buoi-pt','Buổi PT'],
    ['/pt/lop','Lớp học'],
    ['/pt/thoi-khoa-bieu','Thời khóa biểu'],
    ['/pt/profile','Hồ sơ']
  ],
  NHAN_VIEN:[
    ['/admin','Tổng quan',1],
    ...A.ADMIN_NAV,
    ['/admin/chi-so','Chỉ số cơ thể'],
    ['/admin/gia-pt','Giá PT']
  ]
}

function Layout({role}){
  const {user,logout}=useAuth(),nav=useNavigate(),loc=useLocation()

  useEffect(()=>{
    if(role==='HOI_VIEN'){
      api('GET','/hoi-vien/cua-toi').catch(e=>{
        if(e.status===404||e.status===400)nav('/member/profile')
      })
    }
  },[])

  return <div className="sh">
    <div className="site-background" aria-hidden="true"><div className="site-background-image"/></div>
    <aside>
      <div className="logo brand-logo"><img src="/img/fitcore-logo.png" alt="FitCore"/><span>Fit<b>Core</b></span></div>
      {NAV[role].map(([to,label,end])=>
        <NavLink key={to} to={to} end={!!end} className={({isActive})=>'nv'+(isActive?' on':'')}>
          {label}
        </NavLink>
      )}
      <div className="me">
        <b>{user.name}</b>
        <small>{role}</small>
        <button className="btn g s" style={{marginTop:10}} onClick={logout}>Đăng xuất</button>
      </div>
    </aside>
    <main>
      <div className="top">
        <div className="logo brand-logo brand-logo-top"><img src="/img/fitcore-logo.png" alt="FitCore"/><span>Fit<b>Core</b></span></div>
        <button className="btn g s" onClick={logout}>Thoát</button>
      </div>
      <div className="v">
        <Outlet/>
      </div>
    </main>
  </div>
}

const Guard=({role})=>{
  const {user}=useAuth()
  return !user?<Navigate to="/login" replace/>:
    user.role!==role?<Navigate to={HOME[user.role]} replace/>:
    <Layout role={role}/>
}

export default function App(){
  const {user}=useAuth()
  return <><Routes>
    <Route path="/login" element={user?<Navigate to={HOME[user.role]} replace/>:<Auth/>}/>

    <Route path="/member" element={<Guard role="HOI_VIEN"/>}>
      <Route index element={<M.Home/>}/>
      <Route path="goi" element={<M.Goi/>}/>
      <Route path="lop" element={<M.Lop/>}/>
      <Route path="thoi-khoa-bieu" element={<M.ThoiKhoaBieu/>}/>
      <Route path="pt" element={<M.Pt/>}/>
      <Route path="check-in" element={<M.CheckIn/>}/>
      <Route path="chi-so" element={<M.ChiSo/>}/>
      <Route path="profile" element={<M.Profile/>}/>
    </Route>

    <Route path="/pt" element={<Guard role="PT"/>}>
      <Route index element={<P.Home/>}/>
      <Route path="buoi-pt" element={<P.Buoi/>}/>
      <Route path="lop" element={<P.Lop/>}/>
      <Route path="thoi-khoa-bieu" element={<P.ThoiKhoaBieu/>}/>
      <Route path="profile" element={<P.Profile/>}/>
    </Route>

    <Route path="/admin" element={<Guard role="NHAN_VIEN"/>}>
      <Route index element={<A.Home/>}/>
      <Route path="chi-so" element={<A.ChiSo/>}/>
      <Route path="gia-pt" element={<A.GiaPt/>}/>
      <Route path=":res" element={<A.Resource/>}/>
    </Route>

    <Route path="*" element={<Navigate to={user?HOME[user.role]:'/login'} replace/>}/>
  </Routes></>
}
