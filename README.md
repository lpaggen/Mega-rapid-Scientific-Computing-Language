*Welcome to my mathematical optimization language repo*

Currently WIP. Maybe master's thesis or academic publication down the line, will think about it and evaluate if this is even possible with the time constraints i am facing

atm it has: tokenizer, parser (wip), error handling (wip), AST (wip). Ideally i would implement support for functions and basic for loops, as well as conditional branching. no OOP in mind for the project. The big thing about this project would also be the support for symbolic algebra and mathematical optimization down the line, so linear programming etc could be implemented in due time. I am also considering implementing my own version of MapReduce in the language, to enhance the speed of those very expensive matrix multiplications. 

the tokenizer runs in O(n) time (obviously). parser O(n), 2nd pass in addition to the tokenizer. semantic analysis should be O(n) something when done, so overall no (major) mistakes are being made when it comes to core interpreter logic.

obviously i could use ANTLR and LLVM, but i'd rather just build this for learning, as opposed to it being an actually useful tool. perhaps it could be shown to university students if the algebra and optimization parts are ever made to work, however! that would be a cool learning tool. 
