package doan.zera.jsp.repositories;

import doan.zera.jsp.model.Khoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhoaRepository extends JpaRepository<Khoa, Integer> {
    Khoa findByMaKhoa(String maKhoa);
}
