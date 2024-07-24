# aiBotFunction
 
 This is a aiBotFunction developed using Spring Serveless deployed in AWS Lambda.

 ## What this project achieves
 - Using **pay-as-you-go** cloud services. Which is best for apps that needs full availabilty but with less usage.
   - Using Pinecone online for the vector store.
   - Using AWS lambda as the compute backend
   This is best cost model for infrequent usage apps but being always available.
 - Using Spring Serverless
   - Makes Developing and testing application locally is easy. Just like any other SpringBoot application
   - Spring makes other module integration like aws secret and aws lamda integration easy with standard spring configuration
   - Since we use Spring Serverless we can use Spring AI which makes developing apps using LLM more robust
   


