package org.ncibi.ws.client;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.ncibi.lrpath.LRPathArguments;
import org.ncibi.ws.util.NameValuePairUtil;

class LRPathArgsUtil
{
	private LRPathArgsUtil()
	{
	}

	public static List<NameValuePair> buildNameValueArgsFromLRPathData(LRPathArguments data)
	{
		List<NameValuePair> args = new LinkedList<NameValuePair>();
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("database", data.getDatabase()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("maxg", data.getMaxg()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("ming", data.getMing()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("oddsmax", data.getOddsmax()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("oddsmin", data.getOddsmin()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("sigcutoff", data.getSigcutoff()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("species", data.getSpecies()));
		args.add(NameValuePairUtil.buildNameValuePairFromCollection("geneids", toIntegerList(data.getGeneids())));
		args.add(NameValuePairUtil.buildNameValuePairFromCollection("sigvals", toDoubleList(data.getSigvals())));
		args.add(NameValuePairUtil.buildNameValuePairFromCollection("direction", toDoubleList(data.getDirection())));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("application", data.getApplication()));
		args.add(NameValuePairUtil.buildNameValuePairFromNameValue("javax", true));
		args.add(NameValuePairUtil.buildNameValuePairFromCollection("identifiers", toStringList(data.getIdentifiers())));
		return args;
	}

	private static List<Double> toDoubleList(double[] items)
	{
		List<Double> l = new LinkedList<Double>();
		if (items != null)
		{
			for (double item : items)
			{
				l.add(item);
			}
		}

		return l;
	}

	private static List<Integer> toIntegerList(int[] items)
	{
		List<Integer> l = new LinkedList<Integer>();
		if (items != null)
		{
			for (int item : items)
			{
				l.add(item);
			}
		}
		return l;
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
