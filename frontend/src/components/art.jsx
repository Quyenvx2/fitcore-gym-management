// Minh họa vector tối giản dùng cho các trạng thái/visual phụ của FitCore
export const Pulse=({w=220,className=''})=><svg className={className} width={w} viewBox="0 0 220 50" fill="none"><path className="pl" d="M0 28H60L72 6L88 46L100 28H220" stroke="var(--ac)" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round"/></svg>
export const classIcon=n=>{
  n=(n||'').toLowerCase()
  if(/yoga|thiền|giãn cơ|stretch/.test(n)) return '/img/icons/fitness-gym.png'
  if(/pilates/.test(n)) return '/img/icons/pilates.png'
  if(/box|võ|muay|boxing/.test(n)) return '/img/icons/muscle.png'
  if(/hiit|cardio|burn|gym|strength|tạ/.test(n)) return '/img/icons/fitness.png'
  if(/zumba|dance|nhảy/.test(n)) return '/img/icons/fitness-gym.png'
  if(/cycl|đạp|spin/.test(n)) return '/img/icons/fitness.png'
  if(/swim|bơi/.test(n)) return '/img/icons/wellness.png'
  if(/giảm cân|slim|weight/.test(n)) return '/img/icons/slimming.png'
  return '/img/icons/fitness.png'
}
export const ptIcon='/img/icons/personal-trainer.png'
export const metricIcon='/img/icons/wellness.png'
export const reportIcon='/img/icons/report.png'

const Q=['Mỗi buổi tập là một khoản đầu tư cho chính bạn.','Không cần hoàn hảo, chỉ cần bắt đầu.','Cơ thể bạn làm được, hãy thuyết phục tâm trí.','Kỷ luật hôm nay, tự do ngày mai.']
export function Banner({name,role}){
  const h=new Date().getHours(),g=h<11?'Chào buổi sáng':h<18?'Chào buổi chiều':'Chào buổi tối'
  const label=role==='NHAN_VIEN'?'KHU VỰC QUẢN TRỊ':role==='PT'?'HUẤN LUYỆN VIÊN':'HỘI VIÊN FITCORE'
  return <div className="banner dashboard-banner">
    <div className="banner-image-layer" aria-hidden="true"/>
    <div className="banner-copy">
      <small>{label}</small>
      <h2>{g}, <em>{name}</em></h2>
      <p>{Q[new Date().getDate()%Q.length]}</p>
      <Pulse w={190}/>
      <div className="banner-meta"><span>● Hệ thống đang hoạt động</span><span>FITCORE 2026</span></div>
    </div>
  </div>
}
