package com.veio.kafka.bean;

import com.veio.kafka.utils.MsgCounter;
import org.softee.management.annotation.Description;
import org.softee.management.annotation.MBean;
import org.softee.management.annotation.ManagedAttribute;

import java.util.Map;

@MBean
@Description("kafka monitor")
public class StatusBean {
	@ManagedAttribute
	@Description("kafka消息计数")
	public Map<String, String> getKafkaMsgCountMap() {
		return MsgCounter.getCountMap();
	}
}