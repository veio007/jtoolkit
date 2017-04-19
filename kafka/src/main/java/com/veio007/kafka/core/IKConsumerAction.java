package com.veio007.kafka.core;

public interface IKConsumerAction {
	public void process(String message);
}