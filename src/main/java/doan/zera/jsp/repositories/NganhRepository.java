package doan.zera.jsp.repositories;

import doan.zera.jsp.model.Khoa;
import doan.zera.jsp.model.Nganh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NganhRepository extends JpaRepository<Nganh, Integer> {
    long countAllByKhoa(Khoa khoa);

    List<Nganh> findAllByKhoa(Khoa khoa);

    Nganh findByMaNganh(String maNganh);
}
