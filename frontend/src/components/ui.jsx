import {useEffect,useState,useCallback} from 'react'

export const toast=(m,e)=>{
  const d=document.createElement('div')
  d.className='t'+(e?' e':'')
  d.textContent=m
  document.getElementById('toast').append(d)
  setTimeout(()=>d.remove(),3800)
}
export const errMsg=e=>e.fields?Object.values(e.fields).join(' · '):e.code?`${e.message} (${e.code})`:e.message
export const act=async(fn,ok,after)=>{
  try{await fn();ok&&toast(ok);after&&after()}
  catch(e){toast(errMsg(e),1)}
}
export const money=n=>Number(n).toLocaleString('vi-VN')+'đ'
export function fmt(k,v){
  if(v==null)return '—'
  if(typeof v==='string'&&/^\d{4}-\d\d-\d\dT/.test(v))return new Date(v).toLocaleString('vi-VN',{dateStyle:'short',timeStyle:'short'})
  if(typeof v==='string'&&/^\d{4}-\d\d-\d\d$/.test(v))return new Date(v).toLocaleDateString('vi-VN')
  if(typeof v==='number'&&/gia|luong|tien/i.test(k))return money(v)
  return String(v)
}

export function useLoad(fn,deps=[]){
  const [s,set]=useState({d:null,l:true,e:null})
  const run=useCallback(()=>{
    set(x=>({...x,l:true}))
    fn().then(d=>set({d,l:false,e:null})).catch(e=>set({d:null,l:false,e}))
  },deps)
  useEffect(run,[run])
  return {...s,reload:run}
}

export const Head=({t,s,eyebrow})=><div className="page-head">
  {eyebrow&&<div className="page-eyebrow">{eyebrow}</div>}
  <div className="page-title-row"><div><h2>{t}</h2><p className="sub">{s}</p></div><div className="title-mark"><i/><i/><i/></div></div>
</div>

export function State({s,children,empty='Chưa có dữ liệu.'}){
  if(s.l&&!s.d)return <div className="cd">Đang tải…</div>
  if(s.e)return <div className="cd" style={{borderColor:'var(--co)'}}>{errMsg(s.e)}</div>
  if(Array.isArray(s.d)&&!s.d.length)return <div className="cd" style={{textAlign:'center',padding:36}}><p className="sub" style={{margin:'8px 0 0'}}>{empty}</p></div>
  return children(s.d)
}

const FIELD_LABELS={
  maTk:'Mã tài khoản',maHv:'Mã hội viên',maPt:'Mã PT',maPhong:'Mã phòng',maGoi:'Mã gói',maLop:'Mã lớp',maBuoi:'Mã buổi',maBuoiPt:'Mã buổi PT',
  maDk:'Mã đăng ký',maDkGoi:'Mã đăng ký gói',maDkLop:'Mã đăng ký lớp',tenDangNhap:'Tên đăng nhập',vaiTro:'Vai trò',trangThai:'Trạng thái',
  cccd:'CCCD',hoTen:'Họ tên',ngaySinh:'Ngày sinh',gioiTinh:'Giới tính',diaChi:'Địa chỉ',sdt:'Số điện thoại',
  chuyenMon:'Chuyên môn',soNamKinhNghiem:'Số năm kinh nghiệm',luongCoBan:'Lương cơ bản',tenPhong:'Tên phòng',viTri:'Vị trí',sucChua:'Sức chứa',
  tenGoi:'Tên gói',thoiHanThang:'Thời hạn (tháng)',giaTien:'Giá tiền',soBuoiPt:'Số buổi PT',soBuoiPtConLai:'Số buổi PT còn lại',
  tenLop:'Tên lớp',donGiaPt:'Đơn giá PT',thuHoc:'Thứ học',gioBatDau:'Giờ bắt đầu',gioKetThuc:'Giờ kết thúc',ngayBatDau:'Ngày bắt đầu',ngayKetThuc:'Ngày kết thúc',
  ngayDangKy:'Ngày đăng ký',thoiGianBatDau:'Thời gian bắt đầu',thoiLuong:'Thời lượng',ngayHoc:'Ngày học',
  thoiGianCheckIn:'Thời gian check-in',thoiGianCheckout:'Thời gian check-out',canNang:'Cân nặng',chieuCao:'Chiều cao',phanTramMo:'Phần trăm mỡ',vongEo:'Vòng eo',
  message:'Thông báo',createdAt:'Ngày tạo',updatedAt:'Cập nhật'
}
const fieldLabel=k=>FIELD_LABELS[k]||k.replace(/([a-z])([A-Z])/g,'$1 $2').replace(/_/g,' ').replace(/^./,x=>x.toUpperCase())
export function Table({rows,actions,hide=[]}){
  const cols=Object.keys(rows[0]||{}).filter(k=>!hide.includes(k)&&typeof rows[0][k]!=='object')
  return <div className="cd tw"><table><thead><tr>{cols.map(c=><th key={c}>{fieldLabel(c)}</th>)}{actions&&<th>Thao tác</th>}</tr></thead><tbody>{rows.map((r,i)=><tr key={i}>{cols.map(c=><td key={c}>{fmt(c,r[c])}</td>)}{actions&&<td style={{whiteSpace:'nowrap'}}>{actions(r)}</td>}</tr>)}</tbody></table></div>
}

function Num({v}){
  const [n,set]=useState(0)
  useEffect(()=>{
    if(typeof v!=='number')return
    let s=null,id
    const f=t=>{
      s??=t
      const p=Math.min((t-s)/900,1)
      set(Math.round(v*(1-Math.pow(1-p,3))))
      if(p<1)id=requestAnimationFrame(f)
    }
    id=requestAnimationFrame(f)
    return()=>cancelAnimationFrame(id)
  },[v])
  return typeof v==='number'?n:String(v)
}
const STAT_LABELS={
  soGoiTapDaDangKy:'Gói tập đang sở hữu', soBuoiPtDaSuDung:'Buổi PT đã sử dụng', soLopHocDaDangKy:'Lớp học đang đăng ký', soLanCheckIn:'Lượt check-in', soLanDoChiSoCoThe:'Lần đo cơ thể',
  tongHoiVien:'Tổng hội viên', tongPt:'Tổng PT', tongLopHoc:'Tổng lớp học', doanhThu:'Doanh thu', doanhThuThangNay:'Doanh thu tháng này',
  soHoiVien:'Hội viên', soBuoiPt:'Buổi PT', soLopHoc:'Lớp học', soCheckIn:'Lượt check-in', soBuoiHoanThanh:'Buổi đã hoàn thành', soHocVien:'Học viên',
  soBuoiPtDaDat:'Buổi PT đã đặt', soLopDangPhuTrach:'Lớp đang phụ trách', soBuoiDaDay:'Buổi đã dạy', soBuoiDaHoanThanh:'Buổi đã hoàn thành'
}
const statLabel=k=>STAT_LABELS[k]||k.replace(/([a-z])([A-Z])/g,'$1 $2').replace(/^./,x=>x.toUpperCase())
export const Stats=({d})=><div className="gr stats-grid">{Object.entries(d||{}).filter(([,v])=>v!=null).map(([k,v],i)=><div key={k} className="cd st"><span className="stat-index">{String(i+1).padStart(2,'0')}</span><small>{statLabel(k)}</small><b>{typeof v==='number'&&/gia|luong|tien|doanhThu/i.test(k)?money(v):<Num v={v}/>}</b><i>FITCORE · LIVE</i></div>)}</div>

export function Form({fields,init={},onSubmit,label='Lưu',onCancel}){
  const [v,set]=useState(init),[errors,setErrors]=useState({})
  useEffect(()=>{set(init);setErrors({})},[JSON.stringify(init)])
  const validate=(k,x,t)=>{
    const s=String(x??'').trim()
    if(!s)return ''
    if(k==='tenDangNhap' && !/^[A-Za-z0-9_]+$/.test(s))return 'Tên đăng nhập chỉ được chứa chữ cái không dấu, số và dấu gạch dưới.'
    if(k==='cccd' && !/^\d{12}$/.test(s))return 'CCCD phải gồm đúng 12 chữ số.'
    if(k==='sdt' && !/^\d{10,11}$/.test(s))return 'Số điện thoại phải gồm 10–11 chữ số.'
    if(k==='matKhau' && s.length<6)return 'Mật khẩu phải có ít nhất 6 ký tự.'
    if(['soNamKinhNghiem','soBuoiPt','soBuoiPtConLai'].includes(k) && Number(x)<0)return 'Giá trị không được âm.'
    if(['sucChua','thoiHanThang'].includes(k) && Number(x)<1)return 'Giá trị phải lớn hơn hoặc bằng 1.'
    if(['giaTien','luongCoBan','donGiaPt'].includes(k) && Number(x)<=0)return 'Giá trị phải lớn hơn 0.'
    return ''
  }
  const sub=e=>{
    e.preventDefault()
    const next={}
    fields.forEach(([k,,t])=>{const msg=validate(k,v[k],t);if(msg)next[k]=msg})
    setErrors(next)
    if(Object.keys(next).length)return
    const o={}
    fields.forEach(([k,,t])=>{
      let x=v[k]
      if(x===''||x==null)return
      if(t==='number')o[k]=+x
      else if(t==='datetime-local'&&x.length===16)o[k]=x+':00'
      else o[k]=x
    })
    onSubmit(o)
  }
  return <form onSubmit={sub} className="cd" noValidate>
    <div className="row">
      {fields.map(([k,l,t='text',opts])=><div key={k} className={errors[k]?'field-invalid':''}>
        <label>{l}</label>
        {opts?<select value={v[k]??''} onChange={e=>{set({...v,[k]:e.target.value});if(errors[k])setErrors(x=>({...x,[k]:''}))}}>
          <option value=""></option>
          {opts.map(o=><option key={typeof o==='object'?o.value:o} value={typeof o==='object'?o.value:o}>{typeof o==='object'?o.label:o}</option>)}
        </select>:<input type={t} step={t==='number'?'any':undefined} min={t==='datetime-local'?new Date(Date.now()-new Date().getTimezoneOffset()*60000).toISOString().slice(0,16):undefined} value={v[k]??''} onChange={e=>{set({...v,[k]:e.target.value});if(errors[k])setErrors(x=>({...x,[k]:''}))}}/>}
        {errors[k]&&<small className="field-error">{errors[k]}</small>}
      </div>)}
    </div>
    <div style={{marginTop:16,display:'flex',gap:8}}>
      <button className="btn">{label}</button>
      {onCancel&&<button type="button" className="btn g" onClick={onCancel}>Hủy</button>}
    </div>
  </form>
}
/*
 * Lịch tuần dùng trực tiếp dữ liệu LOP_HOC.
 * thuHoc có thể là chuỗi như "2,4,6"; mỗi lớp được đưa vào đúng các cột Thứ 2 -> Chủ nhật.
 */
const WEEK=[
  ['2','Thứ 2'],['3','Thứ 3'],['4','Thứ 4'],['5','Thứ 5'],
  ['6','Thứ 6'],['7','Thứ 7'],['CN','Chủ nhật']
]
const normalizeDays=v=>{
  if(v==null)return []
  const s=String(v).toLowerCase().replace(/\s+/g,'')
  if(s.includes('cn'))return [...s.replace(/cn/g,'').split(',').filter(Boolean),'CN']
  return s.split(',').filter(Boolean)
}
function parseLocalDate(v){
  if(!v)return null
  const [y,m,d]=String(v).slice(0,10).split('-').map(Number)
  if(!y||!m||!d)return null
  return new Date(y,m-1,d)
}
function isoDate(d){
  const y=d.getFullYear(),m=String(d.getMonth()+1).padStart(2,'0'),day=String(d.getDate()).padStart(2,'0')
  return `${y}-${m}-${day}`
}
function startOfDay(v){const d=v instanceof Date?new Date(v):parseLocalDate(v); d?.setHours(0,0,0,0); return d}
function daysInMonth(y,m){return new Date(y,m+1,0).getDate()}
function dateRangeLabel(d){return d.toLocaleDateString('vi-VN',{month:'long',year:'numeric'})}
function normalizeWeekday(v){
  const raw=String(v??'').toLowerCase().replace(/\s+/g,'').replace(/thứ/g,'').replace(/^t(?=\d)/,'')
  return raw.split(/[,;/-]/).filter(Boolean).map(x=>{
    if(x==='cn'||x==='0'||x==='8')return 0
    const n=Number(x)
    return Number.isFinite(n)&&n>=2&&n<=7?n-1:null
  }).filter(x=>x!=null)
}
function expandClassOccurrences(item,monthDate){
  const start=startOfDay(item.ngayBatDau), end=startOfDay(item.ngayKetThuc)
  if(!start||!end)return []
  const y=monthDate.getFullYear(),m=monthDate.getMonth()
  const first=new Date(y,m,1), last=new Date(y,m,daysInMonth(y,m))
  const from=start>first?start:first, to=end<last?end:last
  if(from>to)return []
  const weekdays=normalizeWeekday(item.thuHoc)
  const out=[]
  for(let d=new Date(from);d<=to;d.setDate(d.getDate()+1)){
    if(weekdays.includes(d.getDay()))out.push({date:new Date(d),type:'class',source:item})
  }
  return out
}
function ptOccurrence(item){
  const dt=item.thoiGianBatDau||item.ngayBatDau
  const date=parseLocalDate(dt)
  return date?{date,type:'pt',source:item}:null
}
function displayTime(v){return v?String(v).slice(0,5):'--:--'}
function monthTitle(d){return d.toLocaleDateString('vi-VN',{month:'long',year:'numeric'}).replace(/^./,c=>c.toUpperCase())}
export function Schedule({items=[],role,ptSessions=[],initialMonth}){
  const now=new Date();
  const [cursor,setCursor]=useState(()=>initialMonth?new Date(initialMonth+'-01'):new Date(now.getFullYear(),now.getMonth(),1))
  const [selected,setSelected]=useState(null)
  const monthStart=new Date(cursor.getFullYear(),cursor.getMonth(),1)
  const firstWeekday=(monthStart.getDay()+6)%7
  const total=daysInMonth(cursor.getFullYear(),cursor.getMonth())
  const cells=Math.ceil((firstWeekday+total)/7)*7
  const events=[]
  items.forEach(x=>expandClassOccurrences(x,cursor).forEach(e=>events.push(e)))
  ;(ptSessions||[]).forEach(x=>{const e=ptOccurrence(x);if(e&&e.date.getFullYear()===cursor.getFullYear()&&e.date.getMonth()===cursor.getMonth())events.push(e)})
  const byDate={}
  events.forEach(e=>{const k=isoDate(e.date);(byDate[k]??=[]).push(e)})
  const todayKey=isoDate(now)
  const selectedEvents=selected?byDate[selected]||[]:[]
  return <div className="calendar-shell">
    <div className="calendar-toolbar">
      <div>
        <span className="calendar-kicker">LỊCH TRÌNH</span>
        <strong>{monthTitle(cursor)}</strong>
        <small>{role==='member'?'Lịch học và buổi PT đã đăng ký':'Lịch dạy lớp và các buổi PT'}</small>
      </div>
      <div className="calendar-actions">
        <button className="btn g s" onClick={()=>{setSelected(null);setCursor(new Date(now.getFullYear(),now.getMonth(),1))}}>Hôm nay</button>
        <button className="cal-arrow" onClick={()=>{setSelected(null);setCursor(new Date(cursor.getFullYear(),cursor.getMonth()-1,1))}}>‹</button>
        <button className="cal-arrow" onClick={()=>{setSelected(null);setCursor(new Date(cursor.getFullYear(),cursor.getMonth()+1,1))}}>›</button>
      </div>
    </div>
    <div className="calendar-weekdays">{['T2','T3','T4','T5','T6','T7','CN'].map(x=><div key={x}>{x}</div>)}</div>
    <div className="calendar-grid">
      {Array.from({length:cells},(_,i)=>{
        const day=i-firstWeekday+1
        if(day<1||day>total)return <div className="calendar-cell muted-cell" key={i}/>
        const d=new Date(cursor.getFullYear(),cursor.getMonth(),day), key=isoDate(d), ev=byDate[key]||[], isToday=key===todayKey, isSelected=key===selected
        return <button className={'calendar-cell '+(isToday?'today ':'')+(isSelected?'selected':'')} key={key} onClick={()=>setSelected(key)}>
          <span className="calendar-date">{day}</span>
          <div className="calendar-events">
            {ev.slice(0,3).map((e,j)=>{
              const x=e.source
              return <span className={'calendar-event '+e.type} key={j}><b>{e.type==='class'?(x.tenLop||`Lớp #${x.maLop}`):'Buổi PT'}</b><small>{e.type==='class'?`${displayTime(x.gioBatDau)}–${displayTime(x.gioKetThuc)}`:displayTime(x.thoiGianBatDau?.slice(11)||x.thoiGianBatDau)}</small></span>
            })}
            {ev.length>3&&<em>+{ev.length-3} lịch khác</em>}
          </div>
        </button>
      })}
    </div>
    {selected&&<div className="calendar-detail">
      <div><span className="calendar-kicker">NGÀY ĐÃ CHỌN</span><h3>{new Date(selected+'T00:00:00').toLocaleDateString('vi-VN',{weekday:'long',day:'2-digit',month:'2-digit',year:'numeric'})}</h3></div>
      {!selectedEvents.length?<p className="sub">Không có lịch trong ngày này.</p>:<div className="detail-list">{selectedEvents.map((e,i)=>{const x=e.source;return <div className={'detail-item '+e.type} key={i}><div><b>{e.type==='class'?(x.tenLop||`Lớp #${x.maLop}`):'Buổi PT'}</b><small>{e.type==='class'?`Thứ ${x.thuHoc} · ${displayTime(x.gioBatDau)}–${displayTime(x.gioKetThuc)}`:`${displayTime(x.thoiGianBatDau?.slice(11)||x.thoiGianBatDau)} · ${x.thoiLuong?x.thoiLuong+' phút':''}`}</small></div><span>{e.type==='class'?'LỚP HỌC':'PT 1-1'}</span></div>})}</div>}
    </div>}
  </div>
}
