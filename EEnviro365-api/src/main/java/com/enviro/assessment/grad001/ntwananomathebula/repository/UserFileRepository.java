package com.enviro.assessment.grad001.ntwananomathebula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enviro.assessment.grad001.ntwananomathebula.entity.UserFile;

/**
 * Repository interface for File entities.
 */
@Repository
public interface UserFileRepository extends JpaRepository<UserFile, Long> {

	/**
     * Check if a file with the given name exists.
     * 
     * @return true if a file with the given name exists, false otherwise
     */
    boolean existsByFileName(String fileName);
}

    