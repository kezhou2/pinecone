/**
 * 
 */
package com.tenline.pinecone.platform.web.service.restful;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

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
		try {
			record.setTimestamp(new Date());
			record.setValue(URLDecoder.decode(record.getValue(), "utf-8"));
			record.setVariable((Variable) getJdoTemplate().getObjectById(Variable.class, record.getVariable().getId()));
			record = (Record) getJdoTemplate().makePersistent(record);
			record.setValue(URLEncoder.encode(record.getValue(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}

	@Override
	public Record update(Record record) {
		// TODO Auto-generated method stub
		try {
			Record detachedRecord = (Record) getJdoTemplate().getObjectById(Record.class, record.getId());
			detachedRecord.setTimestamp(new Date());
			if (record.getValue() != null) detachedRecord.setValue(URLDecoder.decode(record.getValue(), "utf-8"));
			record = (Record) getJdoTemplate().makePersistent(detachedRecord);
			record.setValue(URLEncoder.encode(record.getValue(), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Record> show(String filter) {
		// TODO Auto-generated method stub
		String queryString = "select from " + Record.class.getName();
		if (!filter.equals("all")) queryString += " where " + filter;
		Collection<Record> records = getJdoTemplate().find(queryString);
		for (Iterator<Record> iterator = records.iterator(); iterator.hasNext();) {
			Record record = iterator.next();
			try {
				record.setValue(URLEncoder.encode(record.getValue(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return records;
	}

	@Override
	public Collection<Record> showByVariable(String filter) {
		// TODO Auto-generated method stub
		Variable variable = getJdoTemplate().getObjectById(Variable.class, filter.substring(filter.indexOf("'") + 1, filter.lastIndexOf("'")));
		Collection<Record> records = variable.getRecords();
		for (Iterator<Record> iterator = records.iterator(); iterator.hasNext();) {
			Record record = iterator.next();
			try {
				record.setValue(URLEncoder.encode(record.getValue(), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return records;
	}

}
