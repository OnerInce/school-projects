#include<iostream>
#include<fstream>
#include <string>
#include <cstring>
#include <stdlib.h>
#include <string.h>
#include <map>
#include <vector>
#include <algorithm>

using namespace std;

typedef std::map<std::string, int> MyMap;

typedef struct secondListNode{

    char awayTeamName[50];
    int minuteOfGoal;
    int matchID;
    struct secondListNode* secondListNextPointer;
    struct secondListNode* secondListPrevPointer;

}secondListNode;

typedef struct firstListNode{

    char footballerName[50];
    char footballerTeamName[50];
    struct firstListNode* firstListNextPointer;
    secondListNode * nextListPointer;

}firstListNode;

// Function to find which half has most goals

int calculatePeriod(firstListNode* head){

    firstListNode* iterPeriod = head;

    int firstHalf = 0, secondHalf = 0;

    while(iterPeriod != NULL){

            secondListNode* iterPeriod2 = iterPeriod->nextListPointer;

            while(iterPeriod2 != NULL){

                    if(iterPeriod2->minuteOfGoal < 46){firstHalf++;}
                    else if(iterPeriod2->minuteOfGoal > 45){secondHalf++;}


                iterPeriod2 = iterPeriod2->secondListNextPointer;
            }
        iterPeriod = iterPeriod->firstListNextPointer;

    }

    free(iterPeriod);

    if(firstHalf > secondHalf){return 0;}
    else if(firstHalf < secondHalf){return 1;}
    else if(firstHalf == secondHalf){return -1;}

}

// Find top scorers using Map

void calculateTopScorer(MyMap scorerMap){
	    
	map<std::string, int>::iterator itr; 

    int topScoredGoal = 0;
    vector<string> scorerVector;

    for (itr = scorerMap.begin(); itr != scorerMap.end(); ++itr)
    {
        if(itr->second > topScoredGoal){topScoredGoal = itr->second;}
    }

    for (itr = scorerMap.begin(); itr != scorerMap.end(); ++itr)
    {
        if(itr->second == topScoredGoal){scorerVector.push_back(itr->first);}
    }

    int i, numberOfTopScorers = scorerVector.size();

    for(i = 0; i < numberOfTopScorers; i++){

        cout << scorerVector[i] << endl;

    }
}

// Find players who scored 3 or more goals in a single match

void hatTrickFinder(firstListNode* head){


    int currentMatchID = 0;

    firstListNode* iterFirst = head;

    while(iterFirst != NULL){

        secondListNode* iterSecond = iterFirst->nextListPointer;
        int singleMatchGoalCounter = 0;
        currentMatchID = iterSecond->matchID;

        while(iterSecond != NULL){

            if(iterSecond->matchID == currentMatchID){singleMatchGoalCounter++;}
            else{singleMatchGoalCounter = 1; currentMatchID = iterSecond->matchID;}
            if(singleMatchGoalCounter > 2){printf("%s\n", iterFirst->footballerName);}
            iterSecond = iterSecond->secondListNextPointer;
		}

        iterFirst = iterFirst->firstListNextPointer;
    }

}

firstListNode* addNodeFirstList(firstListNode* root1, char* footballerName, char* footballerTeamName, char* awayTeamName, int minuteOfGoal, int matchID){

    // If it is empty

    if(root1 == NULL){

        root1 = (firstListNode*)malloc(sizeof(firstListNode));
        root1->firstListNextPointer = NULL;
        strcpy(root1->footballerName, footballerName);
        strcpy(root1->footballerTeamName, footballerTeamName);

        secondListNode* temp = (secondListNode*)malloc(sizeof(secondListNode));
        strcpy(temp->awayTeamName, awayTeamName);
        temp->minuteOfGoal = minuteOfGoal;
        temp->matchID = matchID;
        temp->secondListNextPointer = NULL;
        temp->secondListPrevPointer = NULL;
        root1->nextListPointer = temp;
        return root1;

    }

    firstListNode* iterDuplicate = root1;

    // If player already in list, just add the goal information

    while(iterDuplicate!= NULL){

        if(strcmp(iterDuplicate->footballerName, footballerName) == 0){

                secondListNode* temp2 = (secondListNode*)malloc(sizeof(secondListNode));
                strcpy(temp2->awayTeamName, awayTeamName);
                temp2->minuteOfGoal = minuteOfGoal;
                temp2->matchID = matchID;

                secondListNode* iter2 = iterDuplicate->nextListPointer;

                // If need to add goal information to the head of the list

                if(iter2->matchID > matchID){

                        temp2->secondListNextPointer = iterDuplicate->nextListPointer;
                        temp2->secondListPrevPointer = NULL;
                        iterDuplicate->nextListPointer->secondListNextPointer = NULL;
                        iterDuplicate->nextListPointer->secondListPrevPointer = temp2;

                }

                else{
                while(iter2->secondListNextPointer != NULL && iter2->secondListNextPointer->matchID < matchID){

                    iter2 = iter2->secondListNextPointer;

                }

                temp2->secondListNextPointer = iter2->secondListNextPointer;
                temp2->secondListPrevPointer = iter2;
                iter2->secondListNextPointer = temp2;
                }
                return root1;
        }

			iterDuplicate = iterDuplicate->firstListNextPointer;

    }

    // If need to add a player to the head of the list

    if(strcmp(root1->footballerName, footballerName) > 0){

        firstListNode* tempNode = (firstListNode*)malloc(sizeof(firstListNode));
        strcpy(tempNode->footballerName, footballerName);
        strcpy(tempNode->footballerTeamName, footballerTeamName);
        tempNode->firstListNextPointer = root1;

        secondListNode* tempNode2 = (secondListNode*)malloc(sizeof(secondListNode));
        strcpy(tempNode2->awayTeamName, awayTeamName);
        tempNode2->minuteOfGoal = minuteOfGoal;
        tempNode2->matchID = matchID;
        tempNode2->secondListNextPointer = NULL;
        tempNode2->secondListPrevPointer = NULL;
        tempNode->nextListPointer = tempNode2;
        return tempNode;

    }

    firstListNode* iter = root1;
	
	// Find the correct place to add with iterating over the list

    while(iter->firstListNextPointer != NULL && strcmp(iter->firstListNextPointer->footballerName, footballerName) < 0){

        iter = iter->firstListNextPointer;

    }

    firstListNode* temp2 = (firstListNode*) malloc(sizeof(firstListNode));

    temp2->firstListNextPointer = iter->firstListNextPointer;
    iter->firstListNextPointer = temp2;

    strcpy(temp2->footballerName, footballerName);
    strcpy(temp2->footballerTeamName, footballerTeamName);

    secondListNode* temp3 = (secondListNode*) malloc(sizeof(secondListNode));

    strcpy(temp3->awayTeamName, awayTeamName);
    temp3->minuteOfGoal = minuteOfGoal;
    temp3->matchID = matchID;
    temp3->secondListNextPointer = NULL;
    temp3->secondListPrevPointer = NULL;
    temp2->nextListPointer = temp3;

    return root1;

}

int main(int argc, char *argv[]) {
	
	freopen (argv[3],"w",stdout);

    MyMap playerToNumberOfGoals; // Key: player name, value : number of goals

    firstListNode * rootFirstList = NULL;

    ifstream inputFile;
    ifstream operationFile;
    inputFile.open(argv[1]);
    operationFile.open(argv[2]);
    string inputLine;
    string operationLine;

    while (!inputFile.eof()) {

		getline(inputFile, inputLine);

		int counter = 1, matchID, minuteOfGoal;
		char footballer[50];
		char homeTeam[50];
		char awayTeam[50];

		char* cstr = new char [inputLine.length()+1];
		strcpy (cstr, inputLine.c_str());

		char* p = std::strtok (cstr,",");
		while (p!=0)
		{
			switch(counter%5){
				case(1):
				{
					counter++;
					strcpy(footballer, p);
					std::string str(footballer);

					if ( playerToNumberOfGoals.find(footballer) == playerToNumberOfGoals.end() ) { // Update the map

						playerToNumberOfGoals[footballer] = 1;
					}
					else {playerToNumberOfGoals[footballer]++;}

					break;
				}
				case(2):
				{
					counter++;
					strcpy(homeTeam, p);
					break;
				}
				case(3):
				{
					counter++;
					strcpy(awayTeam, p);
					break;
				}
				case(4):
				{
					counter++;
					minuteOfGoal = atoi(p);
					break;
				}
				case(0):
				{
					counter++;
					matchID = atoi(p);
					rootFirstList = addNodeFirstList(rootFirstList, footballer, homeTeam, awayTeam, minuteOfGoal, matchID );
					break;
				}
			}
			p = strtok(NULL,",");
		}
    }

    inputFile.close();

    printf("1)THE MOST SCORED HALF\n");
    printf("%d\n", calculatePeriod(rootFirstList));

    printf("2)GOAL SCORER\n");
    calculateTopScorer(playerToNumberOfGoals);

    printf("3)THE NAMES OF FOOTBALLERS WHO SCORED HAT-TRICK\n");
    hatTrickFinder(rootFirstList);

    printf("4)LIST OF TEAMS\n");

    firstListNode* iter = rootFirstList;

    vector<string> listOfTeams;

    while(iter != NULL){

        if(std::find(listOfTeams.begin(), listOfTeams.end(), iter->footballerTeamName) == listOfTeams.end()) {

            char teamName[50];

            strcpy(teamName, iter->footballerTeamName);

            std::string str(teamName);

            listOfTeams.push_back(teamName);

        }

        iter = iter->firstListNextPointer;

    }

    int i, numberOfTeams = listOfTeams.size();

    for(i = 0; i < numberOfTeams; i++){

        cout << listOfTeams[i] << endl;

    }

    printf("5)LIST OF FOOTBALLERS\n");

    firstListNode* iter2 = rootFirstList;

    vector<string> listOfFootballers;

    while(iter2 != NULL){

        if(std::find(listOfFootballers.begin(), listOfFootballers.end(), iter2->footballerName) == listOfFootballers.end()) {

            char footballerName[50];

            strcpy(footballerName, iter2->footballerName);

            std::string str(footballerName);

            listOfFootballers.push_back(footballerName);

        }

        iter2 = iter2->firstListNextPointer;

    }

    int numberOfFootballers = listOfFootballers.size();

    for(i = 0; i < numberOfFootballers; i++){

        cout << listOfFootballers[i] << endl;

    }

    printf("6)MATCHES OF GIVEN FOOTBALLER\n");
    vector<string> listofSixthItem;

    while (!operationFile.eof()) {

        getline(operationFile, operationLine);
        char* cstr2 = new char [operationLine.length()+1];
        strcpy (cstr2, operationLine.c_str());

        char* p = std::strtok (cstr2,",");
        while (p!=0)
        {
            listofSixthItem.push_back(p);
            p = strtok(NULL,",");
        }
        break;
    }

    for(i = 0; i < 2; i++){
    
	firstListNode* iter3 = rootFirstList;
    cout << "Matches of ";
    cout << listofSixthItem[i] << endl;
        
		while(iter3 != NULL){

            char footballerName[50];

            strcpy(footballerName, iter3->footballerName);

            std::string str(footballerName);

            if(footballerName == listofSixthItem[i]){

                    secondListNode* iter3_2 = iter3->nextListPointer;

                while(iter3_2 != NULL){
                    printf("Footballer Name: %s,Away Team: %s,Min of Goal: %d,Match ID: %d\n", footballerName, iter3_2->awayTeamName, iter3_2->minuteOfGoal, iter3_2->matchID);
                    iter3_2 = iter3_2->secondListNextPointer;

                }

            }
            iter3 = iter3->firstListNextPointer;
        }

    }

    printf("7)ASCENDING ORDER ACCORDING TO MATCH ID\n");

    vector<string> listofSeventhItem;

        while (!operationFile.eof()) {

        getline(operationFile, operationLine);
        char* cstr2 = new char [operationLine.length()+1];
        strcpy (cstr2, operationLine.c_str());

        char* p = std::strtok (cstr2,",");
        
		while (p!=0)
        {
            listofSeventhItem.push_back(p);
            p = strtok(NULL,",");
        }
        break;
    }

    for(i = 0; i < 2; i++){

        int prevMatchID = -1;
        firstListNode* iter4 = rootFirstList;
            while(iter4 != NULL){
                char footballerName[50];
                strcpy(footballerName, iter4->footballerName);

                std::string str(footballerName);

                if(footballerName == listofSeventhItem[i]){
                        secondListNode* iter4_2 = iter4->nextListPointer;
                        while(iter4_2 != NULL){
                            
							if(prevMatchID != iter4_2->matchID){printf("footballer Name: %s,Match ID: %d\n", footballerName,iter4_2->matchID);}
                            prevMatchID = iter4_2->matchID;
                            iter4_2 = iter4_2->secondListNextPointer;

                        }

                }
            iter4 = iter4->firstListNextPointer;
            }

    }


    printf("8)DESCENDING ORDER ACCORDING TO MATCH ID\n");

    vector<string> listofEighthItem;
    vector<int> stackofEighthItem;

        while (!operationFile.eof()) {

        getline(operationFile, operationLine);
        char* cstr2 = new char [operationLine.length()+1];
        strcpy (cstr2, operationLine.c_str());

        char* p = std::strtok (cstr2,",");
        while (p!=0)
        {
            listofEighthItem.push_back(p);
            p = strtok(NULL,",");
        }
        break;
    }

    for(i = 0; i < 2; i++){
        int numberOfMatchesOfCurrentFootballer = 0;
        char footballerNameFound[50];

        int prevMatchID = -1;
        firstListNode* iter4 = rootFirstList;
            while(iter4 != NULL){
                char footballerName[50];
                strcpy(footballerName, iter4->footballerName);

                std::string str(footballerName);

                if(footballerName == listofEighthItem[i]){
                        secondListNode* iter4_2 = iter4->nextListPointer;
                        while(iter4_2 != NULL){
                            if(prevMatchID != iter4_2->matchID){stackofEighthItem.insert(stackofEighthItem.begin(),iter4_2->matchID); numberOfMatchesOfCurrentFootballer++;}
                            prevMatchID = iter4_2->matchID;
                            iter4_2 = iter4_2->secondListNextPointer;

                        }

                }
            iter4 = iter4->firstListNextPointer;
            }
        int m, sizeofStack = stackofEighthItem.size();

        for(m = 0; m < numberOfMatchesOfCurrentFootballer; m++){

            printf("footballer Name: ");
            cout << listofEighthItem[i];
            printf(",Match ID: %d", stackofEighthItem[m]);
			if(m < numberOfMatchesOfCurrentFootballer - 1){printf("\n");}

    }
	if(i == 0){printf("\n");}

	}
	
	operationFile.close();
	
    return 0;
}
