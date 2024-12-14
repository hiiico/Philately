package com.philately.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.philately.model.entity.Paper;
import com.philately.model.entity.enums.PaperEnum;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {

    Paper findByPaperEnum(PaperEnum paperEnum);
}
