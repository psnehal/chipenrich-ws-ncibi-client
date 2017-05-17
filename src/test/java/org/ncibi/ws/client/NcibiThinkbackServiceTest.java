package org.ncibi.ws.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.ncibi.lrpath.LRPathArguments;
import org.ncibi.ws.Response;
import org.ncibi.ws.thinkback.LRThinkArgs;
import org.ncibi.ws.thinkback.ThinkbackAdjustmentMethod;
import org.ncibi.ws.thinkback.ThinkbackEnrichmentMethod;

public class NcibiThinkbackServiceTest
{
//    private static String BASE = "C:\\Users\\ffarfan\\Documents\\U-M\\Research\\Eureka\\Code\\STS\\Eureka\\data\\Revision\\";
	private static String BASE = "/media/266A07E06A07AC1D/ffarfan/Documents/U-M/Research/Eureka/Code/STS/Eureka/data/Revision/";
    
    @Test
    public void thinkbackGSEA_DS()
    {
        NcibiGseaThinkService thinkback = new NcibiGseaThinkService();
        
        Response<String> r = thinkback.submitThinkBackRequest(ThinkbackEnrichmentMethod.GSEA,
                ThinkbackAdjustmentMethod.DS, pathwaysToAnalyze(), BASE + "expns3494_2.gct", BASE + "3494.cls", BASE
                        + "keggdata_all.gmt", BASE + "HG_U133A.chip");
        System.out.println(r);
    }
    
    @Test
    @Ignore
    public void thinkbackLRPATH_DS() {
        
        NcibiLrpathThinkService thinkback = new NcibiLrpathThinkService();
        
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
        data.setDatabase("KEGG Pathway");
        
        
        
        LRThinkArgs a = new LRThinkArgs();
        a.setAdjustmentMethod(ThinkbackAdjustmentMethod.DS);
        a.setLrpathArgs(data);
        a.setPathways(pathwaysToAnalyze());
        
        Response<String> r = thinkback.submitLrpathThinkRequest(a);
        System.out.println(r);
        
    }

    private List<String> pathwaysToAnalyze()
    {
        List<String> pathways = new LinkedList<String>();
        try
        {
            
            FileInputStream fstream = new FileInputStream(BASE + "pathways.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String pathway;
            while ((pathway = br.readLine()) != null)
            {
                pathways.add(pathway);
            }
            in.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }

        return pathways;
    }

}
