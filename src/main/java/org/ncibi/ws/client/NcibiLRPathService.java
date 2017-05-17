package org.ncibi.ws.client;

import java.util.List;

import org.apache.http.NameValuePair;
import org.ncibi.lrpath.LRPathArguments;
import org.ncibi.lrpath.LRPathResult;
import org.ncibi.ws.lrpath.*;
import org.ncibi.ws.util.*;
import org.ncibi.ws.HttpRequestType;
import org.ncibi.ws.Response;
import org.ncibi.ws.WebServiceProxy;
import org.ncibi.ws.request.RequestStatus;

public class NcibiLRPathService
{
    private final BeanXMLWebServiceClient<String> wsClient;
    private final WebServiceUrlBuilder wsUrlBuilder = new WebServiceUrlBuilder();
    private final BeanXMLWebServiceClient<RequestStatus<List<LRPathResult>>> wsStatusClient;


    public NcibiLRPathService(HttpRequestType requestType)
    {
        this.wsClient = new BeanXMLWebServiceClient<String>(requestType);
        this.wsStatusClient = new BeanXMLWebServiceClient<RequestStatus<List<LRPathResult>>>(
                    requestType);
    }
    
    public void setProxy(WebServiceProxy proxy)
    {
        wsClient.setProxy(proxy);
        wsStatusClient.setProxy(proxy);
    }

    public Response<String> submitLRPathRequest(LRPathArguments args)
    {
        List<NameValuePair> arguments = LRPathArgsUtil.buildNameValueArgsFromLRPathData(args);
        return executeLRPathRequest(arguments);
    }

    public Response<RequestStatus<List<LRPathResult>>> lrpathStatus(String uuid)
    {
        return executeStatusRequest(uuid);
    }

    private Response<String> executeLRPathRequest(List<NameValuePair> args)
    {
        String url = wsUrlBuilder.toServiceUrl("ncibi", "lrpath");
        return wsClient.executeRequest(url, args);
    }

    private Response<RequestStatus<List<LRPathResult>>> executeStatusRequest(String uuid)
    {
        return wsStatusClient.executeRequest(wsUrlBuilder.toServiceUrl("ncibi", "status") + "/"
                    + uuid + "/lrpath");
    }
}
