package com.fitcore.backend;

import com.fitcore.backend.dto.TaiKhoanDTO;

import com.fitcore.backend.service.TaiKhoanService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner testDatabase(TaiKhoanService service) {
        return args -> {

            List<TaiKhoanDTO> danhSach = service.layDanhSachTaiKhoan();

            System.out.println("=================================");
            System.out.println("KET NOI DATABASE THANH CONG");
            System.out.println("So tai khoan: " + danhSach.size());

            for (TaiKhoanDTO tk : danhSach) {
                System.out.println(
                    tk.getMaTk() + " - " +
                    tk.getTenDangNhap() + " - " +
                    tk.getVaiTro()
                );
            }

            System.out.println("=================================");
        };
    }
}