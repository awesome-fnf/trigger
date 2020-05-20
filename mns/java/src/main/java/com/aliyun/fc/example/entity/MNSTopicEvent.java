package com.aliyun.fc.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MNSTopicEvent {
    public String TopicOwner;
    public String Message;
    public Long PublishTime;
    public String SubscriptionName;
    public String MessageMD5;
    public String TopicName;
    public String MessageId;

    @Override
    public String toString() {
        return "MNSTopicEvent{" +
                "TopicOwner='" + TopicOwner + '\'' +
                ", Message='" + Message + '\'' +
                ", PublishTime=" + PublishTime +
                ", SubscriptionName='" + SubscriptionName + '\'' +
                ", MessageMD5='" + MessageMD5 + '\'' +
                ", TopicName='" + TopicName + '\'' +
                ", MessageId='" + MessageId + '\'' +
                '}';
    }
}
