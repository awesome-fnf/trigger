package com.aliyun.fc.example;

import com.aliyun.fc.example.entity.MNSTopicEvent;
import com.aliyun.fc.runtime.*;
import com.aliyun.fc.runtime.event.OSSEvent;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.fnf.model.v20190315.StartExecutionRequest;
import com.aliyuncs.fnf.model.v20190315.StartExecutionResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Entrance implements StreamRequestHandler {
    ObjectMapper mapper = new ObjectMapper();
    private final String FLOW_NAME = "fnf-demo-mns-flow";
    private final String REGION = "cn-shanghai"; // todo for now context not contain region info

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        FunctionComputeLogger fcLogger = context.getLogger();

        // Read input from InputStream
        String data = IOUtils.toString(input, Charset.defaultCharset());
        fcLogger.info(String.format("Received mns trigger event: %s", data));

        // This shows how to decode input to mns trigger event
        MNSTopicEvent mnsEvent = mapper.readValue(data, MNSTopicEvent.class);
        fcLogger.info(String.format("Parsed mns trigger event: %s", mnsEvent.toString()));

        // Read sts ak/sk/token from context and construct a AcsClient
        Credentials creds = context.getExecutionCredentials();
        IAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(REGION, creds.getAccessKeyId(), creds.getAccessKeySecret(),
                creds.getSecurityToken()));

        // StartExecution to execute serverless workflow
        StartExecutionRequest request = new StartExecutionRequest();
        request.setFlowName(FLOW_NAME);
        request.setInput(mapper.writeValueAsString(mnsEvent));
        try {
            StartExecutionResponse response = client.getAcsResponse(request);
            fcLogger.info(String.format("StartExecution flow: %s, response requestId: %s, execName: %s",
                    FLOW_NAME, response.getRequestId(), response.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
