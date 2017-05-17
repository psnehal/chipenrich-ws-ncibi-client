package org.ncibi.ws.client;

import java.util.List;

import org.apache.http.NameValuePair;
import org.ncibi.lrpath.LRPathResult;
import org.ncibi.ws.HttpRequestType;
import org.ncibi.ws.Response;
import org.ncibi.ws.WebServiceProxy;
import org.ncibi.ws.request.RequestStatus;
import org.ncibi.ws.thinkback.LRThinkArgs;
import org.ncibi.ws.util.NameValuePairUtil;

public class NcibiLrpathThinkService
{
    private final BeanXMLWebServiceClient<String> wsClient = new BeanXMLWebServiceClient<String>(
            HttpRequestType.POST);
    private final WebServiceUrlBuilder wsUrlBuilder = new WebServiceUrlBuilder();
    private final BeanXMLWebServiceClient<RequestStatus<List<LRPathResult>>> wsStatusClient = new BeanXMLWebServiceClient<RequestStatus<List<LRPathResult>>>(
            HttpRequestType.POST);

    public void setProxy(WebServiceProxy proxy)
    {
        wsClient.setProxy(proxy);
        wsStatusClient.setProxy(proxy);
    }

    public Response<String> submitLrpathThinkRequest(LRThinkArgs a)
    {
        List<NameValuePair> args = LRPathArgsUtil.buildNameValueArgsFromLRPathData(a.getLrpathArgs());
        args.add(NameValuePairUtil.buildNameValuePairFromNameValue("adjustmentMethod", a
                .getAdjustmentMethod().toString()));
        args.add(NameValuePairUtil.buildNameValuePairFromNameValue("pathways", a.getPathways()));
        return executeRequest(args);
    }

    public Response<RequestStatus<List<LRPathResult>>> thinkbackStatus(String uuid)
    {
        return executeStatusRequest(uuid);
    }

    private Response<String> executeRequest(List<NameValuePair> args)
    {
        String url = wsUrlBuilder.toServiceUrl("ncibi", "lrpath-think");
        return wsClient.executeRequest(url, args);
    }

    private Response<RequestStatus<List<LRPathResult>>> executeStatusRequest(String uuid)
    {
        return wsStatusClient.executeRequest(wsUrlBuilder.toServiceUrl("ncibi", "status") + "/" + uuid
                + "/xml");
    }

}
