#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>

int main() {
    char command[300]; // store the user inputs
    char *arg1, *arg2, *arg3; // for command arguments i had to add an extra i dont know if this is breaking the rules
    char cwd[1024]; // used to store the current working directory path

    while (true) {
        if (getcwd(cwd, sizeof(cwd)) != NULL) {
            printf("\nType Ctrl-C or type 'exit' to exit.\nCurrent directory: %s\n> ", cwd); //imma keep this here if needed
        } else {
            perror("getcwd() error");
            return 1;
        }

        if (fgets(command, sizeof(command), stdin) == NULL) {
            continue;
        }
        command[strcspn(command, "\n")] = 0;

        arg1 = strtok(command, " ");
        arg2 = strtok(NULL, " ");
        arg3 = strtok(NULL, " ");
        char *extraArg = strtok(NULL, " "); // check for additional arguments

        if (arg1 == NULL) {
            continue;
        } else if (strcmp(arg1, "exit") == 0) {
            printf("exiting program\n");
            break;
        } else if (strcmp(arg1, "cd") == 0) {
            if (arg2 == NULL || arg3 != NULL) {
                printf("incorrect number of arguments for 'cd'\n");
            } else if (_chdir(arg2) != 0) {
                perror("cd error");
            }
        } else if (strcmp(arg1, "dir") == 0) {
            if (arg2 != NULL || extraArg != NULL) {
                printf("incorrect number of arguments for 'dir'\n");
            } else {
                system("dir");
            }
        } else if (strcmp(arg1, "type") == 0) {
            if (arg2 == NULL || arg3 != NULL) {
                printf("incorrect number of arguments for 'type'\n");
            } else {
                char cmd[350];
                snprintf(cmd, sizeof(cmd), "type %s", arg2);
                system(cmd);
            }
        } else if (strcmp(arg1, "del") == 0) {
            // simple it deletes
            if (arg2 == NULL || arg3 != NULL) {
                printf("incorrect number of arguments for 'del'\n");
            } else {
                char cmd[350];
                snprintf(cmd, sizeof(cmd), "del %s", arg2);
                system(cmd);
            }
        } else if (strcmp(arg1, "ren") == 0) {
            // renames from arg 2 to 3
            if (arg2 == NULL || arg3 == NULL || extraArg != NULL) {
                printf("Incorrect number of arguments for 'ren'\n");
            } else {
                char cmd[400];
                snprintf(cmd, sizeof(cmd), "ren %s %s", arg2, arg3);
                system(cmd);
            }
        } else if (strcmp(arg1, "copy") == 0) {\
        // copies from arg2 to 3
            if (arg2 == NULL || arg3 == NULL || extraArg != NULL) {
                printf("Incorrect number of arguments for 'copy'\n");
            } else {
                char cmd[400];
                snprintf(cmd, sizeof(cmd), "copy %s %s", arg2, arg3);
                system(cmd);
            }
        } else {
            // cant type or wrong command test case
            printf("unsupported or incorrect command\n");
        }
    }

    return 0;
}
