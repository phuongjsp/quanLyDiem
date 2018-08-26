package doan.zera.jsp.repositories;

import doan.zera.jsp.model.Kyhoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KyhocRepository extends JpaRepository<Kyhoc, Integer> {
    Kyhoc findByMaKyHoc(String maKyHoc);
}
