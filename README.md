# Spring Boot app with AWS SQS integration

**A simple Spring Boot REST application that integrates with AWS SQS (FIFO queues) using the Spring AWS messaging library, it demonstrates usage for both sending and consuming messages from a specific queue.**


1. Setup a SQS FIFO queue in your AWS account, update the Queue name accordingly in the SQSRestController
2. Replace the credentials in the application.properties file with your AWS account/user credentials
3. Build & start the application.
4. Post a message to the /customer endpoint as shown below, this will post the customer object to the SQS Queue and will subsequently be received via the listener method  within the controller

	**curl -X POST localhost:8080/customer -H 'Content-type:application/json' -d '{"customerId": "1999", "name": "Rogers Nelson", "state":"MN"}'**


