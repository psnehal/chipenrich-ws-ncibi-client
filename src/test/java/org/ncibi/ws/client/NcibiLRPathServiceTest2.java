package org.ncibi.ws.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ncibi.lrpath.LRPathArguments;
import org.ncibi.lrpath.LRPathResult;
import org.ncibi.task.TaskStatus;
import org.ncibi.ws.HttpRequestType;
import org.ncibi.ws.Response;
import org.ncibi.ws.client.NcibiLRPathService;
import org.ncibi.ws.request.RequestStatus;

public class NcibiLRPathServiceTest2
{
	private int[] geneids;
	private double[] direction;
	private double[] sigvals;

	@Test
	public void testLRPath()
	{

		URL file = getClass().getResource("/org/ncibi/resource/gene_file_human3.txt");
		reader(file.getFile());

		System.out.println("r server  ");
		//LRPathRServer rserver = new LRPathRServer(RServerConfiguration.rserverAddress(), RServerConfiguration.rserverPort());
		System.out.println("r server connected ");
		//LRPath lrpath = new LRPath(rserver);
		LRPathArguments data = new LRPathArguments();

		data.setGeneids(geneids);
		data.setDirection(direction);
		data.setSigvals(sigvals);
		data.setSpecies("hsa");
		data.setMaxg(99999);
		data.setSigcutoff(0.05);
		data.setMing(5);
		data.setOddsmin(0.0010);
		data.setOddsmax(0.5);
		data.setDatabase("EHMN Pathway Gene");

		// System.out.println(data);
		System.out.println(data);

		NcibiLRPathService client = new NcibiLRPathService(HttpRequestType.POST);
		System.out.println("\n\n submitting request...\n\n");
		Response<String> response = client.submitLRPathRequest(data);
		System.out.println(response);
		if (!response.isSuccess())
		{
			try
			{
				System.out.println("Couldn't submit request, sleeping and trying again.");
				Thread.sleep(10000);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Response<RequestStatus<List<LRPathResult>>> r = client.lrpathStatus(response.getResponseValue());

		System.out.println(response.getResponseValue());

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

	private void reader(String file)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			ArrayList<Integer> g = new ArrayList<Integer>();
			ArrayList<Double> s = new ArrayList<Double>();
			ArrayList<Double> d = new ArrayList<Double>();

			while ((line = reader.readLine()) != null)
			{
				System.out.println(line);
				String[] data = line.split("\t");
				if (data[0] != null && data[1] != null && data[0].matches("\\d+") && data[1].matches("-?\\d+(.\\d+)?")
						&& data[2].matches("-?\\d+(.\\d+)?"))
				{
					g.add((Integer) Integer.parseInt(data[0]));
					s.add((Double) Double.parseDouble(data[1]));
					d.add((Double) Double.parseDouble(data[2]));
				}

			}

			geneids = new int[g.size()];
			sigvals = new double[s.size()];
			direction = new double[0];

			for (int i = 0; i < g.size(); i++)
			{
				geneids[i] = (Integer) g.get(i);
				sigvals[i] = (Double) s.get(i);
				// direction[i] = (Double) d.get(i);
			}

			System.out.println("NUmber of genes : " + geneids.length);

			reader.close();
		}
		catch (IOException e)
		{
			System.out.println(e);
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
			e.printStackTrace();
		}
	}

}
