/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenline.pinecone.platform.model.Record;

/**
 * @author Bill
 *
 */
public interface RecordRepository extends JpaRepository<Record, Long> {}
