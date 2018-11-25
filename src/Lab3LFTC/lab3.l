%{
#include <stdlib.h>
#include <string.h>
#include <math.h>


struct TSItem {
	int Index;
	char Name[20];
};

struct FIPItem {
	int code;
	int TSIndex;
};

struct FIPItem fip[1000];
struct TSItem tsConsts[100];
struct TSItem tsIdentifiers[100];
int tsIdentifiersCount = 0;
int fipCount = 0;
int tsConstsCount = 0;


void addItem(int code,int tsIndex ) {
	fip[fipCount].TSIndex = tsIndex;
	fip[fipCount].code = code;
	fipCount = fipCount + 1;
}

void addConstant(int code, char name[]) {
	// check if constant already exists
	int i = 0;
	for (i = 0; i < tsConstsCount; i++) {
		if (strcmp(tsConsts[i].Name, name) == 0) { // if it exists
			// add the item with the corresponding ts index
			int index = tsConsts[i].Index;
			fip[fipCount].code = code;
			fip[fipCount].TSIndex = index;
			fipCount = fipCount + 1;
			return;
		}
	}
	// new constant
	// add the constant to ts
	tsConsts[tsConstsCount].Index = tsConstsCount;
	strcpy(tsConsts[tsConstsCount].Name, name);
	tsConstsCount = tsConstsCount + 1;

	// add the item with the corresponding ts index
	int index = tsConsts[i].Index;
	fip[fipCount].code = code;
	fip[fipCount].TSIndex = index;
	fipCount = fipCount + 1;
}

void addIdentifier(int code, char name[]) {
	// check if identifier already exists
	int i = 0;
	for (i = 0; i < tsIdentifiersCount; i++) {
		if (strcmp(tsIdentifiers[i].Name, name) == 0) {
			// add the item with the corresponding ts index
			int index = tsIdentifiers[i].Index;
			fip[fipCount].code = code;
			fip[fipCount].TSIndex = index;
			fipCount = fipCount + 1;
			return;
		}	
	}
	// new identifier
	// add the identifier to ts
	tsIdentifiers[tsIdentifiersCount].Index = tsIdentifiersCount;
	strcpy(tsIdentifiers[tsIdentifiersCount].Name, name);
	tsIdentifiersCount = tsIdentifiersCount + 1;

	// add the item with the corresponding ts index
	int index = tsIdentifiers[i].Index;
	fip[fipCount].code = code;
	fip[fipCount].TSIndex = index;
	fipCount = fipCount + 1;
}

void show(){
    int i = 0;
	printf("FIP:\n");
	for (i = 0; i < fipCount; i++) {
		printf("Code: %d      |IndexTS: %d \n", fip[i].code, fip[i].TSIndex);
	}
	printf("\nTS constante\n");
	for (i = 0; i < tsConstsCount; i++) {
		printf("Index: %d | Name: %s\n", tsConsts[i].Index, tsConsts[i].Name);
	}
	printf("\nTS identifieri\n");
	for (i = 0; i < tsIdentifiersCount; i++) {
		printf("Index: %d | Name: %s\n", tsIdentifiers[i].Index, tsIdentifiers[i].Name);
	}
	printf("ENDDDDD\n");
}
%}
%option noyywrap

%%
readln						addItem(22, -1);
const						addItem(19, -1);
writeln						addItem(23, -1);
if							addItem(24, -1);
else						addItem(25, -1);
then						addItem(26, -1);
while							 addItem(27, -1);
real						addItem(28, -1);
integer					addItem(29, -1);
string					addItem(30, -1);
do					addItem(31, -1);
var				addItem(32, -1);
begin				 addItem(33, -1);
end							addItem(34, -1);

\+							addItem(2, -1);
\-							addItem(3, -1);
\/							addItem(4, -1);
\*							addItem(5, -1);
\%							 addItem(6, -1);
\<							addItem(7, -1);
\>							 addItem(8, -1);
\<>							 addItem(9, -1);
=							 addItem(10, -1);
\:=							addItem(11, -1);
\(						 addItem(12, -1);
\)						 addItem(13, -1);
\<=						 addItem(14, -1);
\>=						addItem(15, -1);
\[						addItem(16, -1);
\]						 addItem(17, -1);
\:							addItem(18, -1);
\;						addItem(35, -1);
(end.)					addItem(36, -1);
[ \t\n]+				;/* eat up whitespace */
[a-zA-Z][a-zA-Z0-9]*		addIdentifier(0, yytext);
[+-]?[0-9]					addConstant(1, yytext);
[+-]?[1-9][0-9]*			addConstant(1, yytext);
[+-]?[1-9][0-9]*\.[0-9]+    addConstant(1, yytext);
\'[a-zA-Z0-9]+\'    	    addConstant(1, yytext);
%%

int main(int argc, char *argv[]) {
	if (argc!=2) {
		printf("Usage: <./a.out> <source file> \n");
		exit(0);
	}
	yyin = fopen(argv[1], "r");
	yylex();
	show();
	
}