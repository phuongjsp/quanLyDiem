package doan.zera.jsp.repositories;

import doan.zera.jsp.model.MonHoc;
import doan.zera.jsp.model.Nganh;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonHocRepository extends JpaRepository<MonHoc, Integer> {
    long countAllByNganh(Nganh nganh);

    MonHoc findByMaMonHoc(String maMonHoc);

    List<MonHoc> findAllByNganh(Nganh nganh, Pageable pageable);

    List<MonHoc> findAllByNganh(Nganh nganh);

    List<MonHoc> findAllByNganhAndIdNotIn(Nganh nganh, List<Integer> ids);
}
