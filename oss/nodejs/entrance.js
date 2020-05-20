'use strict';
/**
 * This function triggered by oss, and start execute workflow to process oss object
 */
const FnFClient = require('@alicloud/fnf-2019-03-15');


exports.handler = async function (event, context, callback) {
    let eventJson = JSON.parse(event);
    console.log('Receive oss trigger event: %s', eventJson);

    // Read ak/sk/token from context and construct a FnFClient
    const client = new FnFClient({
        endpoint: 'https://' + context.region + '.fnf.aliyuncs.com',
        accessKeyId: context.credentials.accessKeyId,
        accessKeySecret: context.credentials.accessKeySecret,
        securityToken: context.credentials.securityToken
    });

    // StartExecution to execute serverless workflow
    const flowName = 'fnf-demo-oss-flow'
    await client.startExecution ({
        FlowName: flowName,
        Input: JSON.stringify(eventJson),
    }).then(function(res){
        console.log('StartExecution flow: %j, response: %j', flowName, res);
        callback(null, 'success');
    }).catch(function(err){
        callback(err);
    });
};
