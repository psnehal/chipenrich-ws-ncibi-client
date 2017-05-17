package org.ncibi.ws.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.ncibi.lrpath.LRPathResult;
import org.ncibi.ws.HttpRequestType;
import org.ncibi.ws.Response;
import org.ncibi.ws.WebServiceProxy;
import org.ncibi.ws.request.RequestStatus;
import org.ncibi.ws.thinkback.ThinkbackAdjustmentMethod;
import org.ncibi.ws.thinkback.ThinkbackEnrichmentMethod;
import org.ncibi.ws.util.NameValuePairUtil;

public class NcibiGseaThinkService
{
    private final BeanXMLWebServiceClient<String> wsClient = new BeanXMLWebServiceClient<String>(
            HttpRequestType.MULTI_PART_POST);
    private final WebServiceUrlBuilder wsUrlBuilder = new WebServiceUrlBuilder();
    private final BeanXMLWebServiceClient<RequestStatus<List<LRPathResult>>> wsStatusClient = new BeanXMLWebServiceClient<RequestStatus<List<LRPathResult>>>(
            HttpRequestType.POST);

    public void setProxy(WebServiceProxy proxy)
    {
        wsClient.setProxy(proxy);
        wsStatusClient.setProxy(proxy);
    }

    public Response<String> submitThinkBackRequest(ThinkbackEnrichmentMethod enrichMethod,
            ThinkbackAdjustmentMethod adjustMethod, List<String> pathways, String datasetFilePath,
            String clsFilePath, String templateFilePath, String chipsetFilePath)
    {
        List<NameValuePair> args = new ArrayList<NameValuePair>();
        addToArgs(args, "enrichmentMethod", enrichMethod.toString());
        addToArgs(args, "adjustmentMethod", adjustMethod.toString());
        args.add(NameValuePairUtil.buildNameValuePairFromCollection("pathways", pathways));

        /**
         * TODO should send files, not file names
         */
        addToArgs(args, "file:template", templateFilePath);
        addToArgs(args, "file:dataset", datasetFilePath);
        addToArgs(args, "file:cls", clsFilePath);
        addToArgs(args, "file:chipset", chipsetFilePath);
        return executeRequest(args);
    }

    public Response<RequestStatus<List<LRPathResult>>> thinkbackStatus(String uuid)
    {
        return executeStatusRequest(uuid);
    }

    private void addToArgs(List<NameValuePair> args, String name, String value)
    {
        if (value != null)
        {
            args.add(NameValuePairUtil.buildNameValuePairFromNameValue(name, value));
        }
    }

    private Response<String> executeRequest(List<NameValuePair> args)
    {
        String url = wsUrlBuilder.toServiceUrl("ncibi", "gsea-think");
        return wsClient.executeRequest(url, args);
    }

    private Response<RequestStatus<List<LRPathResult>>> executeStatusRequest(String uuid)
    {
        return wsStatusClient.executeRequest(wsUrlBuilder.toServiceUrl("ncibi", "status") + "/" + uuid
                + "/xml");
    }
}
