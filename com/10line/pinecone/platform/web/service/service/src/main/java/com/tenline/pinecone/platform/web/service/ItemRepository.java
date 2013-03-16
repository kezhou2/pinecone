/**
 * 
 */
package com.tenline.pinecone.platform.web.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenline.pinecone.platform.model.Item;

/**
 * @author Bill
 *
 */
public interface ItemRepository extends JpaRepository<Item, Long> {}
