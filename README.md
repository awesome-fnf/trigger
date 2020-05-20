# Alibaba Cloud Serverless workflow event trigger
This repo contains:
- How to use [Function Compute(FC)](https://help.aliyun.com/document_detail/53102.html?spm=a2c4g.11186623.6.658.29754c07i6A47o) event triggers.
- How to trigger [Serverless Workflow(FnF)](https://help.aliyun.com/knowledge_detail/114020.html?spm=5176.cnfnf.0.0.66ab1ea8LigDBR) by fc and its event triggers.

## Supported
### Trigger
- OSS
- MNS Topic

### Language
- java
- python
- nodejs
- php

## How to use
1. Replace all `{REPLACE_ME}` with your own info in `template.yml` before deploy.
2. Compile your code:
      - For java:
        ```bash
        mvn clean package
        ```
      - For php, run `composer` to install all dependencies needed:
          ```bash
          composer require alibabacloud/fnf
          composer config repo.packagist composer https://mirrors.aliyun.com/composer/
          ```
      - For nodejs, run `npm install` if needed.
      - For python, do nothing.
3. Deploy
   ```bash
   fun deploy
   ```

# About
Pull request welcome.<br>
You can join DingDing discuss group by scan:
![image](resource/fnf.JPG)