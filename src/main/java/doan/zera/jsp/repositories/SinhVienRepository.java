package doan.zera.jsp.repositories;

import doan.zera.jsp.model.KhoaHoc;
import doan.zera.jsp.model.Nganh;
import doan.zera.jsp.model.SinhVien;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, Integer> {
    long countAllByKhoaHocAndNganh(KhoaHoc khoaHoc, Nganh nganh);

    List<SinhVien> findAllByKhoaHocAndNganh(KhoaHoc k, Nganh nganh, Pageable pageable);

    long countAllByKhoaHoc(KhoaHoc khoaHoc);

    List<SinhVien> findAllByKhoaHoc(KhoaHoc khoaHoc, Pageable pageable);

    long countAllByNganh(Nganh nganh);

    List<SinhVien> findAllByNganh(Nganh nganh, Pageable pageable);

    List<SinhVien> findAllByNganh(Nganh nganh);

    List<SinhVien> findAllByIdNotInAndNganh(List<Integer> idSv, Nganh nganh);

    List<SinhVien> findAllByIdNotInAndKhoaHocAndNganh(List<Integer> idSv, KhoaHoc khoaHoc, Nganh nganh);

    List<SinhVien> findAllByKhoaHocAndNganh(KhoaHoc khoaHoc, Nganh nganh);
}
