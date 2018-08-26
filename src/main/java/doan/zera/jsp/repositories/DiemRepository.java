package doan.zera.jsp.repositories;

import doan.zera.jsp.model.Diem;
import doan.zera.jsp.model.SinhVien;
import doan.zera.jsp.model.ThoiKhoaBieu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiemRepository extends JpaRepository<Diem, Integer> {
    @Query(nativeQuery = true, value = "select d.thoi_khoa_bieu_id from diem d join thoi_khoa_bieu tkb on d.thoi_khoa_bieu_id = tkb.id where tkb.ky_hoc_id=?1 and d.sinhvien_id=?2")
    List<Integer> findAllIdThoiKhoaBieuByIdKyHocAndIdSinhVien(int idKyHoc, int idSinhVien);

    @Query(nativeQuery = true, value = "select distinct tkb.id from thoi_khoa_bieu tkb join mon_hoc mh on tkb.monhoc_id = mh.id where tkb.ky_hoc_id=?1 and mh.nganh_id=?2 and tkb.id not in ?3 ")
    List<Integer> findAllIdThoiKhoaBieuByIdKyHocAndIdSinhVienNotInAndNganh(int idKyHoc, int idNganh, List<Integer> listTKB);

    @Query(nativeQuery = true, value = "select distinct tkb.id from thoi_khoa_bieu tkb join mon_hoc mh on tkb.monhoc_id = mh.id where tkb.ky_hoc_id=?1 and mh.nganh_id=?2 ")
    List<Integer> findAllIdThoiKhoaBieuByIdKyHocAndIdSinhVienNotInAndNganh(int idKyHoc, int idNganh);

    long countAllBySinhVien(SinhVien sinhVien);

    List<Diem> findAllBySinhVien(SinhVien sinhVien, Pageable pageable);

    @Query(nativeQuery = true, value = "select tkb.monhoc_id from diem d join thoi_khoa_bieu tkb on d.thoi_khoa_bieu_id = tkb.id where tkb.ky_hoc_id=?1 and d.sinhvien_id=?2")
    List<Integer> findIdMonHocByIdKyHocAndIdSv(int idKyHoc, int idSv);

    long countAllByThoiKhoaBieu(ThoiKhoaBieu thoiKhoaBieu);

    List<Diem> findAllByThoiKhoaBieu(ThoiKhoaBieu thoiKhoaBieu, Pageable pageable);

    long countAllBySinhVienAndThoiKhoaBieu(SinhVien sinhVien, ThoiKhoaBieu thoiKhoaBieu);

    List<Diem> findAllBySinhVienAndThoiKhoaBieu(SinhVien sinhVien, ThoiKhoaBieu thoiKhoaBieu, Pageable pageable);

    @Query(nativeQuery = true, value = "select d.sinhvien_id from diem d where d.thoi_khoa_bieu_id=?1")
    List<Integer> findAllSinhVienByIdTKB(int idTKB);

    @Query(nativeQuery = true, value = "select d.* from diem d where d.sinhvien_id=?1 and d.thoi_khoa_bieu_id=?2")
    Diem findByIdSvAndIdTKB(int idSv, int idTkb);

    @Query(nativeQuery = true, value = "select count(d.id)from diem d join thoi_khoa_bieu tkb on d.thoi_khoa_bieu_id = tkb.id where tkb.ky_hoc_id=?1 and (((((d.chuyen_can +d.diem_kiem_tra*2))/3)*30 + (d.diem_thi*70))/100) between ?2 and ?3")
    long countAllByKyHoc(int idKyHoc, double min, double max);

    @Query(nativeQuery = true, value = "select count(d.id)from diem d join thoi_khoa_bieu tkb on d.thoi_khoa_bieu_id = tkb.id where tkb.ky_hoc_id=?1 and tkb.monhoc_id=?2  and (((((d.chuyen_can +d.diem_kiem_tra*2))/3)*30 + (d.diem_thi*70))/100) between ?3 and ?4")
    long countAllByKyHocAndMonHoc(int idKyHoc, int idMonHoc, double min, double max);

    @Query(nativeQuery = true, value = "select count(d.id)from diem d join thoi_khoa_bieu tkb on d.thoi_khoa_bieu_id = tkb.id join mon_hoc mh on tkb.monhoc_id = mh.id join nganh n on mh.nganh_id = n.id where tkb.ky_hoc_id=?1 and n.khoa_id =?2  and (((((d.chuyen_can +d.diem_kiem_tra*2))/3)*30 + (d.diem_thi*70))/100) between ?3 and ?4")
    long countAllByKyHocAndKhoa(int idKyHoc, int idKhoa, double min, double max);

    @Query(nativeQuery = true, value = "select count(d.id)from diem d join thoi_khoa_bieu tkb on d.thoi_khoa_bieu_id = tkb.id join mon_hoc mh on tkb.monhoc_id = mh.id where tkb.ky_hoc_id=?1 and mh.nganh_id =?2  and (((((d.chuyen_can +d.diem_kiem_tra*2))/3)*30 + (d.diem_thi*70))/100) between ?3 and ?4")
    long countAllByKyHocAndNganh(int idKyHoc, int idKhoa, double min, double max);

    @Query(nativeQuery = true, value = "select count(d.id)from diem d join thoi_khoa_bieu tkb on d.thoi_khoa_bieu_id = tkb.id join sinh_vien v on d.sinhvien_id = v.id where tkb.ky_hoc_id=?1 and  v.khoa_hoc_id=?2 and (((((d.chuyen_can +d.diem_kiem_tra*2))/3)*30 + (d.diem_thi*70))/100) between ?3 and ?4")
    long countAllByKyHocAndKhoaHoc(int idKyHoc, int idKhoaHoc, double min, double max);
}
