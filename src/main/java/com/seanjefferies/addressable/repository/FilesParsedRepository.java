package com.seanjefferies.addressable.repository;

import com.seanjefferies.addressable.model.FilesParsed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesParsedRepository extends JpaRepository<FilesParsed, String> {
}
