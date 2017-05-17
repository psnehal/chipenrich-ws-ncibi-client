package org.ncibi.ws.client;

import java.util.List;

import org.apache.http.NameValuePair;




import org.ncibi.chipenrich.ChipEnrichInputArguments;
import org.ncibi.chipenrich.ChipEnrichResults;

import org.ncibi.ws.HttpRequestType;
import org.ncibi.ws.Response;
import org.ncibi.ws.WebServiceProxy;
import org.ncibi.ws.request.RequestStatus;

public class NcibiChipEnrichService
{
    private final BeanXMLWebServiceClient<String> wsClient;
    private final WebServiceUrlBuilder wsUrlBuilder = new WebServiceUrlBuilder();
    private final BeanXMLWebServiceClient<RequestStatus<List<ChipEnrichResults>>> wsStatusClient;


    public NcibiChipEnrichService(HttpRequestType requestType)
    {
    	
        this.wsClient = new BeanXMLWebServiceClient<String>(requestType);
        this.wsStatusClient = new BeanXMLWebServiceClient<RequestStatus<List<ChipEnrichResults>>>(
                    requestType);
        
        
    }
    
    public void setProxy(WebServiceProxy proxy)
    {
        wsClient.setProxy(proxy);
        wsStatusClient.setProxy(proxy);
    }

    public Response<String> submitChipEnrichRequest(ChipEnrichInputArguments args)
    {
    	
        List<NameValuePair> arguments = ChipEnrichArgsUtil.buildNameValueArgsFromChipEnrichData(args);
        System.out.println("inside submitChipEnrichRequest"+arguments);
        return executeChipEnrichRequest(arguments);
    }

    public Response<RequestStatus<List<ChipEnrichResults>>> chipEnrichStatus(String uuid)
    {
    	
    	return executeStatusRequest(uuid);
        
    }

    private Response<String> executeChipEnrichRequest(List<NameValuePair> args)
    {
    	
        String url = wsUrlBuilder.toServiceUrl("ncibi", "chipenrich");
        System.out.println(url);
        return wsClient.executeRequest(url, args);
    }

    private Response<RequestStatus<List<ChipEnrichResults>>> executeStatusRequest(String uuid)
    {
    	
    	String url = wsUrlBuilder.toServiceUrl("ncibi", "status") + "/" + uuid + "/chipenrich";
    	System.out.println("executeStatusRequest");
        return wsStatusClient.executeRequest(url);
    }
}
