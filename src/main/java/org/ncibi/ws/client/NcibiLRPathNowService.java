package org.ncibi.ws.client;

import java.util.List;

import org.apache.http.NameValuePair;
import org.ncibi.lrpath.LRPathArguments;
import org.ncibi.lrpath.LRPathResult;
import org.ncibi.ws.HttpRequestType;
import org.ncibi.ws.Response;
import org.ncibi.ws.WebServiceProxy;

public class NcibiLRPathNowService
{
    private final BeanXMLWebServiceClient<List<LRPathResult>> wsClient;
    private final WebServiceUrlBuilder wsUrlBuilder = new WebServiceUrlBuilder();

    public NcibiLRPathNowService(HttpRequestType requestType)
    {
        this.wsClient = new BeanXMLWebServiceClient<List<LRPathResult>>(requestType);
    }

    public void setProxy(WebServiceProxy proxy)
    {
        wsClient.setProxy(proxy);
    }

    public Response<List<LRPathResult>> runLRPathNowRequest(LRPathArguments args)
    {
        List<NameValuePair> arguments = LRPathArgsUtil.buildNameValueArgsFromLRPathData(args);
        return executeLRPathNowRequest(arguments);
    }

    private Response<List<LRPathResult>> executeLRPathNowRequest(List<NameValuePair> args)
    {
        String url = wsUrlBuilder.toServiceUrl("ncibi", "lrpathnow");
        return wsClient.executeRequest(url, args);
    }
}
