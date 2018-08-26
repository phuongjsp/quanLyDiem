package doan.zera.jsp.repositories;

import doan.zera.jsp.model.KhoaHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhoaHocRepository extends JpaRepository<KhoaHoc, Integer> {
    KhoaHoc findByMaKhoa(String maKhoa);
}
