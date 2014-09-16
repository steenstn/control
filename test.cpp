#include <iostream>
#include <Windows.h>

#define PAUSE 0xB3
#define NEXT_TRACK 0xB0
#define PREVIOUS_TRACK 0xB1
#define VOLUME_UP 0xAF
#define VOLUME_DOWN 0xAE

void printExplanation();

int main(int argc, char** argv) {

	if(argc <= 1) {
		printExplanation();
		return 0;
	}
	int resultingAction;
	
	if(*argv[1] == 'p') {
		resultingAction = PAUSE;
	} else if(*argv[1] == 'n') {
		resultingAction = NEXT_TRACK;
	} else if(*argv[1] == 'b') {
		resultingAction = PREVIOUS_TRACK;
	} else if(*argv[1] == 'u') {
		resultingAction = VOLUME_UP;
	} else if(*argv[1] == 'd') {
		resultingAction = VOLUME_DOWN;
	} else {
		printExplanation();
		return 0;
	}
	INPUT input;

	input.type = INPUT_KEYBOARD;
	input.ki.wScan = 0;
	input.ki.time = 0;
	input.ki.dwExtraInfo = 0;
	
    input.ki.wVk = resultingAction; 
    input.ki.dwFlags = 0; // 0 for key press
    SendInput(1, &input, sizeof(INPUT));
 
   
    input.ki.dwFlags = KEYEVENTF_KEYUP; // KEYEVENTF_KEYUP for key release
    SendInput(1, &input, sizeof(INPUT));

	return 0;
}

void printExplanation() {
	std::cout << "p - Play/pause" << std::endl
		<< "n - Next track" << std::endl
		<< "b - Previous track" << std::endl
		<< "u - Volume up" << std::endl
		<< "d - Volume down";
}