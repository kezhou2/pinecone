/**
 * 
 */
package com.tenline.pinecone.service;

import javax.jdo.PersistenceManagerFactory;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.orm.jdo.JdoTemplate;

/**
 * @author Bill
 *
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractServiceTest {

	@Mock
	protected PersistenceManagerFactory persistenceManagerFactory;
	
	@Mock
	protected JdoTemplate jdoTemplate;

}
