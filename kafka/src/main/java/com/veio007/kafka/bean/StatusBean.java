package com.veio007.kafka.bean;

import com.veio007.kafka.utils.MsgCounter;
import org.softee.management.annotation.Description;
import org.softee.management.annotation.MBean;
import org.softee.management.annotation.ManagedAttribute;

import java.util.Map;

@MBean
@Description("com.veio007.kafka monitor")
public class StatusBean {
	@ManagedAttribute
	@Description("kafka消息计数")
	public Map<String, String> getKafkaMsgCountMap() {
		return MsgCounter.getCountMap();
	}
}
