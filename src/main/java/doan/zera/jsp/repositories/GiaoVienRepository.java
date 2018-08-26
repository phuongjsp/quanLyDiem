package doan.zera.jsp.repositories;

import doan.zera.jsp.model.GiaoVien;
import doan.zera.jsp.model.Nganh;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiaoVienRepository extends JpaRepository<GiaoVien, Integer> {
    long countAllByNganh(Nganh nganh);

    List<GiaoVien> findAllByNganh(Nganh nganh, Pageable pageable);

    List<GiaoVien> findAllByNganh(Nganh nganh);

    List<GiaoVien> findAllByNganhAndIdNotIn(Nganh nganh, List<Integer> ids);

    GiaoVien findByMaGv(String maGv);

    @Query(nativeQuery = true, value = "select g.* from giao_vien g where g.user_id = ?1")
    GiaoVien findByUserId(int idUser);
}
