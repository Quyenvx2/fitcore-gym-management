import {useState} from 'react'
import {Banner} from '../components/art.jsx'
import {useParams} from 'react-router-dom'
import {api} from '../api/client.js'
import {useLoad,State,Table,Stats,Head,Form,act,money} from '../components/ui.jsx'
const T=(k,l,t,o)=>[k,l,t||'text',o]
// Cấu hình CRUD theo từng tài nguyên: url, khóa chính, form (f), nút thao tác thêm (x)
const RES={
 'tai-khoan':{t:'Tài khoản',url:'/tai-khoan',id:'maTk',f:[T('tenDangNhap','Tên đăng nhập'),T('matKhau','Mật khẩu','password'),T('vaiTro','Vai trò','text',['NHAN_VIEN','PT','HOI_VIEN']),T('trangThai','Trạng thái')]},
 'hoi-vien':{t:'Hội viên',url:'/hoi-vien',id:'maHv',f:[T('maTk','Mã tài khoản','number'),T('cccd','CCCD'),T('hoTen','Họ tên'),T('ngaySinh','Ngày sinh','date'),T('gioiTinh','Giới tính','text',['Nam','Nữ']),T('diaChi','Địa chỉ'),T('sdt','SĐT'),T('trangThai','Trạng thái')]},
 'pt':{t:'Huấn luyện viên',url:'/pt',id:'maPt',f:[T('maTk','Mã tài khoản','number'),T('cccd','CCCD'),T('hoTen','Họ tên'),T('ngaySinh','Ngày sinh','date'),T('sdt','SĐT'),T('chuyenMon','Chuyên môn'),T('soNamKinhNghiem','Số năm KN','number'),T('luongCoBan','Lương cơ bản','number'),T('trangThai','Trạng thái')]},
 'phong-tap':{t:'Phòng tập',url:'/phong-tap',id:'maPhong',f:[T('tenPhong','Tên phòng'),T('viTri','Vị trí'),T('sucChua','Sức chứa','number'),T('trangThai','Trạng thái','text',['Đang sử dụng','Bảo trì'])]},
 'goi-tap':{t:'Gói tập',url:'/goi-tap',id:'maGoi',f:[T('tenGoi','Tên gói'),T('thoiHanThang','Thời hạn (tháng)','number'),T('giaTien','Giá tiền','number'),T('soBuoiPt','Số buổi PT','number'),T('trangThai','Trạng thái','text',['Active','Inactive'])]},
 'lop-hoc':{t:'Lớp học',url:'/lop-hoc',id:'maLop',f:[T('maPhong','Mã phòng','number'),T('maPt','Mã PT','number'),T('tenLop','Tên lớp'),T('donGiaPt','Đơn giá PT','number'),T('thuHoc','Thứ học (vd 2,4,6)'),T('gioBatDau','Giờ bắt đầu','time'),T('gioKetThuc','Giờ kết thúc','time'),T('ngayBatDau','Ngày bắt đầu','date'),T('ngayKetThuc','Ngày kết thúc','date')]},
 'dang-ky-goi':{t:'Đăng ký gói',url:'/dang-ky-goi',id:'maDk',x:(r,rl)=>r.trangThai==='Đang chờ kích hoạt'&&<><button className="btn s" onClick={()=>act(()=>api('PATCH',`/dang-ky-goi/${r.maDk??r.maDkGoi??r.maDangKy??r.id}/kich-hoat`),'Đã kích hoạt',rl)}>Kích hoạt</button> <button className="btn d s" onClick={()=>act(()=>api('PATCH',`/dang-ky-goi/${r.maDk??r.maDkGoi??r.maDangKy??r.id}/huy`),'Đã hủy',rl)}>Hủy</button></>},
 'buoi-pt':{t:'Buổi PT',url:'/buoi-pt',id:'maBuoiPt',x:(r,rl)=>r.trangThai==='Đã lên lịch'&&<><button className="btn s" onClick={()=>act(()=>api('PUT',`/buoi-pt/${r.maBuoiPt}/trang-thai`,{trangThai:'Đã hoàn thành'}),'Đã cập nhật',rl)}>Hoàn thành</button> <button className="btn g s" onClick={()=>act(()=>api('PUT',`/buoi-pt/${r.maBuoiPt}/trang-thai`,{trangThai:'Vắng mặt'}),'Đã cập nhật',rl)}>Vắng</button></>},
 'check-in-out':{t:'Check-in / out',url:'/check-in-out',id:'id'}}
export const ADMIN_NAV=[['/admin/tai-khoan','Tài khoản'],['/admin/hoi-vien','Hội viên'],['/admin/pt','PT'],['/admin/phong-tap','Phòng tập'],['/admin/goi-tap','Gói tập'],['/admin/lop-hoc','Lớp học'],['/admin/dang-ky-goi','Đăng ký gói'],['/admin/buoi-pt','Buổi PT'],['/admin/check-in-out','Check-in/out']]
export const Home=()=>{const s=useLoad(()=>api('GET','/thong-ke/tong-quan'));const user=JSON.parse(localStorage.getItem('user')||'{}');return <><Banner name={user.name||'bạn'} role="NHAN_VIEN"/><Head t="Bảng điều khiển" s="Theo dõi nhanh các chỉ số vận hành của FitCore" eyebrow="FITCORE ADMIN"/><State s={s}>{d=><><Stats d={d}/><div className="dashboard-grid mt">
        <div className="dashboard-panel visual-panel admin-visual">
          <div>
            <span className="page-eyebrow">CONTROL CENTER</span>
            <h3>Không gian quản trị</h3>
            <p className="sub">Từ hội viên, PT, phòng tập đến gói và lớp học — mọi nghiệp vụ được gom trong một luồng quản lý.</p>
            <div className="quick-actions">
              <a className="quick-action" href="#/admin/hoi-vien"><b>Hội viên</b><span>Quản lý hồ sơ</span></a>
              <a className="quick-action" href="#/admin/pt"><b>PT</b><span>Huấn luyện viên</span></a>
              <a className="quick-action" href="#/admin/lop-hoc"><b>Lớp học</b><span>Lịch & đăng ký</span></a>
            </div>
          </div>
          <div className="admin-scene"><span/><span/><span/><span/><b>FITCORE</b></div>
        </div>
        <div className="dashboard-panel schedule-preview">
          <div className="panel-head"><div><span className="page-eyebrow">NHẮC NHỞ</span><h3>Quản lý hôm nay</h3></div><span className="panel-chip">LIVE</span></div>
          <div className="admin-photo-card">
            <div className="admin-photo-overlay"><span>FITCORE GYM</span><b>Vận hành tập trung.<br/>Không bỏ sót lịch.</b></div>
          </div>
          <div className="upcoming-list">
            <div className="upcoming-item"><span className="event-dot"/><div><b>Kiểm tra trạng thái dữ liệu</b><small>Hội viên · PT · lớp học · gói tập</small></div><em>QUẢN TRỊ</em></div>
            <div className="upcoming-item"><span className="event-dot blue"/><div><b>Kiểm tra lịch hoạt động</b><small>Check-in/out và các buổi PT</small></div><em>VẬN HÀNH</em></div>
          </div>
        </div>
      </div></>}</State></>}
export function Resource(){
  const {res}=useParams()
  if(res==='pt')return <PtResource/>
  const c=RES[res]
  if(!c)return <Head t="Không tìm thấy trang" s=""/>
  return <Inner key={res} c={c}/>
}

function PtResource(){
  const s=useLoad(()=>api('GET','/pt'))
  const now=new Date()
  const [thang,setThang]=useState(String(now.getMonth()+1).padStart(2,'0'))
  const [nam,setNam]=useState(String(now.getFullYear()))
  const [selected,setSelected]=useState(null)
  const [salary,setSalary]=useState(null)
  const [loadingSalary,setLoadingSalary]=useState(false)
  const [salaryError,setSalaryError]=useState(null)

  const xemLuong=async(pt)=>{
    setSelected(pt)
    setSalary(null)
    setSalaryError(null)
    setLoadingSalary(true)
    try{
      const d=await api('GET',`/pt/${pt.maPt}/luong?thang=${Number(thang)}&nam=${Number(nam)}`)
      setSalary(d)
    }catch(e){
      setSalaryError(e)
    }finally{
      setLoadingSalary(false)
    }
  }

  const tinhLai=()=>selected&&xemLuong(selected)

  return <div>
    <Head
      t="Huấn luyện viên"
      s="Quản lý PT và xem tổng lương theo từng tháng"
    />

    {selected&&<div className="cd salary-panel">
      <div className="salary-panel-head">
        <div>
          <span className="page-eyebrow">BẢNG LƯƠNG PT</span>
          <h3 style={{margin:'6px 0 4px'}}>Lương của {selected.hoTen}</h3>
          <p className="sub" style={{margin:0}}>
            Tháng {Number(thang)}/{nam}
          </p>
        </div>
        <button className="btn g s" onClick={()=>{setSelected(null);setSalary(null);setSalaryError(null)}}>Đóng</button>
      </div>

      <div className="row salary-filter">
        <div>
          <label>Tháng</label>
          <select value={thang} onChange={e=>setThang(e.target.value)}>
            {Array.from({length:12},(_,i)=>{
              const m=String(i+1).padStart(2,'0')
              return <option key={m} value={m}>{i+1}</option>
            })}
          </select>
        </div>
        <div>
          <label>Năm</label>
          <input type="number" min="2000" max="2100" value={nam} onChange={e=>setNam(e.target.value)}/>
        </div>
        <div style={{flex:'0 0 auto',minWidth:0}}>
          <button className="btn" onClick={tinhLai} disabled={loadingSalary}>
            {loadingSalary?'Đang tính…':'Xem lương'}
          </button>
        </div>
      </div>

      {salaryError&&<div className="salary-error">{salaryError.message}</div>}

      {loadingSalary&&<div className="salary-loading">Đang tải dữ liệu lương…</div>}

      {salary&&!loadingSalary&&<div className="salary-grid">
        <div className="salary-item">
          <span>Lương cơ bản</span>
          <b>{money(salary.luongCoBan)}</b>
        </div>
        <div className="salary-item">
          <span>Dạy buổi PT hoàn thành</span>
          <b>{money(salary.luongBuoiPt)}</b>
        </div>
        <div className="salary-item">
          <span>Dạy lớp học</span>
          <b>{money(salary.luongLopHoc)}</b>
        </div>
        <div className="salary-item total">
          <span>Tổng lương</span>
          <b>{money(salary.tongLuong)}</b>
        </div>
      </div>}
    </div>}

    <div className="mt">
      <State s={s}>
        {rows=><div className="cd tw">
          <table>
            <thead><tr>
              <th>Mã PT</th>
              <th>Họ tên</th>
              <th>Chuyên môn</th>
              <th>Số năm KN</th>
              <th>Lương cơ bản</th>
              <th>Trạng thái</th>
              <th/>
            </tr></thead>
            <tbody>
              {rows.map(pt=><tr key={pt.maPt}>
                <td>{pt.maPt}</td>
                <td>{pt.hoTen}</td>
                <td>{pt.chuyenMon}</td>
                <td>{pt.soNamKinhNghiem}</td>
                <td>{money(pt.luongCoBan)}</td>
                <td>{pt.trangThai}</td>
                <td style={{whiteSpace:'nowrap'}}>
                  <button className="btn s" onClick={()=>xemLuong(pt)}>
                    Xem lương
                  </button>
                </td>
              </tr>)}
            </tbody>
          </table>
        </div>}
      </State>
    </div>
  </div>
}
function Inner({c}){
  const s=useLoad(()=>api('GET',c.url)),[edit,setEdit]=useState(null)
  const save=o=>act(()=>edit&&edit[c.id]!=null?api('PUT',`${c.url}/${edit[c.id]}`,o):api('POST',c.url,o),'Đã lưu',()=>{setEdit(null);s.reload()})
  return <><Head t={c.t} s={c.f?'Thêm mới hoặc bấm “Sửa” trên từng dòng':'Danh sách'}/>
  {c.f&&(edit?<Form fields={c.f} init={edit} label={edit[c.id]!=null?'Cập nhật':'Tạo mới'} onSubmit={save} onCancel={()=>setEdit(null)}/>:<button className="btn" onClick={()=>setEdit({})}>＋ Thêm mới</button>)}
  <div className="mt"><State s={s}>{d=><Table rows={d} hide={['matKhau']} actions={(c.f||c.x)&&(r=><>{c.f&&<button className="btn g s" onClick={()=>{setEdit(r);scrollTo(0,0)}}>Sửa</button>} {c.x&&c.x(r,s.reload)}</>)}/>}</State></div></>
}
export function ChiSo(){
  const [hv,setHv]=useState('')
  const F=[T('ngayDo','Ngày đo','date'),T('canNang','Cân nặng (kg)','number'),T('chieuCao','Chiều cao (cm)','number'),T('phanTramMo','% mỡ','number'),T('vongEo','Vòng eo (cm)','number'),T('ghiChu','Ghi chú')]
  return <><Head t="Nhập chỉ số cơ thể" s="Nhập cho hội viên theo mã hội viên"/><div style={{maxWidth:200,marginBottom:12}}><label>Mã hội viên</label><input type="number" value={hv} onChange={e=>setHv(e.target.value)}/></div><Form fields={F} label="Lưu chỉ số" onSubmit={o=>hv?act(()=>api('POST','/chi-so-co-the/hoi-vien/'+hv,o),'Đã lưu chỉ số'):act(()=>Promise.reject(new Error('Nhập mã hội viên')))}/></>
}
export function GiaPt(){
  const s=useLoad(()=>api('GET','/cau-hinh-pt'))
  return <><Head t="Cấu hình giá PT" s="Giá cũ chuyển “Ngừng áp dụng”, giá mới “Đang áp dụng”"/><State s={s}>{d=>Array.isArray(d)?<Table rows={d}/>:<Stats d={d}/>}</State><div className="mt"><Form fields={[T('donGiaPt','Đơn giá mới / buổi','number')]} label="Áp dụng" onSubmit={o=>act(()=>api('PUT','/cau-hinh-pt',o),'Đã cập nhật giá',s.reload)}/></div></>
}
