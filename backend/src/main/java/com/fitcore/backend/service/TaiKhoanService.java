package com.fitcore.backend.service;

import com.fitcore.backend.dto.TaiKhoanLoginRequestDTO;
import com.fitcore.backend.dto.TaiKhoanRequestDTO;
import com.fitcore.backend.dto.TaiKhoanDTO;
import com.fitcore.backend.entity.TaiKhoan;
import com.fitcore.backend.repository.TaiKhoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Service
public class TaiKhoanService {

    private final TaiKhoanRepository repository;
    private final PasswordEncoder passwordEncoder;
    public TaiKhoanService(TaiKhoanRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder=passwordEncoder;
    }

    public List<TaiKhoanDTO> layDanhSachTaiKhoan() {
        List <TaiKhoan> danhSach= repository.findAll();

        return danhSach.stream()
                .map(tk -> new TaiKhoanDTO(
                        tk.getMaTk(),
                        tk.getTenDangNhap(),
                        tk.getVaiTro(),
                        tk.getTrangThai()
                ))
                .toList();
    }
    public TaiKhoanDTO layTaiKhoanTheoId (Integer id){
        TaiKhoan tk = repository.findById(id).orElse(null);
        if(tk==null) return null;
        return new TaiKhoanDTO(tk.getMaTk(),
            tk.getTenDangNhap(),
            tk.getVaiTro(),
             tk.getTrangThai());
    }           
    public TaiKhoanDTO taoTaiKhoan(TaiKhoanRequestDTO request){
            TaiKhoan tk = new TaiKhoan();
            tk.setTenDangNhap(request.getTenDangNhap());
            tk.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
            tk.setVaiTro(request.getVaiTro());
            tk.setTrangThai(request.getTrangThai());

            TaiKhoan taiKhoanDaLuu = repository.save(tk);

            return new TaiKhoanDTO(
            taiKhoanDaLuu.getMaTk(),
            taiKhoanDaLuu.getTenDangNhap(),
            taiKhoanDaLuu.getVaiTro(),
            taiKhoanDaLuu.getTrangThai());
    }
    public TaiKhoanDTO capNhatTaiKhoan(Integer id, TaiKhoanRequestDTO request) {

    TaiKhoan tk = repository.findById(id).orElse(null);

    if (tk == null) {
        return null;
    }

    tk.setTenDangNhap(request.getTenDangNhap());
    tk.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
    tk.setVaiTro(request.getVaiTro());
    tk.setTrangThai(request.getTrangThai());

    TaiKhoan taiKhoanDaLuu = repository.save(tk);

    return new TaiKhoanDTO(
            taiKhoanDaLuu.getMaTk(),
            taiKhoanDaLuu.getTenDangNhap(),
            taiKhoanDaLuu.getVaiTro(),
            taiKhoanDaLuu.getTrangThai());
    }
    public void xoaTaiKhoan (Integer id){
        repository.deleteById(id);
    }

    public TaiKhoanDTO dangNhap(TaiKhoanLoginRequestDTO request){
        TaiKhoan tk = repository.findByTenDangNhap(request.getTenDangNhap()).orElse(null);
        if(tk==null){
            return null;
        }
        if(!tk.getTrangThai().equals("Active")) return null;

        boolean matKhauDung = passwordEncoder.matches(request.getMatKhau(), tk.getMatKhau());

        if(!matKhauDung) return null;

        return new TaiKhoanDTO(tk.getMaTk(),tk.getTenDangNhap(),tk.getVaiTro(),tk.getTrangThai());
    }
}