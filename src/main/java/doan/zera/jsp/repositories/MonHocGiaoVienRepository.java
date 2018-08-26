package doan.zera.jsp.repositories;

import doan.zera.jsp.model.GiaoVien;
import doan.zera.jsp.model.MonHoc;
import doan.zera.jsp.model.MonHocGiaoVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MonHocGiaoVienRepository extends JpaRepository<MonHocGiaoVien, Integer> {
    @Query("select m.giaoVien from MonHocGiaoVien m where m.monHoc=?1")
    List<GiaoVien> findGiaoVienByMonHoc(MonHoc monHoc);

    @Query("select m.monHoc from MonHocGiaoVien m where m.giaoVien=?1")
    List<MonHoc> findMonHocByGiaoVien(GiaoVien giaoVien);

    long countAllByMonHoc(MonHoc monHoc);

    long countAllByGiaoVien(GiaoVien giaoVien);

    @Transactional
    long deleteByMonHocAndGiaoVien(MonHoc monHoc, GiaoVien giaoVien);

    @Transactional
    long deleteByMonHoc(MonHoc monHoc);

    @Transactional
    long deleteByGiaoVien(GiaoVien giaoVien);
}
