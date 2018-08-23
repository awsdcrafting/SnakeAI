package main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Project: SnakeAI
 * Created by scisneromam on 23.08.2018.
 *
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2018 | scisneromam | All rights reserved.
 */
public class Output
{

	private Map<Integer, List<String>> turnOutputMap = new HashMap<>();

	public void addOutput(int turn, String output)
	{
		turnOutputMap.putIfAbsent(turn, new ArrayList<>());
		turnOutputMap.get(turn).add(output);
	}

	public String getOutput(int turn, String separator)
	{
		return String.join(separator, turnOutputMap.getOrDefault(turn, new ArrayList<>()));
	}

	public Map<Integer, List<String>> getTurnOutputMap()
	{
		return turnOutputMap;
	}

	public String toJsonString()
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}

	public void loadFromJsonString(String json)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		this.turnOutputMap = gson.fromJson(json, this.getClass()).getTurnOutputMap();
	}

}
