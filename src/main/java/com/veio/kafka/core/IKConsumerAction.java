package com.veio.kafka.core;

public interface IKConsumerAction {
	public void process(String message);
}