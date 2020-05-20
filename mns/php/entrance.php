<?php

use AlibabaCloud\Client\AlibabaCloud;
use AlibabaCloud\Fnf\Fnf;
use AlibabaCloud\Client\Credentials\StsCredential;
use AlibabaCloud\Client\Signature\ShaHmac1Signature;


function handler($event, $context) {
    $eventObj = json_decode($event, $assoc = true);
    $logger = $GLOBALS['fcLogger'];
    $logger->info('Receive mns trigger event: '.$event);

    # Read ak/sk/token from context and construct a invoke client
    $stsCreds = new StsCredential($context['credentials']['accessKeyId'],
        $context['credentials']['accessKeySecret'],
        $context['credentials']['securityToken']);
    AlibabaCloud::client($stsCreds, new ShaHmac1Signature())
        ->regionId($context['region'])
        ->asDefaultClient();

    # StartExecution to execute serverless workflow
    $flowName = 'fnf-demo-mns-flow';
    $options = [
        'debug'=>false,
        'connect_timeout'=>10,
        'timeout'=>10,
        'form_params'=>[
            'FlowName'=>$flowName,
            'Input'=>json_encode($eventObj),
        ],
    ];
    $resp = Fnf::v20190315()
        ->startExecution($options)
        ->request();
    $logger->info('StartExecution flow:'.$flowName.', response: '.(string)$resp);

    return 'hello world';
}