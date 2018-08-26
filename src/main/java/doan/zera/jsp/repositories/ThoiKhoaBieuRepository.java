package doan.zera.jsp.repositories;

import doan.zera.jsp.model.GiaoVien;
import doan.zera.jsp.model.Kyhoc;
import doan.zera.jsp.model.MonHoc;
import doan.zera.jsp.model.ThoiKhoaBieu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ThoiKhoaBieuRepository extends JpaRepository<ThoiKhoaBieu, Integer> {
    long countAllByKyhoc(Kyhoc kyhoc);

    @Query(nativeQuery = true, value = "select t.tin_chi from thoi_khoa_bieu t where t.monhoc_id=?1 and t.ky_hoc_id=?2")
    Integer findTinChiByMonHocAndHocKy(int idMonHoc, int idKyHoc);

    List<ThoiKhoaBieu> findAllByKyhoc(Kyhoc kyhoc, Pageable pageable);

    @Query(nativeQuery = true, value = "select t.* from thoi_khoa_bieu t where t.ky_hoc_id=?1")
    List<ThoiKhoaBieu> findAllByIdKyhoc(int idKyhoc);

    @Query(nativeQuery = true, value = "select t.* from thoi_khoa_bieu t where t.monhoc_id=?1")
    List<ThoiKhoaBieu> findAllByIdMonHoc(int idMonHoc);

    @Query(nativeQuery = true, value = "select t.* from thoi_khoa_bieu t where t.ky_hoc_id=?1 and t.monhoc_id=?2")
    List<ThoiKhoaBieu> findAllByIdKyhocAndIdMonHoc(int idKyhoc, int idMonHoc);

    List<ThoiKhoaBieu> findAllByKyhoc(Kyhoc kyhoc);

    List<ThoiKhoaBieu> findAllByMonHoc(MonHoc monHoc);

    List<ThoiKhoaBieu> findAllByGiaoVien(GiaoVien giaoVien);

    List<ThoiKhoaBieu> findAllByKyhocAndMonHoc(Kyhoc kyhoc, MonHoc monHoc);

    @Query(nativeQuery = true, value = "select distinct t.monhoc_id from thoi_khoa_bieu t where t.ky_hoc_id=?1 and t.giaovien_id=?2 ")
    List<Integer> findAllIdByKyhocAndIdGiaoVien(int idKyhoc, int idGiaoVien);

    List<ThoiKhoaBieu> findAllByMonHocAndGiaoVien(MonHoc monHoc, GiaoVien giaoVien);

    @Query(nativeQuery = true, value = "select t.* from thoi_khoa_bieu t where t.ky_hoc_id=?1 and t.giaovien_id=?2 and t.monhoc_id=?3")
    List<ThoiKhoaBieu> findAllByKyhocAndGiaoVienAndMonHoc(int idkyhoc, int idgiaoVien, int idmonHoc);

    @Modifying
    @Query("select t.phongHoc from ThoiKhoaBieu t where t.kyhoc=?1 and t.soTiet=?2 and t.tietBatDau=?3 and t.thoiGianBatDau=?4")
    List<String> findAllPhongHocInKyHoc(Kyhoc kyhoc, int soTiet, int tietBatDau, Date thoiGianBatDau);

    @Query("select distinct t.monHoc from ThoiKhoaBieu t where t.kyhoc=?1")
    List<MonHoc> findAllIdMonHoc(Kyhoc kyHoc);


    @Query("select distinct t.monHoc from ThoiKhoaBieu t")
    List<MonHoc> findAllMonHoc();
}
