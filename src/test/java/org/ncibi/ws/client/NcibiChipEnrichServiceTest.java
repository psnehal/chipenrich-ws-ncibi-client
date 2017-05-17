package org.ncibi.ws.client;

import java.util.List;

import org.junit.Test;
import org.ncibi.chipenrich.ChipEnrichInputArguments;
import org.ncibi.chipenrich.ChipEnrichResults;
import org.ncibi.task.TaskStatus;
import org.ncibi.ws.HttpRequestType;
import org.ncibi.ws.Response;
import org.ncibi.ws.request.RequestStatus;

public class NcibiChipEnrichServiceTest
{
    @Test
    public void testSubmitLRPathRequest()
    {
    	ChipEnrichInputArguments data = new ChipEnrichInputArguments();
    	
    	 String[] slist = {"biocarta_pathways"};
       
    	
        data.setUploadFile("/home/snehal/workspace2/LRPathBranch/FileStore/nov30test12/E2A.txt");
        data.setOutPath("/home/snehal/workspace2/LRPathBranch/FileStore/nov30test12/");
        data.setEmail("snehal@med.umich.edu");
        data.setOutName("test2");
        data.setSgList("human");
        data.setMethod("chipenrich");
        data.setLd("1kb");
        data.setSgSetList(slist);
        data.setIsMappable("true");
        data.setQc("true");
        
        for (int i = 0; i < 1; i++)
        {
        	NcibiChipEnrichService client = new NcibiChipEnrichService(HttpRequestType.POST);
            System.out.println("\n\n submitting request(" + i + ")...\n\n");
            Response<String> response = client.submitChipEnrichRequest(data);
            System.out.println(response);
            if (!response.isSuccess())
            {
                try
                {
                    System.out.println("Couldn't submit request, sleeping and trying again.");
                    Thread.sleep(10000);
                    continue;
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            Response<RequestStatus<List<ChipEnrichResults>>> r = client.chipEnrichStatus(response.getResponseValue());

            System.out.println("ResponseValue = " + response.getResponseValue());

            while (r.getResponseValue().getTask().getStatus() != TaskStatus.DONE)
            {
                System.out.println("Task not done, status: " + r.getResponseValue().getTask().getStatus());
                sleep(4);
                r = client.chipEnrichStatus(response.getResponseValue());
            }
            System.out.println("Done, results are: " + r);
            List<ChipEnrichResults> results = r.getResponseValue().getData();
            for (ChipEnrichResults result : results)
            {
                System.out.println(result);
            }
        }
    }

    private void sleep(int seconds)
    {
        try
        {
            Thread.sleep(seconds * 1000);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}