package main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import snake.spielfeld.Spielfeld;

import java.awt.image.DirectColorModel;
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
public class Logger
{

	private Map<Integer, Info> turnInfoMap = new HashMap<>();
	private String snakeName;

	public void setSnakeName(String snakeName)
	{
		this.snakeName = snakeName;
	}

	public String getSnakeName()
	{
		return snakeName;
	}

	public void addOutput(int turn, String output)
	{
		turnInfoMap.putIfAbsent(turn, new Info());
		turnInfoMap.get(turn).addMessage(output);
		System.out.println(output);
	}

	public void setDirection(int turn, Spielfeld.direction direction)
	{
		turnInfoMap.putIfAbsent(turn, new Info());
		turnInfoMap.get(turn).setDirection(direction);
	}

	public String getOutput(int turn, String separator)
	{
		return String.join(separator, turnInfoMap.getOrDefault(turn, new Info()).getMessages());
	}

	public Info getInfo(int turn)
	{
		return turnInfoMap.get(turn);
	}

	public Spielfeld.direction getDirection(int turn)
	{
		return turnInfoMap.get(turn).getDirection();
	}

	public Map<Integer, Info> getTurnInfoMap()
	{
		return turnInfoMap;
	}

	public String toJsonString()
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}

	public void loadFromJsonString(String json)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		this.turnInfoMap = gson.fromJson(json, this.getClass()).getTurnInfoMap();
	}

	public static class Info
	{
		public List<String> messages = new ArrayList<>();
		public Spielfeld.direction direction;

		public void addMessage(String message)
		{
			messages.add(message);
		}

		public List<String> getMessages()
		{
			return messages;
		}

		public void setDirection(Spielfeld.direction direction)
		{
			this.direction = direction;
		}

		public Spielfeld.direction getDirection()
		{
			return direction;
		}

	}

}
