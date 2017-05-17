package org.ncibi.ws.client;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.ncibi.chipenrich.ChipEnrichInputArguments;
import org.ncibi.ws.util.NameValuePairUtil;

class ChipEnrichArgsUtil
{
	private ChipEnrichArgsUtil()
	{
	}
	
	


	public static List<NameValuePair> buildNameValueArgsFromChipEnrichData(ChipEnrichInputArguments data)
	{
		List<NameValuePair> args = new LinkedList<NameValuePair>();
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("uploadfile", data.getUploadFile()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("uploadMappafile", data.getUploadMappaFile()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("outpath", data.getOutPath()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("email", data.getEmail()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("outname", data.getOutName()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("sglist", data.getSgList()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("method", data.getMethod()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("ld", data.getLd()));
		args.add(NameValuePairUtil.buildNameValuePairFromCollection("slist", toStringList(data.getSgSetList())));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("ismappable", data.getIsMappable()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("rc", data.getRc()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("qc", data.getQc()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("filter", data.getFilter()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("peakthr", data.getPeakThr()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("javax", true));
		return args;
		
	}
	private static List<String> toStringList(String[] items)
	{
		List<String> l = new LinkedList<String>();
		if (items != null)
		{
			for (String item : items)
			{
				l.add(item);
			}
		}
		return l;
	}

	
}
