package org.ncibi.ws.client;

import java.util.List;

import org.junit.Test;
import org.ncibi.lrpath.LRPathArguments;
import org.ncibi.lrpath.LRPathResult;
import org.ncibi.task.TaskStatus;
import org.ncibi.ws.HttpRequestType;
import org.ncibi.ws.Response;
import org.ncibi.ws.client.NcibiLRPathService;
import org.ncibi.ws.request.RequestStatus;

public class NcibiLRPathServiceTest
{
    @Test
    public void testSubmitLRPathRequest()
    {
        LRPathArguments data = new LRPathArguments();
        int[] geneids = {
                1, 2, 9, 10, 12, 13, 14, 15, 16, 18
        };
        double[] sigvals = {
                0.49, 0.03, 0.14, 0.13, 0.01, 0.8, 0.73, 0.27, 0.08, 0.02
        };
        double[] direction = {
                1.0531594127, 0.8881662714, 1.0228839641, 1.0156455341, 1.1258091261, 1.0065276564,
                1.0082119838, 0.9791801314, 0.9972248203, 1.0289062913
        };
        data.setGeneids(geneids);
        data.setSigvals(sigvals);
        data.setDirection(direction);
        data.setMing(5);
        data.setSpecies("hsa");
        data.setMaxg(99999);
        data.setSigcutoff(0.05);
        data.setOddsmin(0.0010);
        data.setOddsmax(0.5);
        data.setDatabase("OMIM");

        for (int i = 0; i < 1; i++)
        {
            NcibiLRPathService client = new NcibiLRPathService(HttpRequestType.POST);
            System.out.println("\n\n submitting request(" + i + ")...\n\n");
            Response<String> response = client.submitLRPathRequest(data);
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

            Response<RequestStatus<List<LRPathResult>>> r = client.lrpathStatus(response.getResponseValue());

            System.out.println("ResponseValue = " + response.getResponseValue());

            while (r.getResponseValue().getTask().getStatus() != TaskStatus.DONE)
            {
                System.out.println("Task not done, status: " + r.getResponseValue().getTask().getStatus());
                sleep(4);
                r = client.lrpathStatus(response.getResponseValue());
            }
            System.out.println("Done, results are: " + r);
            List<LRPathResult> results = r.getResponseValue().getData();
            for (LRPathResult result : results)
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