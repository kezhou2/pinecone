package com.tenline.pinecone.platform.web.store.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.tenline.pinecone.platform.web.store.client.model.AppInfo;
import com.tenline.pinecone.platform.web.store.client.model.ConsumerInfo;
import com.tenline.pinecone.platform.web.store.client.model.DeviceInfo;
import com.tenline.pinecone.platform.web.store.client.model.UserInfo;

public interface PineconeServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void login( java.lang.String p0, java.lang.String p1, AsyncCallback<com.tenline.pinecone.platform.model.User> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void isUserExist( com.tenline.pinecone.platform.model.User p0, AsyncCallback<java.lang.Boolean> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void logout( java.lang.String p0, AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void getUserRelation( com.tenline.pinecone.platform.web.store.client.model.UserInfo p0, AsyncCallback<List<UserInfo>> asyncCallback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void getConsumerInfo( com.tenline.pinecone.platform.web.store.client.model.UserInfo p0, AsyncCallback<java.util.List> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void getAppInfo( com.tenline.pinecone.platform.web.store.client.model.UserInfo p0, AsyncCallback<List<AppInfo>> asyncCallback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void getAllConsumerInfo( AsyncCallback<List<ConsumerInfo>> asyncCallback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void addConsumerToUser( com.tenline.pinecone.platform.web.store.client.model.UserInfo p0, com.tenline.pinecone.platform.web.store.client.model.ConsumerInfo p1, AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void deleteAppInfo( com.tenline.pinecone.platform.web.store.client.model.AppInfo p0, AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void getConsumerById( java.lang.String p0, AsyncCallback<com.tenline.pinecone.platform.web.store.client.model.ConsumerInfo> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void getAllDevice( com.tenline.pinecone.platform.web.store.client.model.UserInfo p0, AsyncCallback<List<DeviceInfo>> asyncCallback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.tenline.pinecone.platform.web.store.client.PineconeService
     */
    void register( com.tenline.pinecone.platform.model.User p0, AsyncCallback<com.tenline.pinecone.platform.model.User> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static PineconeServiceAsync instance;

        public static final PineconeServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (PineconeServiceAsync) GWT.create( PineconeService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "PineconeService" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
