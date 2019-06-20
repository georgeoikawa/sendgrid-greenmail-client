# sendgrid-greenmail-client

sendgrid-greenmail-client is a client for sending emails using the sendgrid provider and its own sdk java.

This is a brief presentation of the use sendgrid email provider's api, I'm using this api through a sendgrid java client and inside a microservice made with springboot, and like all good code, it's the code tested, let's use the greenmail library to simulate a smtp server to receive the emails.

First of all you need to create an account in https://sendgrid.com and enable an api Key to use in client.

There are some ways to use this sendgrid sdk and in case will be approached the sending of e-mail that will traffic the content of the email approached http connection in json format. There is another way to send an email, which would be to register a template in the administrative panel of sendgrid site and to send through the client with the key of the registered template. For any invocation to sendgrid is necessary to send athe key, which is available in the admin area of the https://app.sendgrid.com/settings/api_keys

# How to use the client
You can use the client through a rest service that has a post endpoint that receives the parameters to send the email or use the client directly in your java project,  because it has a spring service.

# Pre-requisites
Tools properly installed and configured in the deployment environment.
- JDK11
- Git
- Apache Maven 3.5 +

# Rest API Installation
Clone the source code: https://github.com/georgeoikawa/sendgrid-greenmail-client.git
Run the mvn clean install command from the cloned root directory
Run java -jar sendgrid-greenmail-client.jar (the jar is inside the target folder)

## Use
For invocation of the service some mandatory criteria must be followed as below.

### Send e-mail - The e-mail will be sent according to the parameters informed.
Http: POST Method
Endpoint service: http://localhost:8080/email/api/send

### Request body example:
{
  "to": "recipient@mail.com",
  "from": "sender@mail.com",
  "subject": "Email demo test",
  "templateName": "email-demo-template",
  "templateParams": {
  "name": "container",
    "email": "recipient@mail.com"
  }
}

### Example of Response Body:
{
	"statusCode": 202,
	"body": "{\"from\":{\"email\":\"sender@mail.com\"},\"subject\":\"Email demo teste\",\"personalizations\":[{\"to\":[{\"email\":\"recipient@mail.com\"}]}],\"content\":["CONTENT_HTML"],\"custom_args\":{\"customerAccountNumber\":\"{SENDGRID_API}\"}}",
	"headers": {
		"Server": "nginx",
		"Access-Control-Allow-Origin": "https://sendgrid.api-docs.io",
		"Access-Control-Allow-Methods": "POST",
		"Connection": "keep-alive",
		"X-Message-Id": "HYVUqxG-QtSxdMm3KyN2dA",
		"X-No-CORS-Reason": "https://sendgrid.com/docs/Classroom/Basics/API/cors.html",
		"Content-Length": "0",
		"Access-Control-Max-Age": "600",
		"Date": "Thu, 20 Jun 2019 14:36:54 GMT",
		"Access-Control-Allow-Headers": "Authorization, Content-Type, On-behalf-of, x-sg-elas-acl"
	}
}

### Answer messages
  400 - INVALID_ENTRIES_EMAIL = Verify the request send the email parameters<br/>
  400 - INVALID_MAIL_TEMPLATE_NAME = Invalid template name<br/>
  400 - INVALID_MAIL_SUBJECT = Invalid mail subject<br/>
  400 - INVALID_MAIL_FROM = Invalid email<br/>
  400 - INVALID_MAIL_TO = Invalid email<br/>

## For running tests
run: mvn clean install
