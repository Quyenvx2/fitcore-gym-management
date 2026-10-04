import {useEffect,useState} from 'react'
import {useNavigate} from 'react-router-dom'
import {api} from '../api/client.js'
import {classIcon,Banner,ptIcon,metricIcon,reportIcon} from '../components/art.jsx'
import {useLoad,State,Table,Stats,Head,Form,act,money,fmt,Schedule,toast} from '../components/ui.jsx'

const today=()=>new Date().toISOString().slice(0,10)

export const Home=()=>{
  const s=useLoad(async()=>{
    const [stats,classes,regs,ptSessions]=await Promise.all([
      api('GET','/thong-ke/hoi-vien/cua-toi'),
      api('GET','/lop-hoc'),
      api('GET','/dang-ky-lop/cua-toi'),
      api('GET','/buoi-pt/cua-toi')
    ])
    const active=new Set((Array.isArray(regs)?regs:[]).filter(r=>r.trangThai==='Đăng kí thành công').map(r=>r.maLop))
    const now=new Date()
    const upcoming=[]
    ;(Array.isArray(classes)?classes:[]).filter(x=>active.has(x.maLop)).forEach(x=>{
      const start=x.ngayBatDau?new Date(x.ngayBatDau+'T00:00:00'):null
      if(start&&start>=new Date(now.getFullYear(),now.getMonth(),now.getDate()-1))upcoming.push({kind:'Lớp học',title:x.tenLop||`Lớp #${x.maLop}`,meta:`Thứ ${x.thuHoc} · ${x.gioBatDau?.slice(0,5)||'--:--'}–${x.gioKetThuc?.slice(0,5)||'--:--'}`})
    })
    ;(Array.isArray(ptSessions)?ptSessions:[]).filter(x=>x.trangThai==='Đã lên lịch').forEach(x=>upcoming.push({kind:'PT 1-1',title:'Buổi PT cá nhân',meta:`${fmt('d',x.thoiGianBatDau)} · ${x.thoiLuong||0} phút`}))
    return {stats:stats||{},upcoming:upcoming.slice(0,4)}
  })
  const user=JSON.parse(localStorage.getItem('user')||'{}')
  return <><Banner name={user.name||'bạn'} role="HOI_VIEN"/>
    <Head t="Tổng quan" s="Theo dõi hoạt động, lịch học và mục tiêu tập luyện của bạn" eyebrow="FITCORE MEMBER"/>
    <State s={s}>{d=><>
      <Stats d={d.stats}/>
      <div className="dashboard-grid mt">
        <div className="dashboard-panel schedule-preview">
          <div className="panel-head"><div><span className="page-eyebrow">SẮP DIỄN RA</span><h3>Lịch gần nhất</h3></div><span className="panel-chip">CẬP NHẬT</span></div>
          {d.upcoming.length?<div className="upcoming-list">{d.upcoming.map((x,i)=><div className="upcoming-item" key={i}><span className={'event-dot '+(x.kind==='PT 1-1'?'blue':'')}/><div><b>{x.title}</b><small>{x.meta}</small></div><em>{x.kind}</em></div>)}</div>:<p className="sub">Chưa có lịch sắp tới.</p>}
          <a className="inline-link" href="#/member/thoi-khoa-bieu">Xem toàn bộ thời khóa biểu →</a>
        </div>
        <AdCarousel/>
      </div>
    </>}</State>
  </>
}

function AdCarousel(){
  const [active,setActive]=useState(0)
  const slides=[
    {img:'/img/banner1.jpg',ey:'FITCORE GYM · ƯU ĐÃI',title:'Tập mạnh hơn. Sống tốt hơn.',text:'Đăng ký gói tập phù hợp và bắt đầu hành trình của bạn ngay hôm nay.'},
    {img:'/img/banner2.jpg',ey:'FITCORE GYM · KHÔNG GIAN',title:'Không gian tập luyện chuẩn.',text:'Thiết bị, khu vực tập và môi trường được thiết kế để bạn tập trung vào mục tiêu.'},
    {img:'/img/banner3.jpg',ey:'FITCORE GYM · THÀNH VIÊN',title:'Phiên bản tốt hơn mỗi ngày.',text:'Theo dõi lịch tập, PT và chỉ số cơ thể ngay trong một hệ thống.'}
  ]
  useEffect(()=>{const id=setInterval(()=>setActive(x=>(x+1)%slides.length),6000);return()=>clearInterval(id)},[])
  const x=slides[active]
  return <div className="dashboard-panel ad-panel">
    <div className="ad-media" style={{backgroundImage:`url(${x.img})`}}/>
    <div className="ad-shade"/>
    <div className="ad-copy"><span className="page-eyebrow">{x.ey}</span><h3>{x.title}</h3><p>{x.text}</p><a className="ad-link" href="#/member/goi">Khám phá FitCore →</a></div>
    <div className="ad-controls"><button type="button" onClick={()=>setActive((active+slides.length-1)%slides.length)} aria-label="Banner trước">‹</button><div>{slides.map((_,i)=><button key={i} type="button" className={i===active?'on':''} onClick={()=>setActive(i)} aria-label={`Banner ${i+1}`}/>)}</div><button type="button" onClick={()=>setActive((active+1)%slides.length)} aria-label="Banner sau">›</button></div>
  </div>
}

export function Profile(){
  const nav=useNavigate()
  const s=useLoad(()=>api('GET','/hoi-vien/cua-toi').catch(e=>{
    if(e.status===404||e.status===400)return {}
    throw e
  }))
  const has=s.d&&s.d.maHv
  const F=[
    ['cccd','CCCD'],
    ['hoTen','Họ tên'],
    ['ngaySinh','Ngày sinh','date'],
    ['gioiTinh','Giới tính','text',['Nam','Nữ']],
    ['diaChi','Địa chỉ'],
    ['sdt','Số điện thoại']
  ]
  return <>
    <Head t="Hồ sơ hội viên" s={has?'Cập nhật thông tin cá nhân':'Hãy hoàn thiện hồ sơ để bắt đầu sử dụng'}/>
    <State s={s}>
      {d=><Form
        fields={F}
        init={d}
        label={has?'Cập nhật':'Hoàn thiện hồ sơ'}
        onSubmit={o=>act(
          ()=>api(has?'PUT':'POST','/hoi-vien/cua-toi',o),
          'Đã lưu hồ sơ',
          ()=>{s.reload();!has&&nav('/member')}
        )}
      />}
    </State>
  </>
}

export function Goi(){
  const g=useLoad(()=>api('GET','/goi-tap'))
  const m=useLoad(()=>api('GET','/dang-ky-goi/cua-toi'))
  const id=r=>r.maDk??r.maDangKy??r.maDkGoi??r.id

  return <>
    <Head t="Gói tập" s="Chọn gói phù hợp mục tiêu của bạn"/>
    <State s={g}>
      {d=><div className="gr">
        {d.filter(x=>x.trangThai==='Active').map((x,i)=>
          <div key={x.maGoi} className="cd">
            <div className="package-art"><img src={i%2===0?"/img/icons/fitness.png":"/img/icons/muscle.png"} alt="" className="module-icon"/><span>{String(i+1).padStart(2,"0")}</span></div>
            <h3 style={{margin:'0 0 8px'}}>{x.tenGoi}</h3>
            <div className="pr">{money(x.giaTien)}<small> / {x.thoiHanThang} tháng</small></div>
            <p className="sub" style={{margin:'10px 0'}}>Số buổi PT kèm: {x.soBuoiPt}</p>
            <button className="btn" style={{width:'100%'}} onClick={()=>act(
              ()=>api('POST','/dang-ky-goi/cua-toi',{maGoi:x.maGoi,ngayBatDau:today()}),
              'Đăng ký thành công',
              m.reload
            )}>Đăng ký</button>
          </div>
        )}
      </div>}
    </State>

    <h3>Gói đã đăng ký</h3>
    <State s={m} empty="Bạn chưa đăng ký gói nào.">
      {d=><Table rows={d} actions={r=>r.trangThai==='Đang chờ kích hoạt'&&
        <button className="btn d s" onClick={()=>act(
          ()=>api('PATCH',`/dang-ky-goi/cua-toi/${id(r)}/huy`),
          'Đã hủy',
          m.reload
        )}>Hủy</button>
      }/>}
    </State>
  </>
}

export function Lop(){
  const s=useLoad(()=>api('GET','/lop-hoc'))
  return <>
    <Head t="Lớp học" s="Đăng ký lớp cùng huấn luyện viên"/>
    <State s={s}>
      {d=><div className="gr">
        {d.map(l=>{
          const open=l.trangThai==='Lớp đang mở'
          return <div key={l.maLop} className="cd" style={{padding:0,overflow:'hidden'}}>
            <div className="lh"><img src={classIcon(l.tenLop)} alt="" className="module-icon class-icon-img"/></div>
            <div style={{padding:20}}>
              <span className={'bd '+(open?'':'w')}>{l.trangThai}</span>
              <h3 style={{margin:'8px 0 2px'}}>{l.tenLop}</h3>
              <p className="sub" style={{margin:0}}>Thứ {l.thuHoc} · {l.gioBatDau?.slice(0,5)}–{l.gioKetThuc?.slice(0,5)}</p>
              <p className="sub" style={{margin:'4px 0'}}>{fmt('d',l.ngayBatDau)} → {fmt('d',l.ngayKetThuc)} · {l.soNguoiDaDangKy??0} người</p>
              <div style={{display:'flex',gap:8,marginTop:12}}>
                <button className="btn" style={{flex:1}} disabled={!open} onClick={()=>act(
                  ()=>api('POST','/dang-ky-lop/cua-toi',{maLop:l.maLop}),
                  'Đăng ký lớp thành công',
                  s.reload
                )}>Đăng ký</button>
                <button className="btn g" onClick={()=>act(
                  ()=>api('DELETE','/dang-ky-lop/cua-toi?maLop='+l.maLop),
                  'Đã hủy đăng ký',
                  s.reload
                )}>Hủy</button>
              </div>
            </div>
          </div>
        })}
      </div>}
    </State>
  </>
}

export function ThoiKhoaBieu(){
  const s=useLoad(async()=>{
    const [classes,regs,ptSessions]=await Promise.all([
      api('GET','/lop-hoc'),
      api('GET','/dang-ky-lop/cua-toi'),
      api('GET','/buoi-pt/cua-toi')
    ])
    const active=new Set((Array.isArray(regs)?regs:[])
      .filter(r=>r.trangThai==='Đăng kí thành công')
      .map(r=>r.maLop))
    return {
      classes:(Array.isArray(classes)?classes:[]).filter(l=>active.has(l.maLop)),
      ptSessions:Array.isArray(ptSessions)?ptSessions:[]
    }
  })
  return <>
    <Head t="Thời khóa biểu" s="Theo dõi toàn bộ lớp học và buổi PT đã đăng ký" eyebrow="LỊCH CÁ NHÂN"/>
    <State s={s} empty="Bạn chưa có lịch học hoặc buổi PT nào.">
      {d=><Schedule items={d.classes} ptSessions={d.ptSessions} role="member"/>}
    </State>
  </>
}

export function Pt(){
  const [selectedPt,setSelectedPt]=useState('')
  const s=useLoad(()=>api('GET','/buoi-pt/cua-toi'))
  const pts=useLoad(()=>api('GET','/pt/danh-sach-cho-hoi-vien'))

  return <>
    <Head t="PT của tôi" s="Xem huấn luyện viên và đặt buổi PT"/>

    <div className="cd">
      <h3 style={{marginTop:0}}>Danh sách huấn luyện viên</h3>
      <State s={pts} empty="Chưa có PT để hiển thị.">
        {d=><div className="gr">
          {d.map(pt=><div className="cd pt-card" key={pt.maPt}>
            <div className="pt-avatar"><img src={ptIcon} alt="" className="module-icon"/></div>
            <h3 style={{margin:'12px 0 4px'}}>{pt.hoTen}</h3>
            <p className="sub" style={{margin:'0 0 6px'}}>Chuyên môn: {pt.chuyenMon||'—'}</p>
            <p className="sub" style={{margin:0}}>Kinh nghiệm: {pt.soNamKinhNghiem??0} năm</p>
            <button className="btn s" style={{width:'100%',marginTop:14}} onClick={()=>setSelectedPt(String(pt.maPt))}>
              Đặt PT này
            </button>
          </div>)}
        </div>}
      </State>
    </div>

    <div className="booking-panel mt">
      <div className="booking-copy"><span className="calendar-kicker">ĐẶT LỊCH 1-1</span><h3>Chọn PT và thời gian phù hợp</h3><p className="sub">Chọn một ngày trong tương lai và giờ bắt đầu. Lịch sẽ xuất hiện ngay trong thời khóa biểu của bạn.</p></div>
      <Form
        fields={[
          ['maPt','Huấn luyện viên','text',pts.d?.map(x=>({value:x.maPt,label:`${x.hoTen} — ${x.chuyenMon||'Chưa cập nhật'}`}))||[]],
          ['thoiGianBatDau','Ngày & giờ bắt đầu','datetime-local'],
          ['thoiLuong','Thời lượng (phút)','number']
        ]}
        init={{maPt:selectedPt,thoiLuong:60}}
        onSubmit={o=>{
          if(!o.maPt||!o.thoiGianBatDau||!o.thoiLuong){toast('Vui lòng chọn PT, thời gian và thời lượng',1);return}
          if(new Date(o.thoiGianBatDau)<=new Date()){toast('Thời gian đặt PT phải ở tương lai',1);return}
          act(()=>api('POST','/buoi-pt/cua-toi',{...o,maPt:+o.maPt}),'Đặt buổi PT thành công',s.reload)
        }}
        label="Xác nhận đặt lịch"
      />
    </div>

    <h3>Lịch PT của tôi</h3>
    <State s={s} empty="Chưa có buổi PT.">
      {d=><Table rows={d} actions={r=>r.trangThai==='Đã lên lịch'&&
        <button className="btn d s" onClick={()=>act(
          ()=>api('DELETE','/buoi-pt/cua-toi/'+r.maBuoiPt),
          'Đã hủy',
          s.reload
        )}>Hủy</button>
      }/>}
    </State>
  </>
}

export function CheckIn(){
  const s=useLoad(()=>api('GET','/check-in-out/cua-toi'))
  const [out,setOut]=useState(false)
  const click=()=>act(
    ()=>api(out?'PUT':'POST','/check-in-out/cua-toi/'+(out?'check-out':'check-in')),
    out?'Check-out thành công':'Check-in thành công',
    ()=>{setOut(!out);s.reload()}
  )
  return <>
    <Head t="Check-in" s="Chạm để vào / ra phòng tập"/>
    <div className="action-hero"><button className={'ck '+(out?'out':'')} onClick={click}>{out?'CHECK-OUT':'CHECK-IN'}</button></div>
    <h3>Lịch sử</h3><State s={s}>{d=><Table rows={d}/>}</State>
  </>
}

export function ChiSo(){
  const s=useLoad(()=>api('GET','/chi-so-co-the/cua-toi'))
  return <>
    <Head t="Chỉ số cơ thể" s="Do nhân viên đo định kỳ"/>
    <State s={s} empty="Chưa có dữ liệu chỉ số.">
      {d=>{
        const r=[...d].sort((a,b)=>a.ngayDo<b.ngayDo?-1:1),w=r.map(x=>x.canNang)
        const mn=Math.min(...w)-1,mx=Math.max(...w)+1
        const pts=r.map((x,i)=>[20+(r.length>1?i*600/(r.length-1):300),190-(x.canNang-mn)/(mx-mn)*170])
        return <>
          <div className="cd metric-card"><div className="metric-title"><img src={metricIcon} alt="" className="module-icon"/> <span>Xu hướng cân nặng</span></div>
            <svg viewBox="0 0 640 220" style={{width:'100%'}}>
              <polyline fill="none" stroke="var(--ac)" strokeWidth="3" points={pts.map(p=>p.join(',')).join(' ')}/>
              {pts.map((p,i)=><g key={i}><circle cx={p[0]} cy={p[1]} r="5" fill="var(--ac)"/><text x={p[0]} y={p[1]-10} fill="var(--tx)" fontSize="11" textAnchor="middle">{r[i].canNang}</text></g>)}
            </svg>
          </div>
          <h3>Chi tiết</h3><Table rows={r.reverse()}/>
        </>
      }}
    </State>
  </>
}
