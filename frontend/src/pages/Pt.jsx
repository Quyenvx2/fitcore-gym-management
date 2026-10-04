import {api} from '../api/client.js'
import {useLoad,State,Table,Stats,Head,Form,act,Schedule} from '../components/ui.jsx'
import {Banner} from '../components/art.jsx'

export const Home=()=>{
  const s=useLoad(async()=>{
    const [stats,me,classes,ptSessions]=await Promise.all([
      api('GET','/thong-ke/pt/cua-toi'),
      api('GET','/pt/cua-toi'),
      api('GET','/lop-hoc'),
      api('GET','/buoi-pt/pt-cua-toi')
    ])
    return {stats:stats||{},me:me||{},classes:(Array.isArray(classes)?classes:[]).filter(x=>String(x.maPt)===String(me?.maPt)),ptSessions:Array.isArray(ptSessions)?ptSessions:[]}
  })
  const user=JSON.parse(localStorage.getItem('user')||'{}')
  return <><Banner name={s.d?.me?.hoTen||user.name||'bạn'} role="PT"/>
    <Head t="Tổng quan" s="Theo dõi lớp phụ trách và lịch dạy cá nhân" eyebrow="FITCORE COACH"/>
    <State s={s}>{d=><>
      <Stats d={d.stats}/>
      <div className="dashboard-grid mt">
        <div className="dashboard-panel schedule-preview">
          <div className="panel-head"><div><span className="page-eyebrow">LỊCH DẠY</span><h3>Lớp đang phụ trách</h3></div><span className="panel-chip">{d.classes.length} LỚP</span></div>
          {d.classes.length?<div className="upcoming-list">{d.classes.slice(0,4).map(x=><div className="upcoming-item" key={x.maLop}><span className="event-dot"/><div><b>{x.tenLop||`Lớp #${x.maLop}`}</b><small>Thứ {x.thuHoc} · {x.gioBatDau?.slice(0,5)}–{x.gioKetThuc?.slice(0,5)}</small></div><em>LỚP HỌC</em></div>)}</div>:<p className="sub">Chưa có lớp được phân công.</p>}
          <a className="inline-link" href="#/pt/thoi-khoa-bieu">Mở lịch dạy chi tiết →</a>
        </div>
        <div className="dashboard-panel visual-panel coach-visual">
          <div className="coach-photo"></div>
          <div className="coach-copy"><span className="page-eyebrow">COACH MODE</span><h3>Sẵn sàng cho buổi tập?</h3><p className="sub">Tập trung vào từng buổi, từng học viên và từng tiến bộ nhỏ.</p></div>
          <div className="coach-orbit"><div className="orbit-ring r1"/><div className="orbit-ring r2"/><div className="orbit-core">FC</div><div className="orbit-dot d1"/><div className="orbit-dot d2"/><div className="orbit-dot d3"/></div><small className="visual-note">Dữ liệu thật vẫn nằm trong các module nghiệp vụ.</small>
        </div>
      </div>
    </>}</State>
  </>
}

export function Buoi(){
  const s=useLoad(()=>api('GET','/buoi-pt/pt-cua-toi'))
  const set=(id,t)=>act(
    ()=>api('PUT',`/buoi-pt/${id}/trang-thai`,{trangThai:t}),
    'Đã cập nhật: '+t,
    s.reload
  )
  return <>
    <Head t="Buổi PT của tôi" s="Cập nhật kết quả sau mỗi buổi"/>
    <State s={s}>
      {d=><Table rows={d} actions={r=>r.trangThai==='Đã lên lịch'&&<>
        <button className="btn s" onClick={()=>set(r.maBuoiPt,'Đã hoàn thành')}>Hoàn thành</button>
        <button className="btn g s" onClick={()=>set(r.maBuoiPt,'Vắng mặt')}>Vắng</button>
      </>}/>}
    </State>
  </>
}

export const Lop=()=>{
  const s=useLoad(()=>api('GET','/lop-hoc'))
  return <><Head t="Lớp học" s="Danh sách lớp trong hệ thống"/><State s={s}>{d=><Table rows={d}/>}</State></>
}

export function ThoiKhoaBieu(){
  const s=useLoad(async()=>{
    const [me,classes,ptSessions]=await Promise.all([
      api('GET','/pt/cua-toi'),
      api('GET','/lop-hoc'),
      api('GET','/buoi-pt/pt-cua-toi')
    ])
    return {
      classes:(Array.isArray(classes)?classes:[]).filter(l=>String(l.maPt)===String(me.maPt)),
      ptSessions:Array.isArray(ptSessions)?ptSessions:[]
    }
  })
  return <>
    <Head t="Thời khóa biểu" s="Lịch dạy nhiều ngày của bạn, gồm lớp và PT 1-1" eyebrow="LỊCH GIẢNG DẠY"/>
    <State s={s} empty="Bạn chưa có lịch dạy nào.">
      {d=><Schedule items={d.classes} ptSessions={d.ptSessions} role="pt"/>}
    </State>
  </>
}

export function Profile(){
  const s=useLoad(()=>api('GET','/pt/cua-toi'))
  const F=[
    ['cccd','CCCD'],
    ['hoTen','Họ tên'],
    ['ngaySinh','Ngày sinh','date'],
    ['sdt','Số điện thoại'],
    ['chuyenMon','Chuyên môn'],
    ['soNamKinhNghiem','Số năm kinh nghiệm','number']
  ]
  return <><Head t="Hồ sơ PT" s="Lương và trạng thái do quản lý nhân sự đặt"/><State s={s}>{d=><Form fields={F} init={d} label="Cập nhật" onSubmit={o=>act(()=>api('PUT','/pt/cua-toi',o),'Đã lưu',s.reload)}/>}</State></>
}
