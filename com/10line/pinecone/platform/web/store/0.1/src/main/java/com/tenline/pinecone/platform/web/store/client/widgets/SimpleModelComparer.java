/**
 * 
 */
package com.tenline.pinecone.platform.web.store.client.widgets;

import com.extjs.gxt.ui.client.data.ModelComparer;
import com.extjs.gxt.ui.client.data.ModelData;

/**
 * @author Bill
 *
 */
public class SimpleModelComparer<M extends ModelData> implements ModelComparer<M> {

	/**
	 * 
	 */
	public SimpleModelComparer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean equals(M a, M b) {
		// TODO Auto-generated method stub
		return (a != null && a.get("id").equals(b.get("id")));
	}

}
