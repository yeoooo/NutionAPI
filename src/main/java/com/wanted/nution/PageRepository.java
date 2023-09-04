package com.wanted.nution;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PageRepository {
    int save(Pages page) throws SQLException;

    Optional<Pages> findById(Long id);

    List<PageSummary> findSubPagesById(Long id);

//    List<SubPages> findParentPagesById(Long id);

    PageSummary findParentById(Long id);


}
