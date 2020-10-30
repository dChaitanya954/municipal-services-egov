package org.egov.pt.repository;

import org.egov.pt.models.excel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserExcelRepository extends JpaRepository<User, Long> {



}

