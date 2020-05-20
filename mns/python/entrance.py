# -*- coding: utf-8 -*-
import json
import logging

from aliyunsdkcore.client import AcsClient
from aliyunsdkcore.auth.credentials import StsTokenCredential
from aliyunsdkfnf.request.v20190315.StartExecutionRequest import StartExecutionRequest


def handler(event, context):
    logger = logging.getLogger()

    # Parse string format event to json
    evt = json.loads(event)
    logger.info('Receive mns trigger event: %s', evt)

    # Read sts ak/sk/token from context and construct a AcsClient
    creds = context.credentials
    sts_creds = StsTokenCredential(creds.access_key_id, creds.access_key_secret, creds.security_token)
    client = AcsClient(credential=sts_creds, region_id=context.region)

    # StartExecution api to execute serverless workflow
    flow_name = 'fnf-demo-mns-flow'
    req = StartExecutionRequest()
    req.set_FlowName(flow_name)
    req.set_Input(event)

    resp = client.do_action_with_exception(req)
    logger.info('StartExecution flow: %s, response: %s', flow_name, resp)

    return "success"
