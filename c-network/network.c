#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#define FRAME_SIZE 4

// Structure to store Layers information

typedef struct Layers{

char senderID;
char receiverID;
char messageChunk[200];

char senderPort[7];
char receiverPort[7];

char senderIP[20];
char receiverIP[20];

char senderMAC[50];
char receiverMAC[50];

}Layers;

// Structure to store a single frame

typedef struct frameStructure{

Layers singleFrameArray[FRAME_SIZE];
int top;
int numberOfHopsOfFrame;

}frameStructure;

// Structure to store a single queue

typedef struct queueStructure{

int front, rear;
frameStructure *singleQueueArray;
}queueStructure;

// Structure to hold log values

typedef struct logStructure{

char activity;
char success;

}logStructure;

// Structure to store client data

typedef struct Clients{

char ID;
char ipAdress[50];
char macAdress[50];
char *neighbors;
queueStructure inq, outq;
logStructure clientLog[3];
int logNumberOfHops;
}Clients;

void printTime(){

	time_t rawtime;
	struct tm *timeinfo;
	time(&rawtime);
	timeinfo = localtime(&rawtime);
	char buffer[26];
	strftime(buffer, 26, "%Y-%m-%d %H:%M:%S", timeinfo);
	printf("%s\n", buffer);

}

void push(Layers item, frameStructure *frame) // Push function for stacks
{
    frame->singleFrameArray[++(frame->top)] = item;
}

Layers pop(frameStructure *stack)   // Pop function for stacks
{
    return stack->singleFrameArray[(stack->top)--];
}

void insert(frameStructure item, queueStructure **queue){  // Insert function for queue

    (*queue)->singleQueueArray[++((*queue)->rear)] = item;

}

void dequeue(queueStructure **queue){   // Dequeue function for queue

	(*queue)->front = (*queue)->front + 1;

}

// Return the adress of the queue for corresponding type and and client

queueStructure *findQueue(char targetID, char queueType, int numberOfClients, Clients *clientArray){

    int iter, targetIndex;
    for(iter = 0; iter < numberOfClients; iter++){

        if(clientArray[iter].ID == targetID){

            if(queueType == 'i'){return &(clientArray[iter].inq);}
            else if(queueType == 'o'){return &(clientArray[iter].outq);}
        }
    }
}

// Return the adress of the type Clients for corresponding ID

Clients *findClient(char clientID, int numberOfClients, Clients *clientArray){

    int iter;
    for(iter = 0; iter < numberOfClients; iter++){

        if(clientArray[iter].ID == clientID){return &(clientArray[iter]);}

    }

}

// Printing queue info for corresponding ID and queue type

int printQueueInfo(char targetID, char queueType, int numberOfClients, Clients *clientArray){

    int iter;
    int frameCounter = 0;

    if(queueType == 'o'){printf("Client %c Outgoing Queue Status\n", targetID);}
    else if(queueType == 'i'){printf("Client %c Incoming Queue Status\n", targetID);}

    queueStructure *myQueue = findQueue(targetID, queueType, numberOfClients, clientArray);

    if(myQueue->front == myQueue->rear){printf("Current total number of frames: 0\n"); return 0;}

    for(iter = 0; iter < numberOfClients; iter++){


        if(myQueue == NULL){break;}
        else{frameCounter++;}

    }

    printf("Current total number of frames: %d\n", frameCounter - 1);

}

// Printing frame info of corresponding ID and frameNO and queueType

int printFrameInfo(char ID, int frameNO, char queueType, int numberOfClients, Clients *clientArray, int numberOfFrames, char* wholeMessage, char senderID, char receiverID, int maxMessageSize){

    queueStructure *targetQueue = findQueue(ID, queueType, numberOfClients, clientArray);

    char currentChunk[maxMessageSize + 5];

    strncpy(currentChunk,wholeMessage + ((frameNO - 1)*maxMessageSize),maxMessageSize);

    if(frameNO > numberOfFrames || targetQueue->front == targetQueue->rear){printf("No such frame.\n"); return 0;} //Queue empty

    if(queueType == 'o'){printf("Current Frame #%d on the outgoing queue of client %c\n", frameNO, ID);}
    else if(queueType == 'i'){printf("Current Frame #%d on the incoming queue of client %c\n", frameNO, ID);}

    printf("Carried Message: \"%s\"\n", currentChunk);
    printf("Layer 0 info: Sender ID: %c, Receiver ID: %c\n", senderID, receiverID);
    printf("Layer 1 info: Sender port number: %s, Receiver port number: %s\n", targetQueue->singleQueueArray[frameNO - 1].singleFrameArray[1].senderPort, targetQueue->singleQueueArray[frameNO - 1].singleFrameArray[1].receiverPort);
    printf("Layer 2 info: Sender IP address: %s, Receiver IP address: %s\n", targetQueue->singleQueueArray[frameNO - 1].singleFrameArray[2].senderIP, targetQueue->singleQueueArray[frameNO - 1].singleFrameArray[2].receiverIP);
    printf("Layer 3 info: Sender MAC address: %s, Receiver MAC address: %s\n", targetQueue->singleQueueArray[frameNO - 1].singleFrameArray[3].senderMAC, targetQueue->singleQueueArray[frameNO - 1].singleFrameArray[3].receiverMAC);
    printf("Number of hops so far: %d\n", targetQueue->singleQueueArray[frameNO - 1].numberOfHopsOfFrame);

}

// Fill the log data of the corresponding client

void fillLog(char targetID, int entryNumber, int numberOfClients, Clients *clientArray, int numberOfFrames, char success, char activity){

    Clients *targetClient = findClient(targetID, numberOfClients, clientArray);

    targetClient->clientLog[entryNumber].success = success;
    targetClient->clientLog[entryNumber].activity = activity;

}

void printLog(char targetID, int numberOfClients, int numberOfFrames, Clients *clientArray, char senderID, char receiverID, char *message){

    int iter, counter = 1;

    Clients *targetClient = findClient(targetID, numberOfClients, clientArray);

    for(iter = 0; iter < 2; iter++){

        if(targetClient->clientLog[iter].success != NULL){

			printf("Log Entry #%d:\n", counter);
			printf("Timestamp: ");
			printTime();
			printf("Message: %s\n", message);
			printf("Number of frames: %d\n", numberOfFrames);
			printf("Number of hops: %d\n", targetClient->logNumberOfHops);
			printf("Sender ID: %c\n", senderID);
			printf("Receiver ID: %c\n", receiverID);
			if(targetClient->clientLog[iter].activity == 'f'){printf("Activity: Message Forwarded\n");}
			else if(targetClient->clientLog[iter].activity == 'r'){printf("Activity: Message Received\n");}
			if(targetClient->clientLog[iter].success == 'y'){printf("Success: Yes\n");}
			else if(targetClient->clientLog[iter].success == 'n'){printf("Success: No\n");}
			if(iter == 0){printf("--------------\n");}
			counter++;
        }

    }

}

// Find next client while going from source client to target client

char findNextClient(char source, char target, Clients *clientArray, int numberOfClients){

    int iter, iter2, storedIndex;

    for(iter = 0; iter < numberOfClients; iter++){

        if(clientArray[iter].ID == source){
                storedIndex = iter;
        }
    }

    for(iter2 = 0; iter2 < 2 * (numberOfClients - 1); iter2++){

        if(clientArray[storedIndex].neighbors[iter2] == target){

                return clientArray[storedIndex].neighbors[iter2 + 1];
        }
    }
}

char *macGetter(char targetID, int totalClients, Clients *clientArray){ // Get MAC adress of the client

    int iter;

    for(iter = 0;iter < totalClients; iter++){

        if(clientArray[iter].ID == targetID){

            return clientArray[iter].macAdress;

        }
    }
}

// Recursive function for the SEND command

char sendRecursive(char primarySender, char currentClient, char target, Clients *clientArray, int numberOfClients, int numberOfFrames, int hops, char *message){

    hops++;

    char nextStop = findNextClient(currentClient, target, clientArray,numberOfClients); // Find next client from routing table

    if(currentClient != target){printf("A message received by client %c, but intended for client %c. Forwarding...\n",currentClient ,target);}

    int iter, iter2;

    char* newReceiverMAC[50], newSenderMAC[50];

	// If first time going in SEND function, put frames to outging queue from incoming queue

	if(currentClient == findNextClient(primarySender, target, clientArray, numberOfClients)){


        queueStructure *firstOutgoing = findQueue(primarySender, 'o', numberOfClients, clientArray);
        queueStructure *secondIncoming = findQueue(currentClient, 'i', numberOfClients, clientArray);
        queueStructure *secondOutgoing = findQueue(currentClient, 'o', numberOfClients, clientArray);

        secondIncoming->rear = secondIncoming->front = -1;
        secondIncoming->singleQueueArray = (frameStructure*) malloc(numberOfFrames * sizeof(frameStructure*));

        secondOutgoing->rear = secondOutgoing->front = -1;
        secondOutgoing->singleQueueArray = (frameStructure*) malloc(numberOfFrames * sizeof(frameStructure*));

        for(iter2 = 0; iter2 < numberOfFrames; iter2++){ // First client's OUTGOING to second clients's INCOMING

            insert((*firstOutgoing).singleQueueArray[iter2], &secondIncoming);


            dequeue(&firstOutgoing);
        }

        for(iter2 = 0; iter2 < numberOfFrames; iter2++){  // Second client's INCOMING to second clients's OUTGOING

            insert(secondIncoming->singleQueueArray[iter2], &secondOutgoing);
            dequeue(&secondIncoming);

        }

    }
    if(nextStop == '-'){


        printf("Error: Unreachable destination. Packets are dropped after %d hops!\n", hops);
        Clients *lastSuccessClientForLog = findClient(currentClient, numberOfClients, clientArray);
        lastSuccessClientForLog->clientLog[1].success = 'n';  // Fill the log data
        lastSuccessClientForLog->clientLog[1].activity = 'f';
        return 0;
    }

    else if(currentClient == target){ // If function reached the final destination succesfully

        Clients *lastClientForLog = findClient(target, numberOfClients, clientArray);

        lastClientForLog->clientLog[0].success = 'y';
        lastClientForLog->clientLog[0].activity = 'r';
        lastClientForLog->logNumberOfHops = hops;

        printf("A message received by client %c from client %c after a total of %d hops.\n",currentClient ,primarySender,  hops);
        printf("Message: %s\n", message);

        queueStructure *finalIncoming = findQueue(target, 'i', numberOfClients, clientArray);

        for(iter = 0; iter < numberOfFrames; iter++){ // Replace MAC adresses with new MAC adresses

            strcpy(finalIncoming->singleQueueArray[iter].singleFrameArray[3].senderMAC, finalIncoming->singleQueueArray[iter].singleFrameArray[3].receiverMAC);
            strcpy(finalIncoming->singleQueueArray[iter].singleFrameArray[3].receiverMAC, macGetter(target, numberOfClients, clientArray));
            finalIncoming->singleQueueArray[iter].numberOfHopsOfFrame = hops; // Update hop value of the Client
        }

        return 0;

    }

    else {

        queueStructure *currentOutgoing = findQueue(currentClient, 'o', numberOfClients, clientArray);
        queueStructure *currentIncoming = findQueue(nextStop, 'i', numberOfClients, clientArray);

        currentIncoming->rear = currentIncoming->front = -1;
        currentIncoming->singleQueueArray = (frameStructure*) malloc(numberOfFrames * sizeof(frameStructure*));

        for(iter2 = 0; iter2 < numberOfFrames; iter2++){

            insert(currentOutgoing->singleQueueArray[iter2], &currentIncoming);  // Current client's OUTGOING to next client's INCOMING
            dequeue(&currentOutgoing);

        }

        Clients *forwarderClienttoTarget = findClient(currentClient, numberOfClients, clientArray);

		// Update log values if transfer succesfull

        forwarderClienttoTarget->clientLog[0].activity = 'r';
        forwarderClienttoTarget->clientLog[0].success = 'y';
        forwarderClienttoTarget->clientLog[1].activity = 'f';
        forwarderClienttoTarget->logNumberOfHops = hops;

		// Update log values if transfer not succesfull

        if(findNextClient(currentClient, target, clientArray, numberOfClients) != '-'){forwarderClienttoTarget->clientLog[1].success = 'y';}
        else {forwarderClienttoTarget->clientLog[1].success = 'n';}

        if(target != nextStop){

			// If next stop not equals final destination update queues acoordingly

            forwarderClienttoTarget->clientLog[1].success = 'y';

            Clients *secondClientForLogReceiver = findClient(nextStop, numberOfClients, clientArray);

            secondClientForLogReceiver->clientLog[0].activity = 'r';
            secondClientForLogReceiver->clientLog[0].success = 'y';
            secondClientForLogReceiver->logNumberOfHops = hops;

            queueStructure *nextOut = findQueue(nextStop, 'o', numberOfClients, clientArray);

            nextOut->rear = nextOut->front = -1;
            nextOut->singleQueueArray = (frameStructure*) malloc(numberOfFrames * sizeof(frameStructure*));

            for(iter2 = 0; iter2 < numberOfFrames; iter2++){

                insert(currentIncoming->singleQueueArray[iter2], &nextOut); // Current client's INCOMING to next client's OUTGOING
                dequeue(&currentIncoming);

            }

            strcpy(newReceiverMAC, macGetter(nextStop, numberOfClients, clientArray));
            strcpy(newSenderMAC, macGetter(currentClient, numberOfClients, clientArray));

            for(iter = 0; iter < numberOfFrames; iter++){  // Update MAC adresses

                strcpy(nextOut->singleQueueArray[iter].singleFrameArray[3].receiverMAC, newReceiverMAC);
                strcpy(nextOut->singleQueueArray[iter].singleFrameArray[3].senderMAC, newSenderMAC);

            }
        }
    }


        for(iter = 0; iter < numberOfFrames; iter++){

            printf("\tFrame #%d MAC address change: New sender MAC %s, new receiver MAC %s\n", iter + 1, macGetter(currentClient, numberOfClients, clientArray) , macGetter(nextStop, numberOfClients, clientArray));

        }

		// Make another recursive call with updated nextStop parameter

		return sendRecursive(primarySender, nextStop, target, clientArray, numberOfClients, numberOfFrames, hops, message);

}

int createMessage(Clients *clientArray, int numberOfClients, char sender, char receiver, char* realMessage, char *senderIP, char* receiverIP, char *senderMAC, char* receiverMAC, int maxMessageSize, char incomingPort[], char outGoingPort[]){

	int numberOfFrames = (strlen(realMessage) / maxMessageSize) + 1, iter, c;  // Find number of frames

    if(findNextClient(sender, receiver, clientArray, numberOfClients) == '-'){printf("Error: Unreachable destination. Packets are dropped after 0 hops!\n"); return 0;}

	// Allocate memory for array of frames

	frameStructure *arrayOfFrameStructures = (frameStructure*) malloc(numberOfFrames * sizeof(frameStructure));

    queueStructure *firstOut = findQueue(sender, 'o', numberOfClients, clientArray);

    firstOut->rear = firstOut->front = -1;

    firstOut->singleQueueArray = (frameStructure*) malloc(numberOfFrames * 2 * sizeof(frameStructure*));

    char **chunkArray;

	// Store message chunks in a allocated double pointer char array

    chunkArray = (char**)malloc(numberOfFrames * sizeof(char));

    for(iter = 0; iter < numberOfFrames; iter++){

        chunkArray[iter] = (char*) malloc(maxMessageSize * sizeof(char));
        strncpy(chunkArray[iter],realMessage + (iter*maxMessageSize),maxMessageSize);

    }

	// Iterate over each frame and PUSH layers into the stacks

    for(iter = 0; iter < numberOfFrames; iter++){

        Layers appLayer, transportLayer, networkLayer, physicalLayer;

        arrayOfFrameStructures[iter].top = -1;

        strcpy(appLayer.messageChunk, chunkArray[iter]);
        appLayer.receiverID = receiver;
        appLayer.senderID = sender;
        push(appLayer,&(arrayOfFrameStructures[iter]));

        strcpy(transportLayer.receiverPort, incomingPort);
        strcpy(transportLayer.senderPort, outGoingPort);
        push(transportLayer,&(arrayOfFrameStructures[iter]));

        strcpy(networkLayer.receiverIP, receiverIP);
        strcpy(networkLayer.senderIP, senderIP);
        push(networkLayer, &(arrayOfFrameStructures[iter]));

        strcpy(physicalLayer.receiverMAC, macGetter(receiver,numberOfClients,clientArray));
        strcpy(physicalLayer.senderMAC, macGetter(sender,numberOfClients,clientArray));
        push(physicalLayer, &(arrayOfFrameStructures[iter]));

    }

    for(iter = 0;iter < numberOfFrames;iter++){

		// POP the Physical Layer and PUSH the new Physical Layer with updated MAC adresses

        pop(&(arrayOfFrameStructures[iter]));
        Layers newPhysicalLayer;
        strcpy(newPhysicalLayer.receiverMAC, macGetter(findNextClient(sender, receiver, clientArray, numberOfClients),numberOfClients,clientArray));
        strcpy(newPhysicalLayer.senderMAC, senderMAC);
        push(newPhysicalLayer,&(arrayOfFrameStructures[iter]));

        insert(arrayOfFrameStructures[iter], &firstOut); // Insert frames to the queues



        printf("Frame #%d\n", iter+1);

         for(c = 0;c < FRAME_SIZE; c++){
            switch(c){
                case 3:
            printf("Sender ID : %c, ", arrayOfFrameStructures[iter].singleFrameArray[FRAME_SIZE - c - 1].senderID);
            printf("Receiver ID : %c\n", arrayOfFrameStructures[iter].singleFrameArray[FRAME_SIZE - c - 1].receiverID);
            printf("Message chunk carried: %s\n", arrayOfFrameStructures[iter].singleFrameArray[FRAME_SIZE - c - 1].messageChunk);
            printf("--------\n");
                break;
                case 2:
            printf("Sender port number : %s, ", arrayOfFrameStructures[iter].singleFrameArray[FRAME_SIZE - c - 1].senderPort);
            printf("Receiver port number : %s\n", arrayOfFrameStructures[iter].singleFrameArray[FRAME_SIZE - c - 1].receiverPort);
                break;
                case 1:
            printf("Sender IP address : %s, ", arrayOfFrameStructures[iter].singleFrameArray[FRAME_SIZE - c - 1].senderIP);
            printf("Receiver IP address : %s\n", arrayOfFrameStructures[iter].singleFrameArray[FRAME_SIZE - c - 1].receiverIP);
                break;
                case 0:
            printf("Sender MAC address : %s,", arrayOfFrameStructures[iter].singleFrameArray[FRAME_SIZE - c - 1].senderMAC);
            printf(" Receiver MAC address : %s\n", arrayOfFrameStructures[iter].singleFrameArray[FRAME_SIZE - c - 1].receiverMAC);
            }
        }
    }

    Clients *firstClient = findClient(sender, numberOfClients, clientArray);

    firstClient->clientLog[0].activity = 'f'; // Update log activity as forwarding

    return numberOfFrames;
}

char *ipGetter(char targetID, int totalClients, Clients *clientArray){ // Get the IP adress of the targetID

    int iter;

    for(iter = 0;iter < totalClients; iter++){

        if(clientArray[iter].ID == targetID){

            return clientArray[iter].ipAdress;

        }
    }
}

int main( int argc, char *argv[])
{
    FILE *routeFilePointer;
    FILE *commandFilePointer;
    FILE *clientsFilePointer;

    clientsFilePointer = fopen(argv[1], "r");

    int numberOfClients, numberOfCommands, iter, index = 0, maxMessageSize;

	char outGoingPort[10], incomingPort[10];
	strcpy(outGoingPort, argv[5]);
	strcpy(incomingPort, argv[6]);

	maxMessageSize = atoi(argv[4]);

    while(!feof(clientsFilePointer)){

        fscanf(clientsFilePointer, "%d", &numberOfClients); // Get number of clients from input file
        break;
    }

    Clients *clientArray = (Clients *) malloc(numberOfClients * sizeof(Clients));

    while(!feof(clientsFilePointer)) { // Read client informations and store them in Clients array

        for (iter = 0; iter < numberOfClients; iter++) {
            fscanf(clientsFilePointer,"%s %s %s[^\n]", &clientArray[iter].ID, clientArray[iter].ipAdress, clientArray[iter].macAdress);
        }
    }
    fclose(clientsFilePointer);

	routeFilePointer = fopen(argv[2], "r");

    for(iter = 0; iter < numberOfClients; iter++){

        clientArray[iter].neighbors = (char*) malloc(2*(numberOfClients - 1) * sizeof(char*));

    }
        int lineCounter = 1, clientCounter = 1, charCounter = 1;
        char arr[10];

		while(! feof(routeFilePointer)){    // Store neighbor data in Clients structure's array
            if(lineCounter == 1){clientCounter++;}

			fgets(arr, 6, routeFilePointer);
            if(lineCounter != 5){

            clientArray[clientCounter - 2].neighbors[lineCounter*2-2] = arr[0];
            clientArray[clientCounter - 2].neighbors[lineCounter*2-1] = arr[2];

            }
            if(lineCounter == 5){lineCounter = 0;}

            lineCounter++;

		}

    fclose(routeFilePointer);

    char message[200], *realMessage, sender, receiver;

    int numberOfFrames;

    commandFilePointer = fopen(argv[3], "r");

    while(!feof(commandFilePointer)){

        fscanf(commandFilePointer, "%d", &numberOfCommands);
        break;
    }

    while(!feof(commandFilePointer)){

        char currentCommand[50];

        fscanf(commandFilePointer,"%s",currentCommand);

        if(strcmp(currentCommand, "MESSAGE") == 0){

            fscanf(commandFilePointer," %c %c ", &sender, &receiver); // Store message's sender and receiver data

            fgets(message, 100, commandFilePointer);

            printf("---------------------------------------------------------------------------------------\n");
            printf("Command: MESSAGE %c %c %s", sender, receiver, message);
            printf("---------------------------------------------------------------------------------------\n");

            realMessage = strtok(message, "#");

            printf("Message to be sent: %s\n\n", realMessage);

			// Call createMessage function and store its return value in numberOfFrames
            numberOfFrames = createMessage(clientArray, numberOfClients, sender, receiver, realMessage, ipGetter(sender, numberOfClients, clientArray), ipGetter(receiver, numberOfClients, clientArray),macGetter(sender, numberOfClients, clientArray), macGetter(receiver, numberOfClients, clientArray), maxMessageSize, incomingPort, outGoingPort);
        }

        else if(strcmp(currentCommand, "SEND") == 0){

            fgets(currentCommand, 100, commandFilePointer);

            printf("----------------\n");
            printf("Command: SEND %s", currentCommand);
            printf("----------------\n");

			// Call recursive send function
            if(numberOfFrames != 0){sendRecursive(sender, findNextClient(sender, receiver, clientArray, numberOfClients), receiver, clientArray, numberOfClients, numberOfFrames, 0, realMessage);}

        }

        else if(strcmp(currentCommand, "SHOW_FRAME_INFO") == 0){

            char IDtoShow;
            char queueType[5];
            int frameNumber;

            fscanf(commandFilePointer, " %c %s %d", &IDtoShow, queueType, &frameNumber);

            printf("---------------------------------\n");
            printf("Command: SHOW_FRAME_INFO %c %s %d\n", IDtoShow, queueType, frameNumber);
            printf("---------------------------------\n");

            printFrameInfo(IDtoShow, frameNumber, queueType[0], numberOfClients, clientArray, numberOfFrames, realMessage, sender, receiver, maxMessageSize);

        }

        else if(strcmp(currentCommand, "SHOW_Q_INFO") == 0){

            char IDtoShow;
            char queueType[5];

            fscanf(commandFilePointer, " %c %s", &IDtoShow, queueType);

            printf("---------------------------------\n");
            printf("Command: SHOW_Q_INFO %c %s\n", IDtoShow, queueType);
            printf("---------------------------------\n");

            printQueueInfo(IDtoShow, queueType[0], numberOfClients, clientArray);

        }

        else if(strcmp(currentCommand, "PRINT_LOG") == 0){

            char targetID[3];

            fgets(targetID, 3, commandFilePointer);

            printf("---------------------\n");
            printf("Command: PRINT_LOG %c\n", targetID[1]);
            printf("---------------------\n");

            printf("Client %c Logs:\n", targetID[1]);
            printf("--------------\n");
            printLog(targetID[1], numberOfClients, numberOfFrames, clientArray, sender, receiver, realMessage);

        }
        else{

            char wholeCommand[50];

            fgets(wholeCommand, 50, commandFilePointer);

            printf("---------------------\n");
            printf("Command: %s%s\n", currentCommand, wholeCommand);
            printf("---------------------\n");
            printf("Invalid command.\n");

        }
    }
	// Freeing memories and closing command file
    
	free(clientArray);
    fclose(commandFilePointer);

    for(iter = 0; iter < numberOfClients; iter++){

        free(clientArray[iter].neighbors);

    }
}
