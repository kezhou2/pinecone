/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.util.Collection;
import java.util.Date;

import javax.jdo.PersistenceManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jdo.support.JdoDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenline.pinecone.platform.model.Record;
import com.tenline.pinecone.platform.model.Variable;
import com.tenline.pinecone.platform.web.service.RecordService;

/**
 * @author Bill
 *
 */
@Service
@Transactional
public class RecordRestfulService extends JdoDaoSupport implements RecordService {

	/**
	 * 
	 */
	@Autowired
	public RecordRestfulService(PersistenceManagerFactory persistenceManagerFactory) {
		// TODO Auto-generated constructor stub
		setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Response delete(String id) {
		// TODO Auto-generated method stub
		getJdoTemplate().deletePersistent(getJdoTemplate().getObjectById(Record.class, id));
		return Response.status(Status.OK).build();
	}

	@Override
	public Record create(Record record) {
		// TODO Auto-generated method stub
		record.setTimestamp(new Date());
		record.setVariable((Variable) getJdoTemplate().getObjectById(Variable.class, record.getVariable().getId()));
		return getJdoTemplate().makePersistent(record);
	}

	@Override
	public Record update(Record record) {
		// TODO Auto-generated method stub
		Record detachedRecord = (Record) getJdoTemplate().getObjectById(Record.class, record.getId());
		detachedRecord.setTimestamp(new Date());
		if (record.getValue() != null) detachedRecord.setValue(record.getValue());
		return getJdoTemplate().makePersistent(detachedRecord);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Record> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Record.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		return getJdoTemplate().find(queryString);
	}

	@Override
	public Collection<Record> showByVariable(String filter) {
		// TODO Auto-generated method stub
		return getJdoTemplate().getObjectById(Variable.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'"))).getRecords();
	}

}
