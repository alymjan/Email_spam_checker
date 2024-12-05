This is a spamEmail filter. The main class with the checks is called Algorithm.
+--------------------------+
|         Algorithm        |
+--------------------------+
| - email: EmailStorage    |
| - score: int             |
| - isHam: boolean         |
+--------------------------+
| + Algorithm(email: EmailStorage)      |
| + wordCount(): void                   |
| + hyperLinkCheck(): void              |
| + wordLength(): void                  |
| + repeatedWords(): void               |
| + triggerWords(): void                |
| + caseSensitivity(): void             |
| + checkNonsenseText(): void           |
| + triggerPhrases(): void              |
| + scoreChecker(): void                |
| - isPhraseMatch(phrase: String,       |
|                 spamPhrases: String[]): boolean |
+--------------------------+

Algorithm contains the methods that check the email that is passed into it. Each method at the end gives a score, which is added to overall score which at the end determines if the email is spam or ham. The method's give a score only if they don't pass the check. If they do pass the check it does not give any score. The score is checked at the end which determines if the email is ham if it has less than set score. 


EmailStorage
+-------------------------+
|      EmailStorage       |
+-------------------------+
| - word: ArrayList<String> |
| - label: String         |
+-------------------------+
| + EmailStorage(label: String)  |
| + addWord(word: String): void  |
| + getLabel(): String           |
| + getWord(): ArrayList<String> |
| + toString(): String           |
+-------------------------+
EmailStorage is a class that stores each word of the passed Email as a string type ArrayList. It also assigns it a label when passed for easier tracking. 


FeatureExtractor

+--------------------------+
|     FeatureExtractor     |
+--------------------------+
| - email: EmailStorage    |
| - score: int             |
| - isHam: boolean         |
+--------------------------+
| + FeatureExtractor(email: EmailStorage) |
| + wordCount(): int                    |
| + hyperLinkCheck(): int               |
| + wordLength(): int                   |
| + specialCharaters(): int             |
| + repeatedWords(): int                |
| + triggerWords(): int                 |
| + caseSensitivity(): int              |
| + checkNonsenseText(): int            |
| + triggerPhrases(): int               |
| + scoreChecker(): void                |
| - isPhraseMatch(phrase: String,       |
|                 spamPhrases: String[]): boolean |
+--------------------------+
FeatureExtractor is what we used to determine which one of the methods were useful and which were not. Each method either returns 1 or 0 depending on if the email passed the test or not. If it does not pass the test it returns 1. This was used when we were determining if the method was good by adding all of the numbers up at the end. When we checked this, specialCharacter method only failed one of the emails which was an indication for us not to use it for our main class. 


FileScanner
+------------------------------+
|          FileScanner         |
+------------------------------+
| <<Static Class>>             |
+------------------------------+
| + main(args: String[]): void |
| + readFile(name: String):    |
|   ArrayList<EmailStorage>    |
+------------------------------+

FileScanner is our main method and class where we passed the csv file into and had that file read, broken down and stored in EmailStorage. 
